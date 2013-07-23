import scala.annotation.tailrec

object change {

	def or(x: Boolean, y: Boolean): Boolean = if (!x) y else true
                                                  //> or: (x: Boolean, y: Boolean)Boolean
	
  def countChange(money: Int, coins: List[Int], flag: Int = 0): Int = {
    if (money == 0) flag // Handle degenerate case of initial iteration with 0 amount
    else if (or((money < 0), (coins.isEmpty))) 0
    else countChange(money - coins.head, coins, 1) + countChange(money, coins.tail, 1)
  }                                               //> countChange: (money: Int, coins: List[Int], flag: Int)Int

  countChange(100, List(1,2,3,4,5,10,25))         //> res0: Int = 165771
}