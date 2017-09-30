var level = require('level');
var db = level(process.argv[2]);
var pairs = JSON.parse(process.argv[3]);

for(key in pairs) {
  db.put(key, pairs[key]);
}
