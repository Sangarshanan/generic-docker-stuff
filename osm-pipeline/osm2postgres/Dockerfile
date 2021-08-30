FROM ubuntu:focal-20201106
ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y \
    autoconf \
    automake \
    g++ \
    git-core \
    libboost-dev \
    libboost-filesystem-dev \
    libboost-system-dev \
    libboost-thread-dev \
    libbz2-dev \
    libgeos++-dev \
    libgeos-dev \
    libpq-dev \
    libproj-dev \
    libtool \
    libxml2-dev \
    make \
    osmosis \
    osm2pgsql \
    osm2pgrouting

COPY ./mapconfig.xml mapconfig.xml
