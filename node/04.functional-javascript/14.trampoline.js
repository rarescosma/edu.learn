/**
 * Modify the boilerplate below such that it uses a trampoline to continuously call itself synchronously.
 *
 * You can assume that the operation passed to repeat does not take arguments (or they are already bound to the function)
 * and the return value is not important.
 */

function repeat(operation, num) {
  // Modify this so it doesn't cause a stack overflow!
  if (num <= 0) { return; }

  return function () {
    operation();
    return repeat(operation, --num);
  };
}

function trampoline(fn) {
  var res = fn.apply(fn);

  while ('function' === typeof res) {
    res = res();
  }

  return res;
}

module.exports = function (operation, num) {
  // You probably want to call your trampoline here!
  return trampoline(repeat(operation, num));
};
