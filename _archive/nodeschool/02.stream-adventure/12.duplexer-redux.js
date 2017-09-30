/**
 * Create an object to keep a count of all the countries in the input.
 *
 * Once the input ends, call `counter.setCounts()` with your country counts.
 */

var duplexer = require('duplexer');
var through = require('through');

module.exports = function (counter) {
  var counts = {};
  var input = through(function (o) {
    counts[o.country] = counts[o.country] || 0;
    counts[o.country]++;
  }, function () {
    counter.setCounts(counts);
  });

  return duplexer(input, counter);
};
