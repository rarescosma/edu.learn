package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  // If a single element is inserted into a heap,
  // then the findMin operation should fetch it
  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  // Inserting two elements into an empty heap
  // and finding the minimum
  // should yield the smallest element
  // => b2, b3
  property("min2") = forAll { (a: Int, b: Int) =>    
    val h = insert(b, insert(a, empty))
    val min = scala.math.min(a, b)
    val max = scala.math.max(a, b)
    findMin(h) == min && findMin(deleteMin(h)) == max
  }
  
  // Reinserting the minimum into non-empty heaps
  // and performing findMin
  // should yield the same element again
  // => b1, b2
  property("gen1") = forAll { (h: H) =>
          val m = if (isEmpty(h)) 0 else findMin(h)
          findMin(insert(m, h)) == m
  }
  
  // Repeated extractions of the minimum element
  // should yield an ordered sequence
  // => b1, b2, b5
  property("sort1") = forAll { h: H => 
    def checkMin(heap: H): Boolean = {
      val next_h = if(isEmpty(h)) empty else deleteMin(heap)
      
      isEmpty(heap) || 
      isEmpty(next_h) ||
      (findMin(heap) <= findMin(next_h) && checkMin(next_h))
    }
    checkMin(h)
  }
  
  // Inserting an arbitrary sequence into heaps
  // then performing repeated extractions
  // should yield the sorted sequence
  property("sort2") = forAll { l: List[Int] =>
  	def insertList(heap: H, l: List[Int]): H =
  	  l match {
  	    case Nil => heap
  	    case head :: tail => insertList(insert(head, heap), tail)
  	  }
  	
  	def checkList(heap: H, l: List[Int]): Boolean =
  	  isEmpty(heap) ||
  	  (findMin(heap) == l.head && checkList(deleteMin(heap), l.tail))
  	    	
  	checkList(insertList(empty,l), l.sorted)
  }
  
  // Sort property with arbitrary heaps AND lists
  property("sort3") = forAll { (h: H, l: List[Int]) =>
    def insertList(heap: H, l: List[Int]): H =
  	  l match {
  	    case Nil => heap
  	    case head :: tail => insertList(insert(head, heap), tail)
  	  }
    
  	def extractList(heap: H): List[Int] =
  	  if (isEmpty(heap)) Nil else findMin(heap) :: extractList(deleteMin(heap))
  	  
  	(l ++ extractList(h)).sorted == extractList(insertList(h, l))
  }
  

  // Implement Heap Generator
  lazy val genHeap: Gen[H] = for {    
    v <- arbitrary[Int]
    heap <- oneOf(value(empty), genHeap)
  } yield insert(v, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
}
