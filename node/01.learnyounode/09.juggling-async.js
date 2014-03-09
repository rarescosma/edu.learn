/**
 * This problem is the same as the previous problem (HTTP COLLECT) in that you need to use http.get().
 * However, this time you will be provided with three URLs as the first three command-line arguments.
 *
 * You must collect the complete content provided to you by each of the URLs and print it to the console (stdout).
 * You don't need to print out the length, just the data as a String; one line per URL.
 * The catch is that you must print them out in the same order as the URLs are provided to you as command-line arguments.
 */

if (process.argv.length < 3) {
  throw new Error('Please specify at least one URL!');
}

var http = require('http');
var bl = require('bl');
var slice = Function.call.bind(Array.prototype.slice);

var queue = function (items, load, done) {
  var complete = 0;
  var results = [];

  items.map(function (url, index) {
    load(url, function (result) {
      complete++;
      results[index] = result;
      if (complete === items.length) {
        return done(results);
      }
    });
  });
};

var collectURL = function (url, done) {
  http.get(url, function (response) {
    response.pipe(bl(function (err, data) {
      if (err) { throw err; }

      return done(data.toString());
    }));
  });
};

queue(slice(process.argv, 2), collectURL, function (results) {
  results.forEach(function (result) {
    console.log(result);
  });
});
