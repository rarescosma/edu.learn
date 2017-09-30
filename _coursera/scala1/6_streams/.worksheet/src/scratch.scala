object scratch {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(60); 
  println("Welcome to the Scala worksheet");$skip(71); 
  val a = Vector(Vector('S', 'T'), Vector('o', 'o'), Vector('o', 'o'));System.out.println("""a  : scala.collection.immutable.Vector[scala.collection.immutable.Vector[Char]] = """ + $show(a ));$skip(10); val res$0 = 
  a(0)(0);System.out.println("""res0: Char = """ + $show(res$0))}
  
}
