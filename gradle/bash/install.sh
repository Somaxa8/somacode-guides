#!/usr/bin/env bash

docker network create guides

# MariaDB
docker run --name guidesdb -d --network guides -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=guidesdb -v /root/guides/mysql:/var/lib/mysql --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
# Postgres

docker run --name guidesdb -d --network guides -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=guidesdb -v /root/guides/postgres:/var/lib/postgresql/data --restart always postgres

docker run --name guidesmongo -d --network guides -p 27017:27017 -v /root/guides/mongo/:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=1234 --restart always mongo

#docker run --name ${project}mongoclient -d -p 3000:3000 --restart always --link ${project}mongo mongoclient/mongoclient