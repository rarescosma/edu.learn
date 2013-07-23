import scala.annotation.tailrec

object balance {;import org.scalaide.worksheet.runtime.library.WorksheetSupport._; def main(args: Array[String])=$execute{;$skip(402); 

  def balance(chars: List[Char]): Boolean = {

    def evalParanthesis(c: Char): Int = if (c == ')') -1 else if (c == '(') 1 else 0

		@tailrec
    def loop(acc: Int, chars: List[Char]): Boolean =
      if (acc < 0) false
      else if (chars.isEmpty) (acc == 0)
      else loop((acc + evalParanthesis(chars.head)), chars.tail)

    loop(0, chars)
  };System.out.println("""balance: (chars: List[Char])Boolean""");$skip(32); val res$0 = 

  balance("(()())(())".toList);System.out.println("""res0: Boolean = """ + $show(res$0))}
}
