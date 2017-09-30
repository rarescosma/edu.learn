import common._

object scratch {
		type Occurrences = List[(Char, Int)]
		type Word = String
		type Sentence = List[Word];import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(173); 
		
		
	val dictionary: List[Word] = loadDictionary;System.out.println("""dictionary  : List[scratch.Word] = """ + $show(dictionary ));$skip(554); 

  /** Converts the word into its character occurence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = {
    // First, let's extract letters, and letters only
    val letters = w.toLowerCase.toList filter(chr => chr.isLetter)
    val grouped = letters.groupBy((element: Char) => element).toSeq.sortBy(_._1)
    
    grouped.map(x => (x._1, x._2.length)).toList
  };System.out.println("""wordOccurrences: (w: scratch.Word)scratch.Occurrences""");$skip(164); 
  

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = {
    wordOccurrences(s mkString "")
  };System.out.println("""sentenceOccurrences: (s: scratch.Sentence)scratch.Occurrences""");$skip(719); 

  /** The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = {
    dictionary.groupBy(wordOccurrences)
  };System.out.println("""dictionaryByOccurrences: => Map[scratch.Occurrences,List[scratch.Word]]""");$skip(233); 

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    dictionaryByOccurrences get wordOccurrences(word) match {
      case None => List()
      case Some(words) => words
    }
  };System.out.println("""wordAnagrams: (word: scratch.Word)List[scratch.Word]""");$skip(313); 
	
 		
	def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    
    lazy val diffs = for{
      (chr, freq) <- x
      (chr2, freq2) <- y
      if chr == chr2
      if freq >= freq2
    } yield (chr, freq - freq2)
    
    (x.toMap ++ diffs.toMap).filter(o => ( o._2 > 0)).toSeq.sortBy(_._1).toList
  };System.out.println("""subtract: (x: scratch.Occurrences, y: scratch.Occurrences)scratch.Occurrences""");$skip(239); 
  
  def combinations(o: Occurrences): List[Occurrences] = {
  	val subtrees = for {
  		(chr, freq) <- o
  		i <- 0 until freq
	  } yield subtract(o, List((chr, i + 1)))
	
	  (o :: subtrees) ++ subtrees.flatMap(combinations) distinct
  };System.out.println("""combinations: (o: scratch.Occurrences)List[scratch.Occurrences]""");$skip(178); 
    
  def occurrenceToBogusWord(o: Occurrences): Word = {
    o match {
      case List() => ""
      case h :: t => (h._1).toString * h._2 + occurrenceToBogusWord(t)
    }
  };System.out.println("""occurrenceToBogusWord: (o: scratch.Occurrences)scratch.Word""");$skip(41); 
  
  val abba = List(('a', 2), ('b', 2));System.out.println("""abba  : List[(Char, Int)] = """ + $show(abba ));$skip(30); val res$0 = 
  occurrenceToBogusWord(abba);System.out.println("""res0: scratch.Word = """ + $show(res$0));$skip(189); 
  
  
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    if (sentence.isEmpty) List()
    else if (sentence.length == 1) wordAnagrams(sentence.head)
        
    List()
  };System.out.println("""sentenceAnagrams: (sentence: scratch.Sentence)List[scratch.Sentence]""")}
}
