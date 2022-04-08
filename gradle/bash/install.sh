#!/usr/bin/env bash

docker network create template

# MariaDB
docker run --name templatedb -d --network template -p 3306:3306 -e MYSQL_ROOT_PASSWORD=1234 -e MYSQL_DATABASE=templatedb -v /root/template/mysql:/var/lib/mysql --restart always mariadb --character-set-server=utf8 --collation-server=utf8_general_ci
# Postgres

docker run --name templatedb -d --network template -p 5432:5432 -e POSTGRES_PASSWORD=1234 -e POSTGRES_DB=templatedb -v /root/template/postgres:/var/lib/postgresql/data --restart always postgres

docker run --name templatemongo -d --network template -p 27017:27017 -v /root/template/mongo/:/data/db -e MONGO_INITDB_ROOT_USERNAME=root -e MONGO_INITDB_ROOT_PASSWORD=1234 --restart always mongo

#docker run --name ${project}mongoclient -d -p 3000:3000 --restart always --link ${project}mongo mongoclient/mongoclient