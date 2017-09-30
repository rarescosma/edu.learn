/**
 * Override a specified method of an object with new functionality
 * while still maintaining all of the old behaviour.
 *
 * Create a spy that keeps track of how many times a function is called.
 */

function Spy(target, method) {
  var result = {
    count: 0
  };

  var f = target[method];

  target[method] = function () {
    result.count++;
    return f.apply(this, arguments);
  };

  return result;
}

module.exports = Spy;
