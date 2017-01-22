package de.htwg.mps.battleship.controller.model

import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.model.IField

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Ship}


class GamefieldSpec extends ModelSpec{
  "A new gamefield" should {
    val size = 10
    val ships = List(Ship(2))
    val field = Array.fill[IField] (size, size) {Field (false)}
    val gamefield = Gamefield (field, ships)


    "should have a field" in {
      gamefield.field should be(field)
    }

    "should have ships" in {
      gamefield.ships should be(ships)
    }

    "should print" in {
      gamefield.toString should be("Gamefield( field.dimension=" + gamefield.field.length + ", ships.length=" + gamefield.ships.length + " )")
    }

    val point = new Point(1,1)
    //val firedField = gamefield.fire(point)
    val gamefieldNew = gamefield.fire(point)
    "can be fired" in {
      gamefield.fire(point).field should be(gamefieldNew.field)
      gamefield.fire(point).ships should be(gamefieldNew.ships)
    }

    val shipsUpdate = List(Ship(2), Ship(2))
    val newShips = gamefield.updateShips(shipsUpdate)
    "can be update" in {
      gamefield.updateShips(shipsUpdate).ships should be(newShips.ships)
      //gamefield.fire(point).ships should be(gamefieldNew.ships)
    }


  }
}
