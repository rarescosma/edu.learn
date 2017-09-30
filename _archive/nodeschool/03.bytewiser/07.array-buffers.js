/**
 * Take the integer from process.argv[2] and write it as the first element in a single element Uint32Array.
 *
 * Then create a Uint16Array from the Array Buffer of the Uint32Array and
 * log out to the console the JSON stringified version of the Uint16Array.
 */

var ui32 = new Uint32Array([+process.argv[2]]);

var ui16 = new Uint16Array(ui32.buffer);

console.log(JSON.stringify(ui16));
