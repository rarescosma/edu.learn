/**
 * Write a program that performs an HTTP GET request to a URL provided to you as the first command-line argument.
 * Write the String contents of each "data" event from the response to a new line on the console (stdout).
 */

if (process.argv.length < 3) {
  throw new Error('Please specify a URL!');
}

var http = require('http');

http.get(process.argv[2], function (stream) {
  stream.setEncoding('utf8');

  stream.on('data', console.log);
  stream.on('error', console.error);
});
