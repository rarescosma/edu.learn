/**
 * Write a function `duckCount` that returns the number of arguments passed to it
 * which have a property 'quack' defined directly on them.
 *
 * Do not match values inherited from prototypes.
 */

/**
 * Sandbox
 */
if (false) {
  var log = console.log;

  var p = Object.prototype;

  var duck = {
    quack: function () {
      console.log('quack');
    }
  };

  log(duck.hasOwnProperty('quack'));

  var object = Object.create(null);
  object.quack = function () {
    console.log('quack');
  };

  log(Object.getPrototypeOf(object) === Object.prototype); // => false
  log(Object.getPrototypeOf(object) === null);             // => true

  /**
   * Function#call allows us to invoke any function with al altered `this` value.
   */
  log(p.hasOwnProperty.call(object, 'quack'));

  var notDuck = Object.create({quack: true});
  var duck = {quack: true};
  log(p.hasOwnProperty.call(notDuck, 'quack'));
  log(p.hasOwnProperty.call(duck, 'quack'));
}

/**
 * Solution
 * @return int Number of arguments that quack like a duck
 */
function duckCount() {
  return Array.prototype.filter.call(arguments, function (arg) {
    return Object.prototype.hasOwnProperty.call(arg, 'quack');
  }).length;
}

module.exports = duckCount;