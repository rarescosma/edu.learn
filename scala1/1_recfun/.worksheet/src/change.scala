import scala.annotation.tailrec

object change {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(112); 

	def or(x: Boolean, y: Boolean): Boolean = if (!x) y else true;System.out.println("""or: (x: Boolean, y: Boolean)Boolean""");$skip(300); 
	
  def countChange(money: Int, coins: List[Int], flag: Int = 0): Int = {
    if (money == 0) flag // Handle degenerate case of initial iteration with 0 amount
    else if (or((money < 0), (coins.isEmpty))) 0
    else countChange(money - coins.head, coins, 1) + countChange(money, coins.tail, 1)
  };System.out.println("""countChange: (money: Int, coins: List[Int], flag: Int)Int""");$skip(43); val res$0 = 

  countChange(100, List(1,2,3,4,5,10,25));System.out.println("""res0: Int = """ + $show(res$0))}
}
