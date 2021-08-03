# Diccionario de base de datos

A continuación se listan las tablas que hacen parte de la base de datos del sistema.

Este documento se genera a partir de los comentarios de las tablas y los campos que existen en la base de datos.
Como práctica de desarrollo se recomienda tener al día esta documentación directo en la base de datos usando los
siguientes comandos reemplazando <nombre_tabla> y <nombre_campo> por los correspondientes valores:

```
comment on table <nombre_tabla> is '<comentario_tabla>';
comment on column <nombre_tabla>.<nombre_campo> is '<comentario_columna>';
```

## Tablas

<#list tables as table>

### ${table.name}

${table.comments!}

|Nombre|Tipo|Descripción|
|-|-|-|
<#list table.columns as column>
|${column.name}|${column.type}|${column.comments!}|
</#list>
</#list>

