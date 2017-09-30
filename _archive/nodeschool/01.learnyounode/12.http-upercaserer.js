/**
 * Write an HTTP server that receives only POST requests and converts
 * incoming POST body characters to upper-case and returns it to the client.
 *
 * Your server should listen on the port provided by the first argument to your program.
 */

if (process.argv.length < 3) {
  throw new Error('Please specify a port!');
}

var http = require('http');
var map = require('through2-map');

var server = http.createServer(function (request, response) {
  if (request.method !== 'POST') {
    return response.end('please POST\n');
  }

  request.pipe(map(function (chunk) {
    return chunk.toString().toUpperCase();
  })).pipe(response);
});

server.listen(Number(process.argv[2]));