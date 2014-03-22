/**
 * Write a program that combines all of the buffers from `process.stdin`
 * and then writes the single big buffer out to the console.
 *
 * Bonus points if you use a stream!
 */

var through = require('through');
var buffers = [];

process.stdin.pipe(through(function (buf) {
  buffers.push(buf);
}, function () {
  console.log(Buffer.concat(buffers));
}));
