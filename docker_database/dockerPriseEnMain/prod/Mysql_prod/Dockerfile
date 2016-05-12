FROM mysql:5.6
 
ENV MYSQL_DATABASE computer-database-db
ENV MYSQL_ROOT_PASSWORD root

COPY db/1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY db/2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
COPY db/3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTRIES.sql

