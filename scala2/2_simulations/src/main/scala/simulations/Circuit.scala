package simulations

import common._

class Wire {
  private var sigVal = false
  private var actions: List[Simulator#Action] = List()

  def getSignal: Boolean = sigVal

  def setSignal(s: Boolean) {
    if (s != sigVal) {
      sigVal = s
      actions.foreach(action => action())
    }
  }

  def addAction(a: Simulator#Action) {
    actions = a :: actions
    a()
  }
}

abstract class CircuitSimulator extends Simulator {

  val InverterDelay: Int
  val AndGateDelay: Int
  val OrGateDelay: Int

  def probe(name: String, wire: Wire) {
    wire addAction {
      () => afterDelay(0) {
        println(
          "  " + currentTime + ": " + name + " -> " +  wire.getSignal)
      }
    }
  }

  def inverter(input: Wire, output: Wire) {
    def invertAction() {
      val inputSig = input.getSignal
      afterDelay(InverterDelay) { output.setSignal(!inputSig) }
    }
    input addAction invertAction
  }

  def andGate(a1: Wire, a2: Wire, output: Wire) {
    def andAction() {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(AndGateDelay) { output.setSignal(a1Sig & a2Sig) }
    }
    a1 addAction andAction
    a2 addAction andAction
  }

  //
  // to complete with orGates and demux...
  //

  def orGate(a1: Wire, a2: Wire, output: Wire) {
    def orAction() {
      val a1Sig = a1.getSignal
      val a2Sig = a2.getSignal
      afterDelay(OrGateDelay) { output.setSignal(a1Sig | a2Sig) }
    }
    a1 addAction orAction
    a2 addAction orAction
  }

  def orGate2(a1: Wire, a2: Wire, output: Wire) {
    val a1Inv, a2Inv, andOut = new Wire
    inverter(a1, a1Inv)
    inverter(a2, a2Inv)
    andGate(a1Inv, a2Inv, andOut)
    inverter(andOut, output)
  }

  def demux(in: Wire, c: List[Wire], out: List[Wire]) {
    c match {
      // No control bits => simply output the input
      case Nil => {
        def nullDemux() {
   	      val sig = in.getSignal
   	      afterDelay(0) { out.head.setSignal(sig) }
        }
    	in addAction nullDemux
      }
      // Multiple control bits => recurse
      case msb :: t => {
        // Split the output bits
        val m1, m2 = new Wire

        def unit() {
          val sig = in.getSignal
          val c = msb.getSignal
          afterDelay(0) {
            m1.setSignal(sig & c)
            m2.setSignal(sig & !c)
          }
        }

        msb addAction unit
        in addAction unit

        val (out1, out2) = out.splitAt(out.size/2)
        demux(m1, t, out1)
        demux(m2, t, out2)
      }
    }
  }



}

object Circuit extends CircuitSimulator {
  val InverterDelay = 1
  val AndGateDelay = 3
  val OrGateDelay = 5

  def andGateExample {
    val in1, in2, out = new Wire
    andGate(in1, in2, out)
    probe("in1", in1)
    probe("in2", in2)
    probe("out", out)
    in1.setSignal(false)
    in2.setSignal(false)
    run

    in1.setSignal(true)
    run

    in2.setSignal(true)
    run
  }

  def orGateExample {
    val in1, in2, out = new Wire
    orGate(in1, in2, out)
    probe("in1", in1)
    probe("in2", in2)
    probe("out", out)
    in1.setSignal(false)
    in2.setSignal(false)
    run

    in1.setSignal(true)
    run

    in2.setSignal(true)
    run

    in1.setSignal(false)
    run
  }

  def demuxExample {
    val in, c1, c2, out1, out2, out3, out4 = new Wire
    demux(in, List(c1, c2), List(out1, out2, out3, out4))

    probe("c1", c1)
    probe("c2", c2)
    probe("out1", out1)
    probe("out2", out2)
    probe("out3", out3)
    probe("out4", out4)

    in.setSignal(true)
    c1.setSignal(false)
    c2.setSignal(false)
    run

    c1.setSignal(true)
    run

    c2.setSignal(true)
    run

    c1.setSignal(false)
    run
  }
}

object CircuitMain extends App {
  // You can write tests either here, or better in the test class CircuitSuite.
  Circuit.andGateExample
  Circuit.orGateExample
  Circuit.demuxExample
}
