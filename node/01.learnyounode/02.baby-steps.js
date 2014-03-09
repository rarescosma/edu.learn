/**
 * Write a program that accepts one or more numbers as command-line arguments
 * and prints the sum of those numbers to the console (stdout).
 */

console.log(
  Array.prototype.slice.call(process.argv, 2).reduce(
    function (acc, n) { return acc + Number(n); },
    0
  )
);