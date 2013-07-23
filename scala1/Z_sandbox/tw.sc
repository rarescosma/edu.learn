object tw {

  class Tweet(val user: String, val text: String, val retweets: Int) {
    def this(retweets: Int) = this("", "", retweets)

    override def toString: String =
      "[" + retweets + "]"

    def <(that: Tweet): Boolean = retweets < that.retweets
  }

  abstract class TweetSet {

    def isEmpty: Boolean

    def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, Empty)
    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

    def toList: List[Tweet]

    def union(that: TweetSet): TweetSet

    def mostRetweeted: Tweet
    def mostRetweetedAcc(acc: Tweet): Tweet

    def incl(tweet: Tweet): TweetSet
    def remove(tweet: Tweet): TweetSet
    def contains(tweet: Tweet): Boolean
  }

  object Empty extends TweetSet {

    def isEmpty = true

    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

    def toList = List()

    def union(that: TweetSet): TweetSet = that

    def mostRetweeted: Tweet = throw new NoSuchElementException
    def mostRetweetedAcc(acc: Tweet): Tweet = acc

    def contains(tweet: Tweet): Boolean = false
    def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, Empty, Empty)
    def remove(tweet: Tweet): TweetSet = this
  }

  case class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

    def isEmpty = false

    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet =
      left.filterAcc(p, right.filterAcc(p, if (p(elem)) acc.incl(elem) else acc))

    def toList: List[Tweet] = {
      left.toList ++ List(elem) ++ right.toList
    }

    def union(that: TweetSet): TweetSet = {
      fromList(mergeTweetLists(this.toList, that.toList))
    }

    def mostRetweeted: Tweet = mostRetweetedAcc(elem)
    def mostRetweetedAcc(acc: Tweet): Tweet = {
      left.mostRetweetedAcc(right.mostRetweetedAcc(if (elem.retweets > acc.retweets) elem else acc))
    }

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
      
    def fromList(lst: List[Tweet]): TweetSet = {
      lst match {
        case Nil => Empty
        case h :: Nil => new NonEmpty(h, Empty, Empty)
        case _ => {
          val l = lst.length
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
      merge[Tweet]((t1, t2) => (t1.retweets < t2.retweets))
  }

}