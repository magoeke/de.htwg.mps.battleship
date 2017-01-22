package de.htwg.mps.battleship.controller.model

import scala.de.htwg.mps.battleship.model.impl.{Field, Ship}


class ShipSpec extends ModelSpec {
  val shipSize = 2
  "A not initialized Ship with Size " +shipSize should {
    //Ship with with Length and no position
    val ship = Ship(shipSize)
    "should have a size" in {
      ship.size should be (shipSize)
    }
    "should have a empty Pos" in {
      ship.pos should be (List())
    }
    "should have a status if inizialized" in {
      ship.initialized should be (false)
    }
  }

  "A initialized Ship" should {
    //Ship with with Length and no position
    val shipPos =List(new Field(false), new Field(false))
    val shipWithPos = new Ship(shipPos,shipPos.length,true)
    "should have a size" in {5
      shipWithPos.size should be (shipSize)
    }
    "should have a Pos" in {
      shipWithPos.pos should be (shipPos)
    }
    "should have a status if inizialized" in {
      shipWithPos.initialized should be (true)
    }
  }
}
