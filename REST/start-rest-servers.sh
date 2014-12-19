#!/bin/bash

for i in {0..5}
do
  export PORT=666$i
  echo $PORT
  ~/node-v0.10.34-linux-x64/bin/node rest.js 2>&1 >/dev/null &
done
