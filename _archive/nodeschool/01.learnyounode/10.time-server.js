/**
 * Write a TCP time server!
 *
 * Your server should listen to TCP connections on the port provided by the first argument to your program.
 * For each connection you must write the current date & time in the format:
 *
 * "YYYY-MM-DD hh:mm"
 *
 * followed by a newline character. Month, day, hour and minute must be zero-filled to 2 integers. For example:
 *
 * "2013-07-06 07:42"
 */

if (process.argv.length < 3) {
  throw new Error('Please specify the port!');
}

var net = require('net');
var strftime = require('strftime');


var now = function () {
  return strftime('%F %H:%M', new Date());
};

var server = net.createServer(function (socket) {
  socket.end(now() + "\n");
});

server.listen(Number(process.argv[2]));
