FROM pgrouting/pgrouting:12-3.0-3.1.0

ENV POSTGRES_DB 'postgres'
ENV POSTGRES_USER 'postgres'
ENV POSTGRES_PASSWORD 'postgres'

COPY load-extensions.sh /docker-entrypoint-initdb.d/
