/**
 * Write a program that uses a single synchronous filesystem operation to read a file
 * and print the number of newlines it contains to the console (stdout), similar to running cat file | wc -l.
 *
 * The full path to the file to read will be provided as the first command-line argument.
 */

if (process.argv.length < 3) {
  throw new Error('Please specify an input file!');
}

var fs = require('fs');

var content = fs.readFileSync(process.argv[2]).toString();
console.log(content.split("\n").length - 1);

// note you can avoid the .toString() by passing 'utf8' as the
// second argument to readFileSync, then you'll get a String!
//
// fs.readFileSync(process.argv[2], 'utf8').split('\n').length - 1
