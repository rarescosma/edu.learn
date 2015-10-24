var level = require('level');
var db = level(process.argv[2], { valueEncoding: 'json' });

var data = require(process.argv[3]);

db.batch(data.map(function(z){
  return {
    type: 'put',
    key: ('user' == z.type) ? z.name : z.user + '!' + z.name,
    value: z
  };
}));
