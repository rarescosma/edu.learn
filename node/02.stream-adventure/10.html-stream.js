/**
 * Your program will get some html written to stdin.
 *
 * Convert all the inner html to upper-case for elements with a class name of "loud".
 *
 * You can use `trumpet` and `through` to solve this adventure.
 */

var trumpet = require('trumpet');
var through = require('through');
var tr = trumpet();

var stream = tr.select('.loud').createStream();
stream.pipe(through(function (buf) {
  this.queue(buf.toString().toUpperCase());
})).pipe(stream);

process.stdin.pipe(tr).pipe(process.stdout);;
