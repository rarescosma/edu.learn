package simulations

class Simulator {
  type Action = () => Unit

  protected type Agenda = List[WorkItem]
  
  protected var totalTime: Double = 0

  case class WorkItem(time: Int, action: Action)

  protected[simulations] var agenda: Agenda = List()
  protected var currentTime = 0

  protected def afterDelay(delay: Int)(action: => Unit) {
    val item = WorkItem(currentTime + delay, () => action)
    def insert(ag: Agenda): Agenda =
      if (ag.isEmpty || item.time < ag.head.time) item :: ag
      else ag.head :: insert(ag.tail)
    agenda = insert(agenda)
  }
  
  def time[R](block: => R): (Double, R) = {
    val t0 = System.nanoTime()
    val result = block    // call-by-name
    val t1 = System.nanoTime()
    ((t1 - t0) / 1000000, result)
  }

  protected[simulations] def next {
    val(t, result) = time {
      agenda match {
        case List() => {}
        case WorkItem(time, action) :: rest =>
          agenda = rest
          currentTime = time
          action()
      }
    }
    
    totalTime += t
  }

  def run {
    println("*** New propagation ***")
    while (!agenda.isEmpty) { next }
  }
}
