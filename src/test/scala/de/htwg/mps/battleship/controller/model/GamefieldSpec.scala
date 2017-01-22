package de.htwg.mps.battleship.controller.model

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
  }
}
