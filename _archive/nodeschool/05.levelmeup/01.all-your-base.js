/**
 * Write a Node program that accepts two command-line arguments, X and Y
 * and prints out to the console the following text:
 *
 * ALL YOUR X ARE BELONG TO Y
 */

var x = process.argv[2] || 'base';
var y = process.argv[3] || 'us';
console.log('ALL YOUR %s ARE BELONG TO %s', x, y);