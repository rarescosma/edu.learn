package forcomp

object scratch {
  type Occurrences = List[(Char, Int)]
  type Word = String
  type Sentence = List[Word];import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(169); 

  val dictionary: List[Word] = loadDictionary;System.out.println("""dictionary  : List[forcomp.scratch.Word] = """ + $show(dictionary ));$skip(556); 

  /**
   * Converts the word into its character occurence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = {
    // First, let's extract letters, and letters only
    val letters = w.toLowerCase.toList filter (chr => chr.isLetter)
    val grouped = letters.groupBy((element: Char) => element).toSeq.sortBy(_._1)

    grouped.map(x => (x._1, x._2.length)).toList
  };System.out.println("""wordOccurrences: (w: forcomp.scratch.Word)forcomp.scratch.Occurrences""");$skip(161); 

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = {
    wordOccurrences(s mkString "")
  };System.out.println("""sentenceOccurrences: (s: forcomp.scratch.Sentence)forcomp.scratch.Occurrences""");$skip(748); 

  /**
   * The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
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
    dictionary.groupBy(wordOccurrences) withDefaultValue List()
  };System.out.println("""dictionaryByOccurrences: => Map[forcomp.scratch.Occurrences,List[forcomp.scratch.Word]]""");$skip(158); 

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    dictionaryByOccurrences(wordOccurrences(word))
  };System.out.println("""wordAnagrams: (word: forcomp.scratch.Word)List[forcomp.scratch.Word]""");$skip(301); 

  def subtract(x: Occurrences, y: Occurrences): Occurrences = {

    lazy val diffs = for {
      (chr, freq) <- x
      (chr2, freq2) <- y
      if chr == chr2
      if freq >= freq2
    } yield (chr, freq - freq2)

    (x.toMap ++ diffs.toMap).filter(o => (o._2 > 0)).toSeq.sortBy(_._1).toList
  };System.out.println("""subtract: (x: forcomp.scratch.Occurrences, y: forcomp.scratch.Occurrences)forcomp.scratch.Occurrences""");$skip(274); 

  def combinations(occurrences: Occurrences): List[Occurrences] = {
    lazy val subtrees = for {
      (chr, freq) <- occurrences
      i <- 0 until freq
    } yield subtract(occurrences, List((chr, i + 1)))

    occurrences :: subtrees.flatMap(combinations).distinct
  };System.out.println("""combinations: (occurrences: forcomp.scratch.Occurrences)List[forcomp.scratch.Occurrences]""");$skip(41); 
  
  val abba = List(('a', 2), ('b', 2));System.out.println("""abba  : List[(Char, Int)] = """ + $show(abba ));$skip(21); val res$0 = 
  combinations(abba);System.out.println("""res0: List[forcomp.scratch.Occurrences] = """ + $show(res$0));$skip(403); 

  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {

    def occurrenceAnagrams(o: Occurrences): List[Sentence] = o match {
      case Nil => List(Nil)
      case _ => combinations(o) flatMap (
        subtree => dictionaryByOccurrences(subtree) flatMap (
          w => occurrenceAnagrams(subtract(o, subtree)).map(w :: _)))
    }

    occurrenceAnagrams(sentenceOccurrences(sentence))
  };System.out.println("""sentenceAnagrams: (sentence: forcomp.scratch.Sentence)List[forcomp.scratch.Sentence]""");$skip(527); 
  
  def sentenceAnagramsMemo(sentence: Sentence): List[Sentence] = {
    val cache = collection.mutable.Map[Occurrences, List[Sentence]]()

    def occurrenceAnagrams(o: Occurrences): List[Sentence] = o match {
      case Nil => List(Nil)
      case _ => {
        cache.getOrElseUpdate(o, combinations(o) flatMap (
          subtree => dictionaryByOccurrences(subtree) flatMap (
            w => occurrenceAnagrams(subtract(o, subtree)).map(w :: _))))
      }
    }

    occurrenceAnagrams(sentenceOccurrences(sentence))
  };System.out.println("""sentenceAnagramsMemo: (sentence: forcomp.scratch.Sentence)List[forcomp.scratch.Sentence]""");$skip(36); 

  val snt = List("linux", "rulez");System.out.println("""snt  : List[String] = """ + $show(snt ))}
  //sentenceAnagramsMemo(snt)
}
