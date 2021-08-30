# OSM Pipeline

A pipeline that uses the OSM data to build and load what is necessary for OSRM, Pgrouting and Postgis 

### Do dat thang

Download the osm file from Geofabrik

```bash
wget "https://download.geofabrik.de/australia-oceania/micronesia-latest.osm.pbf"
```

Convert the downloaded file to osm 

```bash
osmosis --read-pbf data/micronesia-latest.osm.pbf --write-xml data/micronesia-latest.osm
```

Run the pipeline jobs

```bash
docker-compose -f docker-compose.yml run osm2postgres
docker-compose -f docker-compose.yml run osrm_build
```

To be scheduled with cron ...
