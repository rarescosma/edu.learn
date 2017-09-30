#!/usr/bin/env node

var fs = require('fs');
var assert = require('assert');

var outfile = "primes.txt";

var out = getNPrimes(100).join(",");

fs.writeFileSync(outfile, out);
console.log("Script: " + __filename + "\nWrote: " + out + "To: " + outfile);

assert.deepEqual( getNPrimes(3), [2, 3, 5 ] );
assert.deepEqual( getNPrimes(10), [ 2, 3, 5, 7, 11, 13, 17, 19, 23, 29 ] );

function getNPrimes( N ) {

  var prime = 2,
      acc = [prime];

  for(var i=1;i<N;i++) {
    prime = nextPrime(prime);
    acc.push(prime)
  }

  return acc;
}

assert.equal( isPrime(1), true );
assert.equal( isPrime(2), true );
assert.equal( isPrime(4), false );
assert.equal( isPrime(6), false );
assert.equal( isPrime(1459), true );

function isPrime( number ) {
	if (number == 1 || number == 2) {
		return true;
	}

	var root = Math.sqrt(number);

	for (var i=2;i<=root;i++) {
		if (number % i == 0) {
			return false;
		}
	}

	return true;
}

assert.equal( nextPrime(1), 2 );
assert.equal( nextPrime(2), 3 );
assert.equal( nextPrime(3), 5 );
assert.equal( nextPrime(1453), 1459 );

function nextPrime( number ) {
	do {
    number = number + 1;
  } while (!isPrime(number));

  return number;
}
