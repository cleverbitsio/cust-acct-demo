var http = require("http"),
	url = require("url"),
	couchbase = require("couchbase");

var cluster = new couchbase.Cluster();
var bucket = cluster.openBucket('default');

server = http.createServer(function (request, response) {
	console.log("processing request");

	var uri = url.parse(request.url).pathname;

	console.log(uri);
	response.writeHead(200, {
			"Content-Type": "text/plain; charset=UTF-8"
	});

	if (uri === "/trx") {
      getTrx(request, response);
	}


});

server.listen(8888);

console.log("server running");

server.on('error', function(err){
		console.log(err);
		process.exit(1);
});


function getTrx(request, response) {
	console.log("processing trx");

	bucket.get("acct:100063-trx-2014-11", {format: 'raw'}, function(err, result) {
		if (err) throw err;

		//console.log(result.value);
		response.write(result.value);

		response.end();
	});
	return;
}
