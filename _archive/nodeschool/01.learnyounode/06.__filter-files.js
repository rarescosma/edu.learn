/**
 * See 06.make-it-modular.js
 */
var fs = require('fs');
var path = require('path');

module.exports = function (dir, extension, callback) {
  fs.readdir(dir, function (err, files) {
    if (err) { return callback(err); }

    callback(null, files.filter(
      function (f) { return path.extname(f) === '.' + extension; }
    ));
  });
};