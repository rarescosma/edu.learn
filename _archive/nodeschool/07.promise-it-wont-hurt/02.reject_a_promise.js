var q = require('q');

var def = q.defer();

def.promise.then(null, function(err){
  console.log(err.message);
});

setTimeout(function() {
  def.reject(new Error('REJECTED!'));
}, 300);
