/**
 * You will be given text on process.stdin.
 *
 * Buffer the text and reverse it using
the `concat-stream` module before writing it to stdout.
 */

var concat = require('concat-stream');

process.stdin.pipe(concat(function (body) {
  var content = body.toString();
  process.stdout.write(content.split('').reverse().join(''));
}));