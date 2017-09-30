/**
 * Read the first buffer from process.stdin, copy all bytes into a Uint8Array
 * and then log out a JSON stringified representation of the typed array.
 */

process.stdin.once('data', function (buf) { // using once() -> first buffer only
  console.log(JSON.stringify(new Uint8Array(buf)));
});