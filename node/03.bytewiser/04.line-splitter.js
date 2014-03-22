/**
 * The argument given to you from `process.argv[2]` will be a path to a file.
 *
 * Read this file and split it by newline characters ('\n'). You should log one Buffer per line.
 *
 * Bonus points if you never use `.toString()`.
 */

var fs = require('fs');

fs.readFile(process.argv[2], function (err, buf) {
  if (err) { throw err; }

  for (var c = 0, i = 0; i < buf.length ; i++) {
    if (10 === buf[i]) {
      console.log(buf.slice(c, i));
      c = i + 1;
    }
  }

  console.log(buf.slice(c, buf.length));
});
