object list_stuff {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(63); 
  println("Welcome to the Scala worksheet");$skip(18); 
  val x = List(1);System.out.println("""x  : List[Int] = """ + $show(x ));$skip(36); 
  val(h1, h2) = x.splitAt(x.size/2);System.out.println("""h1  : List[Int] = """ + $show(h1 ));System.out.println("""h2  : List[Int] = """ + $show(h2 ));$skip(5); val res$0 = 
  h1;System.out.println("""res0: List[Int] = """ + $show(res$0))}
}
