object scratch {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val a = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'))
                                                  //> a  : scala.collection.immutable.Vector[scala.collection.immutable.Vector[Cha
                                                  //| r]] = Vector(Vector(S, T), Vector(o, o), Vector(o, o))
  a(0)(0)                                         //> res0: Char = S
  
}