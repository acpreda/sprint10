# Diccionario de base de datos

A continuación se listan las tablas que hacen parte de la base de datos del sistema.

## Tablas

<#list tables as table>
### ${table.name}
|Nombre|Tipo|Descripción|
|-|-|-|
<#list table.columns as column>
|${column.name}|${column.type}||
</#list>
</#list>
