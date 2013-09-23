import scala.collection.mutable
import scala.math

object Q2 extends App {

  def isNbitOne(bitNb: Int, nb: Int): Boolean = {
    nb / math.pow (2, bitNb).toInt % 2 != 0
  }

  def changeNiemBit(bitNb: Int, nb: Int): Int = {
    if (isNbitOne(bitNb,nb)){
      nb - math.pow (2, bitNb).toInt
    }
    else {
      nb + math.pow (2, bitNb).toInt
    }
  }

  def findOneFlipped (number: Int, bitSize: Int): List[Int] = {
    (0 until bitSize).map (x =>
      if (isNbitOne(x,number) ) {
        number - math.pow (2, x).toInt
      }
      else {
        number + math.pow (2, x).toInt
      }
    ).toList
  }

  def findTwoFlipped (number: Int, bitSize: Int): List[Int] = {
    val l:List[(Int,Int)] = for {
      i <- (0 until bitSize).toList
      j <- (0 until bitSize).toList.diff(0 until i+1 )
    } yield (i,j)

    l.map(x => {
      val nb = changeNiemBit(x._1,number)
      changeNiemBit(x._2,nb)
    })
  }

  def findFlipped(number:Int, bitSize: Int): List[Int] = {
    findOneFlipped(number,bitSize):::findTwoFlipped(number,bitSize)
  }

  def main() {
    val input = scala.io.Source.fromFile("../clustering_big.txt", "utf-8")
    val inputL = input.getLines.toList
    val splited_input = inputL.drop(1).map(_.replaceAll(" ", "")).map(Integer.parseInt(_, 2))
    val bitSize = inputL.head.split(" ")(1).toInt
    val zipped = splited_input.zip(Stream from 1)
    val hash = zipped.toMap
    val ds = new UnionFind[Int](splited_input.toArray)
    splited_input.foreach(v =>
    {
      val fl = findFlipped(v,bitSize)
      fl.foreach( f => {
        if (hash.contains(f)){
          ds.union(v, f)
        }
      })
    })
    println(ds.sizeUnion)
  }
  
  main
}