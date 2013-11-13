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
  
  test("demux4") {

    val out = List.fill(8)(new Wire)
    val c = List.fill(3)(new Wire)

    val in = new Wire
    demux(in, c, out)

    in.setSignal(true)
    c(0).setSignal(true)
    c(1).setSignal(true)
    c(2).setSignal(false)
    run
    
    println(signals(out))

    assert(signals(out) == List(false, true, false, false, false, false, false, false))
  }
}
