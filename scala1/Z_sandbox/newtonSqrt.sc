package week1

object newtonSqrt {

  def sqrt(x: Double): Double = {

    def abs(x: Double): Double =
      if (x > 0) x else -x

    def sqrtIter(guess: Double, x: Double): Double =
      if (isGood(guess, x)) guess
      else sqrtIter(improve(guess, x), x)

    def isGood(guess: Double, x: Double): Boolean =
      abs((guess * guess) / x - 1) < 0.0000001

    def improve(guess: Double, x: Double): Double =
      (guess + x / guess) / 2

    sqrtIter(1, x)
  }                                               //> sqrt: (x: Double)Double
  
  sqrt(1e60)                                      //> res0: Double = 1.0000000031080746E30
}