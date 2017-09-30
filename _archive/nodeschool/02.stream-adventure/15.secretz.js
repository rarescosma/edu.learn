/**
 * An encrypted, gzipped tar file will be piped in on process.stdin.
 * To beat this challenge, for each file in the tar input,
 * print a hex-encoded md5 hash of the file contents followed by a single space followed by the filename, then a newline.
 */

var combine = require('stream-combiner');
var fs = require('fs');
var zlib = require('zlib');
var tar = require('tar');
var crypto = require('crypto');
var through = require('through');

var parser = tar.Parse();
parser.on('entry', function (entry) {
  if (entry.type !== 'File') { return; }

  combine(
    entry,
    crypto.createHash('md5', { encoding: 'hex' }),
    through(null, function () { this.queue(' ' + entry.path + '\n'); })
  ).pipe(process.stdout);
});

combine(
  process.stdin,
  crypto.createDecipher(process.argv[2], process.argv[3]),
  zlib.createGunzip(),
  parser
);
