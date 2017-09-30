package simulations

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CircuitSuite extends CircuitSimulator with FunSuite {
  val InverterDelay = 1
  val AndGateDelay = 3
  val OrGateDelay = 5

  test("andGate example") {
    val in1, in2, out = new Wire
    andGate(in1, in2, out)
    in1.setSignal(false)
    in2.setSignal(false)
    run
    assert(out.getSignal === false, "and 00")

    in1.setSignal(true)
    run
    assert(out.getSignal === false, "and 10")

    in2.setSignal(true)
    run
    assert(out.getSignal === true, "and 11")

    in1.setSignal(false)
    run
    assert(out.getSignal === false, "and 01")
  }

  test("orGate example") {
    val in1, in2, out = new Wire
    orGate(in1, in2, out)
    in1.setSignal(false)
    in2.setSignal(false)
    run
    assert(out.getSignal === false, "or 00")

    in1.setSignal(true)
    run
    assert(out.getSignal === true, "or 10")

    in2.setSignal(true)
    run
    assert(out.getSignal === true, "or 11")

    in1.setSignal(false)
    run
    assert(out.getSignal === true, "or 01")
  }

  test("orGate2 example") {
    val in1, in2, out = new Wire
    orGate2(in1, in2, out)
    in1.setSignal(false)
    in2.setSignal(false)
    run
    assert(out.getSignal === false, "or 00")

    in1.setSignal(true)
    run
    assert(out.getSignal === true, "or 10")

    in2.setSignal(true)
    run
    assert(out.getSignal === true, "or 11")

    in1.setSignal(false)
    run
    assert(out.getSignal === true, "or 01")
  }

  test("demux0") {
    val in, out = new Wire
    demux(in, Nil, List(out))

    in.setSignal(true)
    run

    assert(out.getSignal === true, "null demux, in=1")

    in.setSignal(false)
    run

    assert(out.getSignal === false, "null demux, in=0")
  }

  def signals(wires: List[Wire]): List[Boolean] = {
    wires map { (w) => w.getSignal }
  }

  test("demuxN") {

    val num_c = 8
    val num_o = Math.pow(2, num_c).toInt

    val in = new Wire
    val c = List.fill(num_c)(new Wire)
    val out = List.fill(num_o)(new Wire)

    demux(in, c, out)

    in.setSignal(true)

    for (addr <- 1 to num_o) {
      var x = (addr - 1).toBinaryString.toCharArray.toList map {
        case '1' => true
        case '0' => false
      }

      if (x.size < num_c){
        x = List.fill(num_c - x.size){ false } ::: x
      }

      for(z <- 0 to num_c - 1) {
        c(z).setSignal(x(z))
      }
      run

      val expected = List.fill(num_o - addr){false} ::: (true :: List.fill(addr - 1){false})
      assert(signals(out) == expected)
    }
  }
}
