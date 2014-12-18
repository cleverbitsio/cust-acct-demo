#!/bin/bash

cd java-loaders;

javac -cp libs/*:. MainAccGenerator.java ;
java -cp libs/*:. MainAccGenerator; 


