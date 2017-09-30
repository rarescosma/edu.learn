object for_expressions {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(79); 
  
  // 1. simple for-expression
  val e1 = List(1, 2);System.out.println("""e1  : List[Int] = """ + $show(e1 ));$skip(30); 
  def e2(x: Int): Int = x + 1;System.out.println("""e2: (x: Int)Int""");$skip(47); val res$0 = 
  
  // equivalence
  for(x <- e1) yield e2(x);System.out.println("""res0: List[Int] = """ + $show(res$0));$skip(21); val res$1 = 
  e1.map(x => e2(x));System.out.println("""res1: List[Int] = """ + $show(res$1));$skip(78); 
  
  
  // 2. filtered for-expression
  def f(x: Int): Boolean = (x % 2 == 0);System.out.println("""f: (x: Int)Boolean""");$skip(64); val res$2 = 
  
  // equivalence
  for(x <- e1 if f(x); y <- e1) yield e2(x);System.out.println("""res2: List[Int] = """ + $show(res$2));$skip(50); val res$3 = 
  for(x <- e1.withFilter(f); y <- e1) yield e2(x);System.out.println("""res3: List[Int] = """ + $show(res$3));$skip(66); 


	// 3. multiple generators for-expression
	val e22 = List(3, 4);System.out.println("""e22  : List[Int] = """ + $show(e22 ));$skip(62); val res$4 = 
	
	// equivalence
	for(x <- e1; y <- e22; y<- e1) yield e2(x);System.out.println("""res4: List[Int] = """ + $show(res$4));$skip(54); val res$5 = 
	e1.flatMap(x => for (y <- e22; y <- e1) yield e2(x))
                                                  
	// 4. exercise
	class book(val authors: List[String], val title: String);System.out.println("""res5: List[Int] = """ + $show(res$5));$skip(312); 
	val books = List(
		new book(List("Bird Man", "Nonbird Man"), "Good Book"),
		new book(List("Nonbird Man"), "Bad Book"),
		new book(List("Yolozaur", "Birdy Bird"), "Another Good Um")
	);System.out.println("""books  : List[for_expressions.book] = """ + $show(books ));$skip(72); val res$6 = 
	
	for(b <- books; a <- b.authors if a startsWith "Bird") yield b.title;System.out.println("""res6: List[String] = """ + $show(res$6));$skip(114); val res$7 = 
  // 3 & 2
 	books flatMap(b =>
 		for(a <- b.authors withFilter(a => a startsWith "Bird" ))
 		yield b.title
 	);System.out.println("""res7: List[String] = """ + $show(res$7));$skip(105); val res$8 = 
 	
 	// 1
 	books flatMap(b =>
 		b.authors withFilter (a => a startsWith "Bird") map (y => b.title)
 	);System.out.println("""res8: List[String] = """ + $show(res$8))}
}
