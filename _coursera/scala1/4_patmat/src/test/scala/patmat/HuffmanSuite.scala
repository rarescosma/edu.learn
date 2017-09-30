package patmat

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {
  trait TestTrees {
    val t1 = Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5)
    val t2 = Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('d',4), List('a','b','d'), 9)
  }

  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }

  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a','b','d'))
    }
  }

  test("makeCodeTree") {
    new TestTrees {
      assert(makeCodeTree(t1, Leaf('z', 14)) === Fork(Fork(Leaf('a',2), Leaf('b',3), List('a','b'), 5), Leaf('z',14), List('a', 'b', 'z'), 19))
    }
  }

  test("string2Chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("times for a list of characters") {
    assert(times(string2Chars("abracadabra")) === List(('a', 5), ('b', 2), ('r', 2), ('c', 1), ('d', 1)))
  }

  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e',1), Leaf('t',2), Leaf('x',3)))
  }

  test("singleton") {
    assert(singleton(List(Leaf('a',1))) === true)
    assert(singleton(List(Leaf('a',1), Leaf('b','2'))) === false)
    assert(singleton(List(Fork(Leaf('e',3),Leaf('t',4),List('e', 't'),7))) === true)
  }

  test("combine of some leaf list") {
    var leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))

    leaflist = List(Leaf('e', 3), Leaf('t', 4), Leaf('x', 5), Leaf('z', 8))
    assert(combine(leaflist) === List(Leaf('x',5), Fork(Leaf('e',3),Leaf('t',4),List('e', 't'),7), Leaf('z', 8)))

    assert(combine(Nil) === Nil)
    assert(combine(List(Leaf('x', 5))) === List(Leaf('x', 5)))
  }

  test("until") {
    assert(until(singleton, x => x.tail)(List(Leaf('e', 3), Leaf('t', 4), Leaf('x', 5), Leaf('z', 8))) === List(Leaf('z', 8)))
  }

  test("createCodeTree") {
    assert(createCodeTree(string2Chars("abacadabeafabgaha")) === Fork(Leaf('a',8),Fork(Fork(Fork(Leaf('g',1),Leaf('h',1),List('g', 'h'),2),Fork(Leaf('e',1),Leaf('f',1),List('e', 'f'),2),List('e', 'f', 'g', 'h'),4),Fork(Fork(Leaf('c',1),Leaf('d',1),List('c', 'd'),2),Leaf('b',3),List('b', 'c', 'd'),5),List('b', 'c', 'd', 'e', 'f', 'g', 'h'),9),List('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'),17))
  }

  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("abbbabbabbaaaabbbbaba".toList)) === "abbbabbabbaaaabbbbaba".toList)
      assert(decode(t1, quickEncode(t1)("abbbabbabbaaaabbbbaba".toList)) === "abbbabbabbaaaabbbbaba".toList)

      assert(decode(t2, encode(t2)("abdabbbddbaba".toList)) === "abdabbbddbaba".toList)
      assert(decode(t2, quickEncode(t2)("abdabbbddbaba".toList)) === "abdabbbddbaba".toList)
    }
  }

  test("convert") {
    new TestTrees {
      assert(convert(t2) === List(('a',List(0, 0)), ('b',List(0, 1)), ('d',List(1))))
    }
  }

  test("codeBits") {
    assert(codeBits(convert(frenchCode))('a') === List(1,1,1,1))
  }

  test("mergeCodeTables") {
    new TestTrees {
      assert(convert(t2) ++ List(('z',List(1,0,1,0,1,1))) === mergeCodeTables(convert(t2), List(('z',List(1,0,1,0,1,1)))))
    }
  }
}
