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

    "should print" in {
      ship.toString should be("Ship(size=" + ship.size + ", initialized=" + ship.initialized + ")")
    }
  }

  "A initialized Ship" should {
    //Ship with with Length and no position
    val shipPos =List(new Field(false), new Field(false))
    val shipWithPos = Ship(shipPos)
    "should have a size" in {
      shipWithPos.size should be (shipSize)
    }
    "should have a Pos" in {
      shipWithPos.pos should be (shipPos)
    }
    "should have a status if inizialized" in {
      shipWithPos.initialized should be (true)
    }

    "should be a Ship" in {
      shipWithPos should be(Ship(shipPos))
    }
  }

  val shipDeadPos =List(new Field(true), new Field(true))
  val deadShip = new Ship(shipDeadPos, shipDeadPos.length, true)
  "A dead Ship" in{
    deadShip.isDead should be(true)
  }

  val copyShip =List(new Field(true), new Field(false))
  "can be copied" in {
    deadShip.copyShip(copyShip).pos should be(copyShip)
  }

  "should print" in {
    deadShip.toString should be("Ship(size=" + deadShip.size + ", initialized=" + deadShip.initialized + ")")
  }
}
