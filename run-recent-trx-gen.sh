#!/bin/bash

cd java-loaders;

javac -cp libs/*:. MainRecentTrxGenerator.java; 
java -cp libs/*:. MainRecentTrxGenerator; 


