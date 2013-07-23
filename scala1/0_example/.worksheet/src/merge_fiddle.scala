object merge_fiddle {

  class Tweet(val user: String, val text: String, val retweets: Int) {
    def this(retweets: Int) = this("", "", retweets)

    override def toString: String =
      //"User: " + user + "\n" +
      //  "Text: " + text + " [" + retweets + "]"
      "[" + retweets + "]"


    def <(that: Tweet): Boolean = retweets < that.retweets
  }

  abstract class TweetSet {

    def isEmpty: Boolean

    def filter(p: Tweet => Boolean): TweetSet = filterAcc(p, new Empty)
    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet

		def toList: List[Tweet]
		def toListAcc(acc: List[Tweet]): List[Tweet]
		
    def union(that: TweetSet): TweetSet

    def mostRetweeted: Tweet
    def mostRetweetedAcc(acc: Tweet): Tweet

    def incl(tweet: Tweet): TweetSet
    def remove(tweet: Tweet): TweetSet
    def contains(tweet: Tweet): Boolean
  }

  class Empty extends TweetSet {

    def isEmpty = true

    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet = acc

		def toList = List()
		def toListAcc(acc: List[Tweet]): List[Tweet] = acc
		
    def union(that: TweetSet): TweetSet = that

    def mostRetweeted: Tweet = throw new NoSuchElementException
    def mostRetweetedAcc(acc: Tweet): Tweet = acc

    def contains(tweet: Tweet): Boolean = false
    def incl(tweet: Tweet): TweetSet = new NonEmpty(tweet, new Empty, new Empty)
    def remove(tweet: Tweet): TweetSet = this
  }

  case class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {

    def isEmpty = false

    def filterAcc(p: Tweet => Boolean, acc: TweetSet): TweetSet =
      left.filterAcc(p, right.filterAcc(p, if (p(elem)) acc.incl(elem) else acc))

    def toList: List[Tweet] = {
    	toListAcc(Nil)
    }
    
    def toListAcc(acc: List[Tweet]): List[Tweet] = {
    	acc ++ List(elem) ++ left.toListAcc(acc) ++ right.toListAcc(acc)
    }

    def union(that: TweetSet): TweetSet = {
      left.union(right.union(if (that.contains(this.elem)) that else that.incl(this.elem)))
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
  };import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(2803); 
  
  val s1 = new NonEmpty(new Tweet(1), new Empty, new Empty);System.out.println("""s1  : merge_fiddle.NonEmpty = """ + $show(s1 ));$skip(275); 

  def merge[T](less: (T, T) => Boolean): (List[T], List[T]) => List[T] =
    (l1, l2) => {
      if (l1.isEmpty) l2
      else if (l2.isEmpty) l1
      else if (less(l1.head, l2.head)) l1.head :: merge(less)(l1.tail, l2)
      else l2.head :: merge(less)(l1, l2.tail)
    };System.out.println("""merge: [T](less: (T, T) => Boolean)(List[T], List[T]) => List[T]""");$skip(45); 

  val l1 = List(new Tweet(1), new Tweet(5));System.out.println("""l1  : List[merge_fiddle.Tweet] = """ + $show(l1 ));$skip(58); 
  val l2 = List(new Tweet(2), new Tweet(4), new Tweet(6));System.out.println("""l2  : List[merge_fiddle.Tweet] = """ + $show(l2 ));$skip(125); 
  val mergeTweetLists: (List[Tweet], List[Tweet]) => List[Tweet] =
    merge[Tweet]((t1, t2) => (t1.retweets < t2.retweets));System.out.println("""mergeTweetLists  : (List[merge_fiddle.Tweet], List[merge_fiddle.Tweet]) => List[merge_fiddle.Tweet] = """ + $show(mergeTweetLists ))}
}
