package recfun

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CountChangeSuite extends FunSuite {
  import Main.countChange
  test("countChange: example given in instructions") {
    assert(countChange(4,List(1,2)) === 3)
  }

  test("countChange: sorted CHF") {
    assert(countChange(300,List(5,10,20,50,100,200,500)) === 1022)
  }

  test("countChange: no pennies") {
    assert(countChange(301,List(5,10,20,50,100,200,500)) === 0)
  }

  test("countChange: unsorted CHF") {
    assert(countChange(300,List(500,5,50,100,20,200,10)) === 1022)
  }
  
  test("countChange: US coins") {
    assert(countChange(100,List(25,10,5,1)) === 242)
  }
  
  test("countChange: degenerate initial amount 0"){
    assert(countChange(0,List(1,2,3)) === 0)
  }
  
  test("countChange: degenerate initial coin list"){
    assert(countChange(100,List()) === 0)
  }
}
