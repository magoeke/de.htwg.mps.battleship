package de.htwg.mps.battleship.controller.model

import scala.de.htwg.mps.battleship.model.impl.{Field, Ship}


class ShipSpec extends ModelSpec {
  "A new Ship" should {
    //Ship with with Length and no position
    val ship = Ship(2)
    "should have a size" in {
      ship.size should be (2)
    }
    "should have a empty Pos" in {
      ship.pos should be (List())
    }
    "should have a satus if inizialized" in {
      ship.initialized should be (false)
    }

    val shipPos =List(new Field(false), new Field(false), new Field(false))
    val shipWithPos = Ship(shipPos)
    "should have a size" in {
      shipWithPos.size should be (3)
    }
    "should have a empty Pos" in {
      shipWithPos.pos should be (shipPos)
    }
    "should have a satus if inizialized" in {
      shipWithPos.initialized should be (true)
    }
  }
}
