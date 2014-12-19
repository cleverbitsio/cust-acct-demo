var http = require("http"),
	url = require("url"),
	couchbase = require("couchbase");

var PORT = process.env.PORT;

var cluster = new couchbase.Cluster('couchbase://ec2-54-76-39-165.eu-west-1.compute.amazonaws.com');
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

server.listen(PORT);

console.log("server running");

server.on('error', function(err){
		console.log(err);
		process.exit(1);
});

function randomInt (low, high) {
    return Math.floor(Math.random() * (high - low) + low);
}

function getTrx(request, response) {
	console.log("processing trx");
	var acct_id = randomInt(1,100000);

	bucket.get("acct:"+acct_id+"-trx-2014-11", {format: 'raw'}, function(err, result) {
		if (err) throw err;

		//console.log(result.value);
		response.write(result.value);

		response.end();
	});
	return;
}
