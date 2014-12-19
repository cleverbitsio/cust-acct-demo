#!/bin/bash

for i in {0..5}
do
 export PORT=666$i
 echo $PORT
 ab -n 100000 -c 10 http://127.0.0.1:$PORT/trx  > ~/ab$i 2>&1 &
done
