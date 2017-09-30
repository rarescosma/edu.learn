object for_expressions {
  
  // 1. simple for-expression
  val e1 = List(1, 2)                             //> e1  : List[Int] = List(1, 2)
  def e2(x: Int): Int = x + 1                     //> e2: (x: Int)Int
  
  // equivalence
  for(x <- e1) yield e2(x)                        //> res0: List[Int] = List(2, 3)
  e1.map(x => e2(x))                              //> res1: List[Int] = List(2, 3)
  
  
  // 2. filtered for-expression
  def f(x: Int): Boolean = (x % 2 == 0)           //> f: (x: Int)Boolean
  
  // equivalence
  for(x <- e1 if f(x); y <- e1) yield e2(x)       //> res2: List[Int] = List(3, 3)
  for(x <- e1.withFilter(f); y <- e1) yield e2(x) //> res3: List[Int] = List(3, 3)


	// 3. multiple generators for-expression
	val e22 = List(3, 4)                      //> e22  : List[Int] = List(3, 4)
	
	// equivalence
	for(x <- e1; y <- e22; y<- e1) yield e2(x)//> res4: List[Int] = List(2, 2, 2, 2, 3, 3, 3, 3)
	e1.flatMap(x => for (y <- e22; y <- e1) yield e2(x))
                                                  //> res5: List[Int] = List(2, 2, 2, 2, 3, 3, 3, 3)
                                                  
	// 4. exercise
	class book(val authors: List[String], val title: String)
	val books = List(
		new book(List("Bird Man", "Nonbird Man"), "Good Book"),
		new book(List("Nonbird Man"), "Bad Book"),
		new book(List("Yolozaur", "Birdy Bird"), "Another Good Um")
	)                                         //> books  : List[for_expressions.book] = List(for_expressions$$anonfun$main$1$b
                                                  //| ook$1@f4f44a, for_expressions$$anonfun$main$1$book$1@1d256fa, for_expression
                                                  //| s$$anonfun$main$1$book$1@4c4975)
	
	for(b <- books; a <- b.authors if a startsWith "Bird") yield b.title
                                                  //> res6: List[String] = List(Good Book, Another Good Um)
  // 3 & 2
 	books flatMap(b =>
 		for(a <- b.authors withFilter(a => a startsWith "Bird" ))
 		yield b.title
 	)                                         //> res7: List[String] = List(Good Book, Another Good Um)
 	
 	// 1
 	books flatMap(b =>
 		b.authors withFilter (a => a startsWith "Bird") map (y => b.title)
 	)                                         //> res8: List[String] = List(Good Book, Another Good Um)
}