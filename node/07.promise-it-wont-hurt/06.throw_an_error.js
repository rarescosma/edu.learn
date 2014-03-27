var q = require('q');

var parsePromised = function(str) {
  var def = q.defer(), parsed;

  try {
    parsed = JSON.parse(str);
  } catch(e) {
    def.reject(e);
  }

  def.resolve(parsed);
  return def.promise;
}

parsePromised(process.argv[2])
.then(null, console.log);