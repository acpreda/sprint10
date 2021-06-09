package com.acpreda.sprint10.jpa.search;

import com.querydsl.core.types.dsl.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.acpreda.sprint10.commons.StringHelper.*;
import static java.lang.String.format;

public class PredicateBuilder<T> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Pattern QUERY_PATTERN = Pattern.compile("([\\w.]{1,128}?)([~:<>])([^,]{1,128}?),");

    private final List<SearchCriteria> criteria;
    private final Class<T> rootClass;
    private final String rootAlias;

    public PredicateBuilder(Class<T> rootClass) {
        this.rootClass = rootClass;
        this.rootAlias = toSnakeCase(rootClass.getSimpleName());
        criteria = new ArrayList<>();
    }

    public PredicateBuilder(Class<T> rootClass, String queryExpression) {
        this(rootClass);
        expressionToCriteria(queryExpression);
    }

    private void with(String key, String operation, Object value) {
        criteria.add(new SearchCriteria(key, operation, value));
    }

    public BooleanExpression build() throws InvalidPredicateException {
        if (criteria.isEmpty()) {
            return Expressions.asBoolean(true).isTrue();
        }

        try {
            List<BooleanExpression> predicates = criteria.stream()
                    .map(this::criteriaToBooleanExpression)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            BooleanExpression result = Expressions.asBoolean(true).isTrue();
            for (BooleanExpression predicate : predicates) {
                result = result.and(predicate);
            }
            return result;
        } catch (Exception e) {
            throw new InvalidPredicateException(e);
        }
    }

    private void expressionToCriteria(String queryExpression) {
        if (queryExpression != null) {
            Matcher matcher = QUERY_PATTERN.matcher(queryExpression + ",");
            while (matcher.find()) {
                with(matcher.group(1), matcher.group(2), matcher.group(3));
            }
        }
    }

    private PathBuilder<T> path() {
        return new PathBuilder<>(rootClass, rootAlias);
    }

    private BooleanExpression criteriaToBooleanExpression(SearchCriteria c) {

        Class<?> propertyClass = propertyClass(rootClass, c.getKey());
        String propertyName = propertyName(c.getKey());

        Optional<Field> searchable = Arrays.stream(propertyClass.getDeclaredFields())
                .filter(field -> field.getAnnotation(Search.class) != null)
                .filter(field -> field.getName().equals(propertyName))
                .findFirst();
        if (!searchable.isPresent()) {
            throw new PredicateBuilderException("Propiedad no existe o no tiene la anotación Search:" + c.getKey());
        }

        Search annotation = searchable.get().getAnnotation(Search.class);
        if (annotation.strategy() == SearchStrategy.NUMBER) {
            return mLong(c);
        } else if (annotation.strategy() == SearchStrategy.STRING_NORMALIZADO) {
            return containsNormalizado(c);
        } else if (annotation.strategy() == SearchStrategy.UUID) {
            return eqUUID(c);
        } else if (annotation.strategy() == SearchStrategy.BOOLEAN) {
            return eqBoolean(c);
        } else if (annotation.strategy() == SearchStrategy.DATE) {
            return dateStrategy(c);
        }
        return contains(c);
    }

    private String propertyName(String key) {
        if (key.contains(".")) {
            return key.substring(key.lastIndexOf('.') + 1);
        } else {
            return key;
        }
    }

    private Class<?> propertyClass(Class<?> rootClass, String key) {
        if (key.contains(".")) {
            String[] split = key.split("[.]");
            Class<?> propClass;
            try {
                propClass = rootClass.getMethod("get" + toCamelCase(split[0])).getReturnType();
            } catch (NoSuchMethodException e) {
                throw new PredicateBuilderException("El método get no se puede obtener para: " + key);
            }
            return propertyClass(propClass, key.substring(key.indexOf('.') + 1));
        } else {
            return rootClass;
        }
    }

    private BooleanExpression dateStrategy(SearchCriteria c) {
        ComparablePath<Date> comp = path().getComparable(c.getKey(), Date.class);
        Date value;
        try {
            value = dateFormat.parse((String) c.getValue());
        } catch (ParseException parseException) {
            try {
                value = new Date(Long.parseLong(c.getValue().toString()));
            } catch (Exception exception) {
                throw new PredicateBuilderException("La cadena de texto no se puede convertir a fecha: " + c.getValue());
            }
        }
        if (":".equals(c.getOperation())) {
            return comp.eq(value);
        } else if ("<".equals(c.getOperation())) {
            return comp.lt(value);
        } else if (">".equals(c.getOperation())) {
            return comp.goe(value);
        } else {
            throw new PredicateBuilderException(format("La operación '%s' no está soportada para fechas", c.getOperation()));
        }
    }

    private BooleanExpression eqUUID(SearchCriteria c) {
        try {
            if (!":".equals(c.getOperation())) {
                throw new PredicateBuilderException("Operación no soportada para el campo " + c.getKey() + ": " + c.getOperation());
            }
            UUID value = java.util.UUID.fromString(c.getValue().toString());
            return path().getComparable(c.getKey(), UUID.class).eq(value);
        } catch (IllegalArgumentException e) {
            throw new PredicateBuilderException("El valor no es un UUID válido: " + c.getValue().toString(), e);
        }
    }

    private BooleanExpression eqBoolean(SearchCriteria c) {
        if (!":".equals(c.getOperation())) {
            throw new PredicateBuilderException("Operación no soportada para el campo " + c.getKey() + ": " + c.getOperation());
        }
        Boolean value = Boolean.valueOf(c.getValue().toString());
        return path().getComparable(c.getKey(), Boolean.class).eq(value);
    }

    private BooleanExpression contains(SearchCriteria c) {
        StringPath path = path().getString(c.getKey());
        Object valueObject = c.getValue();
        String value = valueObject == null ? "" : valueObject.toString();
        switch (c.getOperation()) {
            case ":":
                return path.eq(value);
            case "~":
                return path.containsIgnoreCase(value);
            default:
                throw new PredicateBuilderException("Boolean operation not supported for " + c.getKey() + " : " + c.getOperation());
        }
    }

    private BooleanExpression containsNormalizado(SearchCriteria c) {
        StringPath path = path().getString(c.getKey());
        String value = normalizar(c.getValue().toString());
        switch (c.getOperation()) {
            case ":":
                return path.eq(value);
            case "~":
                return path.containsIgnoreCase(value);
            default:
                throw new PredicateBuilderException("Normalized string operation not supported for " + c.getKey() + " : " + c.getOperation());
        }
    }

    private BooleanExpression mLong(SearchCriteria c) {
        NumberPath<Long> path = path().getNumber(c.getKey(), Long.class);
        Long value = Long.parseLong(c.getValue().toString());
        switch (c.getOperation()) {
            case ":":
                return path.eq(value);
            case ">":
                return path.goe(value);
            case "<":
                return path.loe(value);
            default:
                throw new PredicateBuilderException("Numeric operation not supported for " + c.getKey() + " : " + c.getOperation());
        }
    }
}
