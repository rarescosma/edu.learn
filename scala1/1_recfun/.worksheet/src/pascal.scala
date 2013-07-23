object pascal {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(59); 
  println("Welcome to the Scala worksheet");$skip(170); 
  
  def main() {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  };System.out.println("""main: ()Unit""");$skip(242); 

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
  	def or(x: Boolean, y: Boolean) = {
  		if(!x) y
  		else true
  	}
  	
    if ( or( (c == 0), (c == r) ) ) 1
    else pascal( (c - 1), (r - 1) ) + pascal ( c, (r - 1))
  };System.out.println("""pascal: (c: Int, r: Int)Int""");$skip(12); 
  
  main()}
}
