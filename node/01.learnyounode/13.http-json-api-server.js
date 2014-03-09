/**
 * Write an HTTP server that serves JSON data when it receives a GET request to the path '/api/parsetime'.
 * Expect the request to contain a query string with a key 'iso' and an ISO-format time as the value.
 *
 * For example: /api/parsetime?iso=2013-08-10T12:10:15.474Z
 *
 * The JSON response should contain only 'hour', 'minute' and 'second' properties. For example:
 *  {
 *    "hour": 14,
 *    "minute": 23,
 *    "second": 15
 *  }
 *
 * Add second endpoint for the path '/api/unixtime' which accepts the same query string
 * but returns UNIX epoch time under the property 'unixtime'. For example:
 *
 * { "unixtime": 1376136615474 }
 *
 * Your server should listen on the port provided by the first argument to your program.
 */

if (process.argv.length < 3) {
  throw new Error('Please specify a port!');
}

var http = require('http');
var url = require('url');

var routes = {
  '/api/parsetime' : function (query) {
    if (!query.iso) {
      return null;
    }

    var date = new Date(query.iso);

    return {
      hour: date.getHours(),
      minute: date.getMinutes(),
      second: date.getSeconds()
    };
  },
  '/api/unixtime' : function (query) {
    if (!query.iso) {
      return null;
    }

    var date = new Date(query.iso);

    return { unixtime: date.getTime() };
  }
};

var server = http.createServer(function (request, response) {
  if (request.method !== 'GET') {
    return response.end('please GET\n');
  }

  var parsed = url.parse(request.url, true);

  if (!routes.hasOwnProperty(parsed.pathname)) {
    response.writeHead(404);
    return response.end('invalid endpoint');
  }

  response.writeHead(200, { 'Content-Type': 'application/json' });
  return response.end(JSON.stringify(routes[parsed.pathname](parsed.query)));
});

server.listen(Number(process.argv[2]));
