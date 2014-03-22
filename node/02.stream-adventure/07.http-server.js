/**
 * Write an http server that uses a through stream to
 * write back the request stream as upper-cased response data for POST requests.
 */

var http = require('http');
var through = require('through');

var server = http.createServer(function (req, res) {
  if (req.method === 'POST') {
    req.pipe(through(function (buf) {
      this.queue(buf.toString().toUpperCase());
    })).pipe(res);
  } else res.end('will i dream? \n');
});

server.listen(parseInt(process.argv[2]));
