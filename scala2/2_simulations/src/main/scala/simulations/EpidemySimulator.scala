package simulations

import math.random

class EpidemySimulator extends Simulator {

  def randomBelow(i: Int) = (random * i).toInt
  def randomPair(i: Int, j: Int) = (randomBelow(i), randomBelow(j))
  def randomDistinctPair(i: Int, j: Int, not: (Int, Int)): (Int, Int) = randomPair(i, j) match {
      case `not` => randomDistinctPair(i, j, not)
      case x => x
  }

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
    val transRate = 0.40
    val dieRate = 0.25

    // Air Traffic
    val airTraffic = false
    val airRate = 0.01

    // Reduced Mobility
    val reducedMobility = true

    // Chosen Few
    val chosenFew = false
    val vaccinationRate = 0.05
  }

  import SimConfig._

  val persons: List[Person] = {
    var persons = List[Person]()
    val sickPersons = (prevalenceRate * population).toInt
    val immunePersons = sickPersons + (vaccinationRate * population).toInt
    for (x <- 1 to population) {
      val p = new Person(x)
      if (x <= sickPersons) { p.getInfected }
      else if(chosenFew && x <= immunePersons) { p.immune = true }
      persons = p :: persons
    }
    persons
  }

  private class Room (val r: Int, val c: Int) {
    private val people = persons.filter { p => p.row == r & p.col == c }

    def coords: (Int, Int) = (r, c)

    def has(predicate: (Person => Boolean)): Boolean = people exists predicate

    def hasonly(predicate: (Person => Boolean)): Boolean = people forall predicate

    def seemsHealthy: Boolean = hasonly ((p) => !p.visiblyInfectious)
  }

  class Person(val id: Int) {
    var infected = false
    var sick = false
    var immune = false
    var dead = false

    // demonstrates random number generation
    var row: Int = randomBelow(roomRows)
    var col: Int = randomBelow(roomColumns)

    afterDelay(moveDelay) { move }

    def move {
      if (dead) return;
      afterDelay(moveDelay) { move }

      if(airTraffic & random < airRate) {
        val (r, c) = randomDistinctPair(roomRows, roomColumns, (row,col))
        changeLocation(new Room(r, c))
      } else {
        val candidates = for(
          (r, c) <- List((row + 1, col), (row + roomRows - 1, col),
                         (row, col + 1), (row, col+ roomColumns - 1));
          room = new Room(r % roomColumns, c % roomColumns)
          if( room.seemsHealthy )
        ) yield room

        val size = candidates.size
        if(size > 0) changeLocation(candidates(randomBelow(size)))
      }
    }

    def changeLocation(room: Room) = {
      val (r, c) = room.coords
      row = r; col = c
      if ( (room has ((p) => p.infectious))
          && (random < transRate) ) getInfected
    }

    def moveDelay: Int = {
      val delay = 1 + randomBelow(moveTime)
      if(!reducedMobility){ delay } else if(!sick) { 2*delay } else { 4*delay }
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
        infected = false
        sick = false
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
