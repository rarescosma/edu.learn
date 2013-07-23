object median {
  val z = List(1,2,3,4,5)                         //> z  : List[Int] = List(1, 2, 3, 4, 5)
	val l = z.length                          //> l  : Int = 5
	
	val m: Int = l / 2                        //> m  : Int = 2
	val r = z.takeRight(l-m)                  //> r  : List[Int] = List(3, 4, 5)
	z.take(m) ++ (r.head :: r.tail)           //> res0: List[Int] = List(1, 2, 3, 4, 5)
}