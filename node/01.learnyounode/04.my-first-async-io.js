/**
 * Write a program that uses a single asynchronous filesystem operation to read a file
 * and print the number of newlines it contains to the console (stdout), similar to running cat file | wc -l.
 *
 * The full path to the file to read will be provided as the first command-line argument.
 */

if (process.argv.length < 3) {
  throw new Error('Please specify an input file!');
}

var fs = require('fs');

fs.readFile(process.argv[2], function (err, content) {
  if (err) { throw err; }
  console.log(content.toString().split("\n").length - 1);
});