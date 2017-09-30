/**
 * Instead of transforming every line as in the previous "INPUT OUTPUT" example,
 * for this challenge, convert even-numbered lines to upper-case and odd-numbered lines to lower-case.
 */

var split = require('split');
var through = require('through');

var lineCount = 0;

process.stdin
  .pipe(split())
  .pipe(through(function (buf) {
    lineCount++;
    var line = buf.toString() + "\n";
    this.queue((lineCount % 2) ? line.toLowerCase() : line.toUpperCase());
  }))
  .pipe(process.stdout);