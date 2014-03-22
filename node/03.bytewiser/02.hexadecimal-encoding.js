/**
 * Write a node program that takes the following 6 bytes,
 * converts them to a hexidecimal string using a Buffer and logs them to the console:
 *
 * 0 15 24 3 250 83
 */

var buf = new Buffer([0, 15, 24, 3, 250, 83]);
console.log(buf.toString('hex'));