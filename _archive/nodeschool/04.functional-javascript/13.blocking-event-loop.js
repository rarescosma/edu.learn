/**
 * Modify the recursive `repeat` function provided in the boilerplate,
 * such that it does not block the event loop (i.e. Timers and IO handlers can fire).
 *
 * This necessarily requires repeat to be asynchronous.
 */

function repeat(operation, num) {
  // modify this so it can be interrupted
  if (num <= 0) { return; }
  operation();

  if (num % 5 === 0) {
    setTimeout(function () {
      return repeat(operation, --num);
    }, 0);
  } else {
    repeat(operation, --num);
  }
}

module.exports = repeat;
