/**
 * Return a function that takes a list of valid users, and returns a function
 * that returns true if all of the supplied users exist in the original list of users.
 *
 * You only need to check that the ids match.
 */

module.exports = function checkUsersValid(goodUsers) {
  return function (submittedUsers) {
    return submittedUsers.every(function (submittedUser) {
      return goodUsers.some(function (user) {
        return user.id === submittedUser.id;
      });
    });
  };
};
