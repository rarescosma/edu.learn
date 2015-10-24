module.exports = function(db, date, cb) {
  var i = 0;
  db.createReadStream({start: date})
  .on('data', function(){ i++;})
  .on('error', function(err) {
    if(cb) {
      cb(err);
      cb = null;
    }
  })
  .on('end', function(){
    if(cb) {
      cb(null, i);
    }
  });
}