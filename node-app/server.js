var app = require('http').createServer(handler),
io = require('socket.io').listen(app),
parser = new require('xml2json'),
fs = require('fs');
var url = require("url");

// creating the server ( localhost:8000 ) 
app.listen(8000);
app.on('error', function(err){
    console.log(err);
    process.exit(1);
});
console.log("server running");

// on server started we can load our client.html page
function handler(req, res) {

  var uri = url.parse(req.url).pathname;
  console.log(uri);
  if (uri.indexOf("/trx") != -1) {
    res.writeHead(200, {
    "Content-Type": "text/plain; charset=UTF-8"
    });
      getTrx(req, res);
  }
  else {

    fs.readFile(__dirname + '/client.html', function(err, data) {
      if (err) {
        console.log(err);
        res.writeHead(500);
        return res.end('Error loading client.html');
      }
      res.writeHead(200);
      res.end(data);
    });
  }
}


fileToWatch = "/example.json";
io.sockets.on('connection', function(socket) {
  compute();
  console.log(__dirname);
  fs.watch(__dirname + fileToWatch, function(curr, prev) { // watching the json file
    fs.readFile(__dirname + fileToWatch, function(err, data) {
      if (err) throw err;
      socket.volatile.emit('notification', JSON.stringify(JSON.parse(data))); // on file change we can read the new json
    });
  });
});

var couchbase = require("couchbase");

var flag=true;
function compute() {

    var cluster = new couchbase.Cluster("127.0.0.1:8091");//connect to Couchbase server
    var bucket = cluster.openBucket("default", function(err) {
      if(err) {
        throw err; //failed to make a connection to the Couchbase cluster
      }

      bucket.get("account:10000", function(err, result) {
        if(err) {
          throw err; //failed to retrive key
        }

        var jsonObj = result.value;

        //add attributes        
        jsonObj.time = new Date();
        //console.log(jsonObj);
        
        var jsonString = JSON.stringify(jsonObj); //console.log(jsonString);
        fs.writeFile("."+fileToWatch, jsonString, function(error) {  
          //if(!error) console.log("Written file!");
        });
      });
    });
   if(flag)
   setTimeout(compute, 5000); 
}


function getTrx(request, response) {
  var cluster = new couchbase.Cluster();
  var bucket = cluster.openBucket('default');
  console.log("processing trx");

// acct:10000-trx-2014-12
// acct:10000-trx-2014-11
// acct:10000-trx-2014-10

  var uri = url.parse(request.url).pathname;
  var key = "acct:10000-trx-2014-12";
  console.log(uri);
  if (uri.indexOf("/trx/acct:10000-trx-2014-12") != -1) 
    key = "acct:10000-trx-2014-12"
  else if (uri.indexOf("/trx/acct:10000-trx-2014-11") != -1) 
    key = "acct:10000-trx-2014-11"
  else if (uri.indexOf("/trx/acct:10000-trx-2014-10") != -1) 
    key = "acct:10000-trx-2014-10"

  bucket.get(key, {format: 'raw'}, function(err, result) {
    if (err) throw err;
    response.write(result.value); //console.log(result.value);
    response.end();
  });
  return;
}
