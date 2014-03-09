/**
 * Implement a recursive function that returns all of the unique dependencies,
 * and sub-dependencies of a module, sorted alphabetically.
 *
 * Dependencies should be printed as dependency@version e.g. 'inflection@1.2.6'.
 *
 * Multiple versions of the same module are allowed, but duplicates modules of the same version should be removed.
 */

function getDependencies(tree, acc) {
  acc = acc || [];
  var deps = tree.dependencies || [];

  Object.keys(deps).map(function (k) {
    var key = k + '@' + tree.dependencies[k].version;
    if (acc.indexOf(key) === -1) {
      acc.push(key);
    }
    getDependencies(tree.dependencies[k], acc);
  });

  return acc.sort();
}

module.exports = getDependencies;
