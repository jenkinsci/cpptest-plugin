#!/bin/bash

# build package hpi and start hudson server on localhost:8080 with this plugins

cd ~/hudson/hudson/plugins/cpptest/
# mvn install
mvn package

cp ~/hudson/hudson/plugins/cpptest/target/cpptest.hpi ~/.hudson/plugins

java -jar ~/.hudson/hudson.war
