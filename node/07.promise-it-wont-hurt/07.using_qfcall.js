var q = require('q');

q.fcall(JSON.parse, process.argv[2])
.catch(console.log).done();
//.then(null, console.log)
