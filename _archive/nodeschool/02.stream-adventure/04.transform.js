/**
 * Convert data from `process.stdin` to upper-case data on `process.stdout` using the `through` module.
 */

var through = require('through');

var tr = through(function (buf) {
  this.queue(buf.toString().toUpperCase());
});

process.stdin.pipe(tr).pipe(process.stdout);
