package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Convenience logical OR function
   */
  def or(x: Boolean, y: Boolean) = {
    if (!x) y
    else true
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    if (or((c == 0), (c == r))) 1
    else pascal((c - 1), (r - 1)) + pascal(c, (r - 1))
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {

    def evalParanthesis(c: Char): Int = if (c == ')') -1 else if (c == '(') 1 else 0

    def loop(acc: Int, chars: List[Char]): Boolean =
      if (acc < 0) false
      else if (chars.isEmpty) (acc == 0)
      else loop((acc + evalParanthesis(chars.head)), chars.tail)

    loop(0, chars)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int], flag: Int = 0): Int = {
   
    if (money == 0) flag // Handle degenerate case of initial iteration with 0 amount
    else if (or((money < 0), (coins.isEmpty))) 0
    else {
      countChange(money - coins.head, coins, 1) + countChange(money, coins.tail, 1)
    }
  }
}
