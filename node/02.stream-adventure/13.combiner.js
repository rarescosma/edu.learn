/**
 * Your program should generate a newline-separated list of JSON lines of genres, each with a `"books"` array containing all the books in that genre.
 */

var combine = require('stream-combiner');
var split = require('split');
var through = require('through');
var zlib = require('zlib');


module.exports = function () {
  var genre = null, books = [], current = function() {
    return JSON.stringify({
      name: genre,
      books: books
    }) + '\n';
  };

  return combine(
    split(),
    through(function (line){
      if (line.length === 0) return;

      var obj = JSON.parse(line);

      if('genre' == obj.type) {
        if(books.length) {
          this.queue(current());
        }

        genre = obj.name;
        books = [];
      } else {
        books.push(obj.name);
      }
    }, function() {
      this.queue(current());
      this.queue(null); // Make sure we close the stream
    }),
    zlib.createGzip()
  );
}
