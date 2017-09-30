var q = require('q');

var def = q.defer();

var attachTitle = function(s) { return 'DR. ' + s; }

def.promise
.then(attachTitle)
.then(console.log);

def.resolve('MANHATTAN');
