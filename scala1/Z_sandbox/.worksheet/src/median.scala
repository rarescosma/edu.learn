object median {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(41); 
  val z = List(1,2,3,4,5);System.out.println("""z  : List[Int] = """ + $show(z ));$skip(18); 
	val l = z.length;System.out.println("""l  : Int = """ + $show(l ));$skip(22); 
	
	val m: Int = l / 2;System.out.println("""m  : Int = """ + $show(m ));$skip(26); 
	val r = z.takeRight(l-m);System.out.println("""r  : List[Int] = """ + $show(r ));$skip(33); val res$0 = 
	z.take(m) ++ (r.head :: r.tail);System.out.println("""res0: List[Int] = """ + $show(res$0))}
}
