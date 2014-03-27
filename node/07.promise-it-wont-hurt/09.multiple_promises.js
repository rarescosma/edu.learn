var q = require('q');

function all() {
  var promises = Array.prototype.slice.call(arguments)
    def = q.defer(),
    i = 0, values = [];

  var fulfill = function (val) {
    values[i++] = val;
    if (i === promises.length) {
      def.resolve(values);
    }
  }

  promises.map(function(promise){
    promise.then(fulfill).then(null, def.reject);
  });

  return def.promise;
}

var p1 = q.defer(), p2 = q.defer();
all(p1.promise, p2.promise).then(console.log);

setTimeout(function(){
  p1.resolve('PROMISES');
  p2.resolve('FTW');
}, 200);