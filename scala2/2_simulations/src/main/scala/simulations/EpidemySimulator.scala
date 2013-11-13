package simulations

import math.random

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt

  protected[simulations] object SimConfig {
    val population: Int = 300
    val roomRows: Int = 8
    val roomColumns: Int = 8

    // to complete: additional parameters of simulation
    val incubationTime = 6
    val dieTime = 14
    val immuneTime = 16
    val healTime = 18
    val moveTime = 5

    val prevalenceRate = 0.01
    val transRate = 0.4
    val dieRate = 0.25

    // extensions
    val airTraffic = false
    val reducedMobility = false
    val chosenFew = false
  }

  import SimConfig._

  def prePopulate: List[Person] = {
    var persons = List[Person]()
    val sickPersons = (prevalenceRate * population).toInt
    for (x <- 1 to population) {
      val p = new Person(x)
      if (x <= sickPersons) { p.getInfected }
      persons = p :: persons
    }
    persons
  }

  protected type MyRoom = (Int, Int)

  val persons: List[Person] = prePopulate // to complete: construct list of persons

  class Person(val id: Int) {
    var infected = false
    var sick = false
    var immune = false
    var dead = false

    // demonstrates random number generation
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)

    afterDelay(0) { move }

    def destination: Option[(Int, Int)] = {
      val candidates = Array(
        (row + 1, col),
        (row + roomRows - 1, col),
        (row, col + 1),
        (row, col + roomColumns - 1)) map {
          case (a, b) => (a % roomRows, b % roomColumns)
        } filter {
          case (room) => (inRoom(room, (p) => p.visiblyInfectious)).size == 0
        }

      candidates.size match {
        case 0 => None
        case _ => Some(candidates(randomBelow(candidates.size)))
      }
    }

    def inRoom(r: MyRoom, predicate: (Person => Boolean)): List[Person] = {
      (persons.find { p => p.row == r._1 & p.col == r._2 & predicate(p) }).toList
    }

    def move {
      def changeLocation(room: MyRoom) {
        row = room._1; col = room._2

        if ((inRoom(room, (p) => p.infectious).size > 0)
          && (random < transRate)) getInfected

        afterDelay(randomBelow(moveTime)) { move }
      }

      if (!dead) {
        destination match {
          case None => afterDelay(randomBelow(moveTime)) { move }
          case Some(room) => changeLocation(room)
        }
      }
    }

    def getInfected {
      if (!immune && !infected) {
        infected = true
        afterDelay(incubationTime) { sick = true }
        afterDelay(dieTime) { DIE }
        afterDelay(immuneTime) { becomeImune }
        afterDelay(healTime) { heal }
      }
    }

    def DIE {
      if (random < dieRate) {
        dead = true
      }
    }

    def becomeImune {
      if (!dead) {
        sick = false
        immune = true
      }
    }

    def heal {
      if (!dead) {
        infected = false
        sick = false
        immune = false
      }
    }

    def visiblyInfectious: Boolean = { sick || dead }
    def infectious: Boolean = { sick || dead || infected }
  }
}
