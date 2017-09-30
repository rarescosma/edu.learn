var level = require('level');
var fs = require('fs');
var split = require('split');
var through = require('through');
var concat = require('concat-stream');

level(process.argv[2], function (err, db) {
  if (err)
    throw err

  fs.createReadStream(process.argv[3])
    .pipe(split())
    .pipe(through(function(buf){
      var command = buf.toString().split(',');
      this.queue({type: command[0], key: command[1], value: command[2]});
    }))
    .pipe(concat(db.batch.bind(db)));
});
