/**
 * In this challenge, we're going to implement a 'curry' function for an arbitrary number of arguments.
 *
 * `curryN` will take two parameters:
 * * fn: The function we want to curry.
 * * n: Optional number of arguments to curry.
 *
 * If not supplied, `curryN` should use the fn's arity as the value for `n`.
 */

var curryN = function (fn, n) {
  n = n || fn.length;

  function curried(args) {
    if (args.length >= n) {
      return fn.apply(null, args);
    }

    return function () {
      return curried(args.concat([].slice.apply(arguments)));
    };
  }

  return curried([]);
};

module.exports = curryN;
