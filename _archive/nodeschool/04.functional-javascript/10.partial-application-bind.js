/**
 * Use Function#bind to implement a logging function that allows you to namespace messages.
 */

module.exports = function (namespace) {
  return console.log.bind(console, namespace);
};
