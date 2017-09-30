/**
 * Create a program that prints a list of files in a given directory, filtered by the extension of the files.
 * You will be provided a directory name as the first argument to your program (e.g. '/path/to/dir/')
 * and a file extension to filter by as the second argument.
 *
 * For example, if you get 'txt' as the second argument then you will need to
 * filter the list to only files that end with .txt.
 *
 * The list of files should be printed to the console, one file per line.
 * You must use asynchronous I/O.
 */

if (process.argv.length < 4) {
  throw new Error('Please specify a directory and an extension!');
}

var fs = require('fs');
var path = require('path');

fs.readdir(process.argv[2], function (err, files) {
  if (err) { throw err; }

  files.filter(
    function (f) { return path.extname(f) === '.' + process.argv[3]; }
  ).map(
    function (f) { console.log(f); }
  );
});
