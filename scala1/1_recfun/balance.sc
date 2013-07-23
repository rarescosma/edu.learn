import scala.annotation.tailrec

object balance {

  def balance(chars: List[Char]): Boolean = {

    def evalParanthesis(c: Char): Int = if (c == ')') -1 else if (c == '(') 1 else 0

		@tailrec
    def loop(acc: Int, chars: List[Char]): Boolean =
      if (acc < 0) false
      else if (chars.isEmpty) (acc == 0)
      else loop((acc + evalParanthesis(chars.head)), chars.tail)

    loop(0, chars)
  }                                               //> balance: (chars: List[Char])Boolean

  balance("(()())(())".toList)                    //> res0: Boolean = true
}