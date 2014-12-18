#!/bin/bash

cd java-loaders;

javac -cp libs/*:. MainCustGenerator.java;
java -cp libs/*:. MainCustGenerator; 


