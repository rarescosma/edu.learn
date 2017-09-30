/**
 * Use Array#filter to write a function called `getShortMessages`.
 *
 * `getShortMessages` takes an array of objects with '.message' properties
 * and prints any messages that are *less than < 50 characters long*.
 */

module.exports = function getShortMessages(messages) {
  return messages.filter(function (x) {
    return x.message.length < 50;
  }).map(function (x) {
    return x.message;
  });
};