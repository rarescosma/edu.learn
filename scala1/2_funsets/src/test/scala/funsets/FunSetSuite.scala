package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }


  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("Union: union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  /**
   * Need more complex sets to test intersect
   */
  trait BiggerTestSets {
    val b1 = union(singletonSet(1), singletonSet(2))
    val b2 = union(singletonSet(2), singletonSet(3))
    val all = union(b1, b2)
  }

  test("Intersection: contains common elements") {
    new BiggerTestSets {
      val s = intersect(b1, b2)
      val s_prime = intersect(b2, b1)
      assert(contains(s, 2), "Intersect contains the common element.")
      assert(!contains(s, 1) && !contains(s, 3), "Intersect doesn't contain other elements.")
      assert( (s(1) == s_prime(1)) && (s(2) == s_prime(2)) && (s(3) == s_prime(3) ), "Intersect is commutative.")
    }
  }

  test("Diff") {
    new BiggerTestSets {
      val s = diff(b1, b2)
      val s_prime = diff(b2, b1)
      assert(contains(s, 1), "Diff contains the element in the first operand that isn't in last operand.")
      assert(!contains(s, 2) && !contains(s, 3), "Diff contains the element in the first operand that isn't in last operand.")
      assert(contains(s_prime, 3), "Diff contains the element in the first operand that isn't in last operand.")
      assert(!contains(s_prime, 1) && !contains(s_prime, 2), "Diff contains the element in the first operand that isn't in last operand.")
      assert( (s(1) != s_prime(1)) || (s(2) != s_prime(2)) || (s(3) != s_prime(3) ), "Diff is NOT commutative.")
    }
  }

  def interval(lower: Int, upper: Int): Set = (x: Int) => ( (x >= lower) && (x < upper) )

  test("Filter") {
    new BiggerTestSets {
      val p = (x: Int) => (x % 2 == 1)
      val filtered = filter(all, p)
      assert(contains(filtered, 1), "Filtered elements are odd")
      assert(contains(filtered, 3), "Filtered elements are odd")
      assert(!contains(filtered, 2), "Filtered elements are odd")
    }
  }

  test("Quantifiers") {

    val m6 = (x: Int) => ( x % 6 == 0 )
    val m2 = (x: Int) => ( x % 2 == 0 )
    val m3 = (x: Int) => ( x % 3 == 0 )

    val all = (x: Int) => true
    val one = singletonSet(1)
    val thousand_one = singletonSet(1001)
    val minus_thousand_one = singletonSet(-1001)

    assert(forall(m6,m2), "All multiples of 6 are multiples of 2")
    assert(forall(m6,m3), "All multiples of 6 are multiples of 3")

    assert(exists(all,one), "There exists 1 in the whole bounded set")
    assert(!exists(all,thousand_one), "There's no 1001 in the whole bounded set")
    assert(!exists(all,minus_thousand_one), "There's no -1001 in the whole bounded set")
  }

  test("Map") {
    new BiggerTestSets {
      val smooth = interval( -100, 100 )
      val odds = map(smooth,  x => 2*x+1)
      assert(!exists(odds, x => (x%2 == 0)), "There's no even in odds")
    }
  }

  trait AbstractSets {
    val nullSet = (x: Int) => false
    val allSet = (x: Int) => true
    val a = interval(0, 60)
    val b = interval(20, 80)
    val c = interval(40, 100)
  }

  test("Identity Laws") {
    new AbstractSets {
      // A set united with null is itself
      assert(forall(union(a, nullSet), a), "Union with null")
      assert(!exists(allSet, diff(union(a, nullSet), a)), "Union with null")

      // A set intersected with the domain is itself
      assert(forall(intersect(a, allSet), a), "Intersect with domain")
      assert(!exists(allSet, diff(intersect(a, allSet), a)), "Intersect with domain")
    }
  }

  test("Domination Laws") {
    new AbstractSets {
      // A set united with the domain is the domain
      assert(forall(union(a, allSet), allSet), "Union with domain")
      assert(!exists(allSet, diff(union(a, allSet), allSet)), "Union with domain")

      // A set intersected with the domain is itself
      assert(forall(intersect(a, nullSet), nullSet), "Intersect with null")
      assert(!exists(allSet, diff(intersect(a, nullSet), nullSet)), "Intersect with null")
    }
  }

  test("DeMorgans") {
    new AbstractSets {
      def l1 = diff(a, union(b, c))
      def r1 = intersect(diff(a, b), diff(a, c))

      assert((forall(l1, r1) && forall(r1, l1)), "1st DeMorgan")

      def l2 = diff(a, intersect(b, c))
      def r2 = union(diff(a, b), diff(a, c))

      assert((forall(l2, r2) && forall(r2, l2)), "2nd DeMorgan")
    }
  }
}
