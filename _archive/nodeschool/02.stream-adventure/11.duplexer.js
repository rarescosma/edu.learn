/**
 * Write a program that exports a function that spawns a process from a `cmd` string and an `args` array
 * and returns a single duplex stream joining together the stdin and stdout of the spawned process.
 */

var spawn = require('child_process').spawn;
var duplexer = require('duplexer');

module.exports = function (cmd, args) {
  // spawn the process and return a single stream
  // joining together the stdin and stdout here
  var proc = spawn(cmd, args);
  return duplexer(proc.stdin, proc.stdout);
};