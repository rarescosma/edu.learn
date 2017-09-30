/**
 * Write a program that takes the first buffer written to `process.stdin`,
 * updates all instances of . with ! and then logs out the updated buffer object.
 *
 * Bonus points if you never call `.toString()` on your buffer!
 */

process.stdin.on('data', function (buf) {
  for (var i = 0; i < buf.length; i++) {
    if (46 === buf[i]) { buf.write('!', i); }
  }
  console.log(buf);
});