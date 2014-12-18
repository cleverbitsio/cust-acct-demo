#!/bin/bash

cd java-loaders;

javac -cp libs/*:. MainTrxGenerator.java;
java -cp libs/*:. MainTrxGenerator; 


