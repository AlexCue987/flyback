# Flyback: decorating flyback with current state of database objects

## Purpose

Flyway is a great tool, but we do not get to see the current tables/views definitions
in the same way as other files such as Java classes.

Instead, we have to glean the current state from from the original CREATE TABLE/VIEW statement
and the history of modifications, or get another tool, such as SQL Developer, to get the current definition.

Flyback reverse-engineers current table/view definitions to be stored in git -
that gives us both current definitions and change history, just like for Java classes and other files.

Also these reverse-engineered definitions are easier to read that the ones generated by SQL Developer.

## Examples

Postgres: src/test/java/com/flyback/oracle/ExampleOfUsage.java

Oracle: src/test/java/com/flyback/oracle/ExampleOfUsage.java
