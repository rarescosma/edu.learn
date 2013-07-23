package objsets

import common._
import annotation._
import TweetReader._

/**
 * A class to represent tweets.
 */
class Tweet(val user: String, val text: String, val retweets: Int) {
  override def toString: String =
    "User: " + user + "\n" +
    "Text: " + text + " [" + retweets + "]"

  def <(that: Tweet): Boolean = text < that.text
}


/**
 * This represents a set of objects of type `Tweet` in the form of a binary search
 * tree. Every branch in the tree has two children (two `TweetSet`s). There is an
 * invariant which always holds: for every branch `b`, all elements in the left
 * subtree are smaller than the tweet at `b`. The eleemnts in the right subtree are
 * larger.
 *
 * Note that the above structure requires us to be able to compare two tweets (we
 * need to be able to say which of two tweets is larger, or if they are equal). In
 * this implementation, the equality / order of tweets is based on the tweet's text
 * (see `def incl`). Hence, a `TweetSet` could not contain two tweets with the same
 * text from different users.
 *
 *
 * The advantage of representing sets as binary search trees is that the elements
 * of the set can be found quickly. If you want to learn more you can take a look
 * at the Wikipedia page [1], but this is not necessary in order to solve this
 * assignment.
 *
 * [1] http://en.wikipedia.org/wiki/Binary_search_tree
 */
abstract class TweetSet {

  /**
   * This method takes a predicate and returns a subset of all the elements
   * in the original set for which the predicate is true.
   *
   * Question: Can we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty)



  /**
   * This is a helper method for `filter` that propagates the accumulated tweets.
   */
  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet


  /**
   * Returns a new `TweetSet` that is the union of `TweetSet`s `this` and `that`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def union(that: TweetSet): TweetSet
  def toList: List[Tweet]


  /**
   * Returns the tweet from this set which has the greatest retweet count.
   *
   * Calling `mostRetweeted` on an empty set should throw an exception of
   * type `java.util.NoSuchElementException`.
   *
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def mostRetweeted: Tweet
  def mostRetweetedAcc(acc: Tweet): Tweet

  /**
   * Returns a list containing all tweets of this set, sorted by retweet count
   * in descending order. In other words, the head of the resulting list should
   * have the highest retweet count.
   *
   * Hint: the method `remove` on TweetSet will be very useful.
   * Question: Should we implment this method here, or should it remain abstract
   * and be implemented in the subclasses?
   */
  def descendingByRetweet: TweetList
  def descendingByRetweetAcc(xs: TweetList): TweetList


  /**
   * The following methods are already implemented
   */

  /**
   * Returns a new `TweetSet` which contains all elements of this set, and the
   * the new element `tweet` in case it does not already exist in this set.
   *
   * If `this.contains(tweet)`, the current set is returned.
   */
  def incl(tweet: Tweet): TweetSet

  /**
   * Returns a new `TweetSet` which excludes `tweet`.
   */
  def remove(tweet: Tweet): TweetSet

  /**
   * Tests if `tweet` exists in this `TweetSet`.
   */
  def contains(tweet: Tweet): Boolean

  /**
   * This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit

  def len: Int
}

class Empty extends TweetSet {

  def len = 0

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

  def union(that: TweetSet): TweetSet = that
  def toList = List()

  /**
   * Retweet methods
   */
  def mostRetweeted: Tweet = throw new NoSuchElementException
  def mostRetweetedAcc(acc: Tweet): Tweet = acc

  def descendingByRetweet = Nil
  def descendingByRetweetAcc(xs: TweetList): TweetList = xs


  /**
   * The following methods are already implemented
   */

  def contains(tweet: Tweet): Boolean = false

  def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)

  def remove(tweet: Tweet): TweetSet = this

  def foreach(f: Tweet => Unit): Unit = ()
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {
  def len = 1 + left.len + right.len

  def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet =
    left.filterAcc(p, right.filterAcc(p, if (p(elem)) acc.incl(elem) else acc))

  def toList: List[Tweet] = {
    left.toList ++ List(elem) ++ right.toList
  }

  def union(that: TweetSet): TweetSet = {
    fromList(mergeTweetLists(this.toList, that.toList))
  }

  /**
   * Retweet methods
   */
  def mostRetweeted: Tweet = mostRetweetedAcc(elem)

  def mostRetweetedAcc(acc: Tweet): Tweet = {
    left.mostRetweetedAcc(right.mostRetweetedAcc(if (elem.retweets > acc.retweets) elem else acc))
  }

  def descendingByRetweet: TweetList = this.descendingByRetweetAcc(Nil)
  def descendingByRetweetAcc(xs: TweetList): TweetList = {
    val mr = mostRetweeted
    new Cons(mr, remove(mr).descendingByRetweetAcc(xs))
  }

  /**
   * The following methods are already implemented
   */

  def contains(x: Tweet): Boolean =
    if (x < elem) left.contains(x)
    else if (elem < x) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    if (x < elem) new NonEmpty(elem, left.incl(x), right)
    else if (elem < x) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def remove(x: Tweet): TweetSet =
    if (x < elem) new NonEmpty(elem, left.remove(x), right)
    else if (elem < x) new NonEmpty(elem, left, right.remove(x))
    else left.union(right)

  def foreach(f: Tweet => Unit): Unit = {
    f(elem)
    left.foreach(f)
    right.foreach(f)
  }

  def fromList(lst: List[Tweet]): TweetSet = {
    if(lst.isEmpty) new Empty
    else {
      val l = lst.length
      if(l == 1) new NonEmpty(lst.head, new Empty, new Empty)
      else {
        val m = l / 2
        val right = lst.takeRight(l - m)
        new NonEmpty(right.head, fromList(lst.take(m)), fromList(right.tail))
      }
    }
  }

  def merge[T](less: (T, T) => Boolean): (List[T], List[T]) => List[T] =
    (l1, l2) => {
      if (l1.isEmpty) l2
      else if (l2.isEmpty) l1
      else if (less(l1.head, l2.head)) l1.head :: merge(less)(l1.tail, l2)
      else l2.head :: merge(less)(l1, l2.tail)
    }

  val mergeTweetLists: (List[Tweet], List[Tweet]) => List[Tweet] =
    merge[Tweet]((t1, t2) => (t1 < t2))
}


trait TweetList {
  def head: Tweet
  def tail: TweetList
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit =
    if (!isEmpty) {
      f(head)
      tail.foreach(f)
    }
}

object Nil extends TweetList {
  def head = throw new java.util.NoSuchElementException("head of EmptyList")
  def tail = throw new java.util.NoSuchElementException("tail of EmptyList")
  def isEmpty = true
}

class Cons(val head: Tweet, val tail: TweetList) extends TweetList {
  def isEmpty = false
}


object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  lazy val theTweets = TweetReader.allTweets

  lazy val googleTweets: TweetSet = theTweets.filter((t) => google.exists((x) => t.text.contains(x)))
  lazy val appleTweets: TweetSet = theTweets.filter((t) => apple.exists((x) => t.text.contains(x)))

  /**
   * A list of all tweets mentioning a keyword from either apple or google,
   * sorted by the number of retweets.
   */
  lazy val trending: TweetList = appleTweets.union(googleTweets).descendingByRetweet
}

object Main extends App {
  // Print the trending tweets
  GoogleVsApple.trending foreach println
}
