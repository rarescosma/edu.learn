/**
 * The callback should be called with all the users loaded.
 *
 * The order of the users should match the order of supplied user ids.
 * Because this function is asynchronous, we do not care about its return value.
 */

function loadUsers(userIds, load, done) {
  var complete = 0;
  var users = [];

  userIds.map(function (id, index) {
    load(id, function (user) {
      complete++;
      users[index] = user;
      if (complete === userIds.length) {
        return done(users);
      }
    });
  });
}

module.exports = loadUsers;
