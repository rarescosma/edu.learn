package forcomp

object scratch {
  type Occurrences = List[(Char, Int)]
  type Word = String
  type Sentence = List[Word]

  val dictionary: List[Word] = loadDictionary     //> dictionary  : List[forcomp.scratch.Word] = List(Aarhus, Aaron, Ababa, aback,
                                                  //|  abaft, abandon, abandoned, abandoning, abandonment, abandons, abase, abased
                                                  //| , abasement, abasements, abases, abash, abashed, abashes, abashing, abasing,
                                                  //|  abate, abated, abatement, abatements, abater, abates, abating, Abba, abbe, 
                                                  //| abbey, abbeys, abbot, abbots, Abbott, abbreviate, abbreviated, abbreviates, 
                                                  //| abbreviating, abbreviation, abbreviations, Abby, abdomen, abdomens, abdomina
                                                  //| l, abduct, abducted, abduction, abductions, abductor, abductors, abducts, Ab
                                                  //| e, abed, Abel, Abelian, Abelson, Aberdeen, Abernathy, aberrant, aberration, 
                                                  //| aberrations, abet, abets, abetted, abetter, abetting, abeyance, abhor, abhor
                                                  //| red, abhorrent, abhorrer, abhorring, abhors, abide, abided, abides, abiding,
                                                  //|  Abidjan, Abigail, Abilene, abilities, ability, abject, abjection, abjection
                                                  //| s, abjectly, abjectness, abjure, abjured, abjures, abjuring, ablate, ablated
                                                  //| , ablates, ablating, abl
                                                  //| Output exceeds cutoff limit.

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
  }                                               //> wordOccurrences: (w: forcomp.scratch.Word)forcomp.scratch.Occurrences

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = {
    wordOccurrences(s mkString "")
  }                                               //> sentenceOccurrences: (s: forcomp.scratch.Sentence)forcomp.scratch.Occurrence
                                                  //| s

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
                                                  //> dictionaryByOccurrences: => Map[forcomp.scratch.Occurrences,List[forcomp.sc
                                                  //| ratch.Word]]
  }

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    dictionaryByOccurrences(wordOccurrences(word))
  }                                               //> wordAnagrams: (word: forcomp.scratch.Word)List[forcomp.scratch.Word]

  def subtract(x: Occurrences, y: Occurrences): Occurrences = {

    lazy val diffs = for {
      (chr, freq) <- x
      (chr2, freq2) <- y
      if chr == chr2
      if freq >= freq2
    } yield (chr, freq - freq2)

    (x.toMap ++ diffs.toMap).filter(o => (o._2 > 0)).toSeq.sortBy(_._1).toList
  }                                               //> subtract: (x: forcomp.scratch.Occurrences, y: forcomp.scratch.Occurrences)f
                                                  //| orcomp.scratch.Occurrences

  def combinations(occurrences: Occurrences): List[Occurrences] = {
    lazy val subtrees = for {
      (chr, freq) <- occurrences
      i <- 0 until freq
    } yield subtract(occurrences, List((chr, i + 1)))

    occurrences :: subtrees.flatMap(combinations).distinct
  }                                               //> combinations: (occurrences: forcomp.scratch.Occurrences)List[forcomp.scratc
                                                  //| h.Occurrences]
  
  val abba = List(('a', 2), ('b', 2))             //> abba  : List[(Char, Int)] = List((a,2), (b,2))
  combinations(abba)                              //> res0: List[forcomp.scratch.Occurrences] = List(List((a,2), (b,2)), List((a,
                                                  //| 1), (b,2)), List((b,2)), List((b,1)), List(), List((a,1), (b,1)), List((a,1
                                                  //| )), List((a,2), (b,1)), List((a,2)))

  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {

    def occurrenceAnagrams(o: Occurrences): List[Sentence] = o match {
      case Nil => List(Nil)
      case _ => combinations(o) flatMap (
        subtree => dictionaryByOccurrences(subtree) flatMap (
          w => occurrenceAnagrams(subtract(o, subtree)).map(w :: _)))
    }

    occurrenceAnagrams(sentenceOccurrences(sentence))
  }                                               //> sentenceAnagrams: (sentence: forcomp.scratch.Sentence)List[forcomp.scratch.
                                                  //| Sentence]
  
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
  }                                               //> sentenceAnagramsMemo: (sentence: forcomp.scratch.Sentence)List[forcomp.scra
                                                  //| tch.Sentence]

  val snt = List("linux", "rulez")                //> snt  : List[String] = List(linux, rulez)
  //sentenceAnagramsMemo(snt)
}