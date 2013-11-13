object list_stuff {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val x = List(1)                                 //> x  : List[Int] = List(1)
  val(h1, h2) = x.splitAt(x.size/2)               //> h1  : List[Int] = List()
                                                  //| h2  : List[Int] = List(1)
  h1                                              //> res0: List[Int] = List()
}