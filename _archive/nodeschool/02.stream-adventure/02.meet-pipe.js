/**
 * You will get a file as the first argument to your program (process.argv[2]).
 *
 * Use `fs.createReadStream()` to pipe the given file to `process.stdout`.
 */

if (process.argv.length < 2) {
  throw new Error('no input file!');
}

var fs = require('fs');

fs.createReadStream(process.argv[2]).pipe(process.stdout);
