#!/bin/bash

cd java-loaders;

javac -cp Couchbase-Java-Client-1.4.6/*:joda-time-2.6/*:gson-2.3.1.jar:. MainAccGenerator.java ;
java -cp Couchbase-Java-Client-1.4.6/*:joda-time-2.6/*:gson-2.3.1.jar:. MainAccGenerator; 


