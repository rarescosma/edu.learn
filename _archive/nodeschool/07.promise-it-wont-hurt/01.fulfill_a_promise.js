var q = require('q');

var def = q.defer();

def.promise.then(console.log);

setTimeout(function() {
  def.resolve('RESOLVED!');
}, 300);

// setTimeout(def.resolve, 300, "RESOLVED!");