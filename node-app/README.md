# PUSH NOTIFICATION SERVER

### Introduction
This is a node application which streams document changes to the browser. 

### Prerequisites

* Node
	- ```brew install node```
* Npm
	- couchbase ```npm install -g couchbase```
	- socket.io ```npm install -g socket.io```


### Environment/Configuration changes

- you the correct domain name in the client side socket.io script.
- ensure you update the Couchbase host in server.js  

### Run instructions

- to run simple type ```node server.js```
- then browse to ```http://<HOST>:8000/client.html```

