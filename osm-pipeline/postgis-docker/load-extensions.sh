psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<EOF
create extension postgis;
create extension hstore;
create extension pgrouting;
select * FROM pg_extension;
EOF
