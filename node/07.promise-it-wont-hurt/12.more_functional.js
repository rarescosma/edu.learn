var qhttp = require("q-io/http")
  _ = require('lodash');

var dbPath = _.bind(String.prototype.concat, "http://localhost:7001/");

qhttp.read("http://localhost:7000")
.then(_.compose(qhttp.read, dbPath))
.then(_.compose(console.log, JSON.parse))
.catch(console.error).done();