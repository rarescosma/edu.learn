/**
 * If you are NOT returning a value from your promise to a caller,
 * then attach a "done" handler to guard against uncaught exceptions.
 */

var q = require('q');

var throwMyGod = function() {
  throw new Error('OH NOES');
},
iterate = function(i) {
  console.log(i);
  return ++i;
}

q.fcall(iterate, 1)
.then(iterate)
.then(iterate)
.then(iterate)
.then(iterate)
.then(throwMyGod)
.then(iterate)
.then(iterate)
.then(null, throwMyGod)
.catch(console.log).done();