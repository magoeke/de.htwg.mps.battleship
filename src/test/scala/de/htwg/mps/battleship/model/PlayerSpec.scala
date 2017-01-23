package de.htwg.mps.battleship.model

import de.htwg.mps.battleship.{Point, Spec}

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}

class PlayerSpec extends Spec{
  "A new player" should {
    val size = 10
    val ships = List (Ship (2))
    val gamefield = Gamefield (Array.fill[IField] (size, size) {Field (false)}, ships)
    val playername = "player"

    val player = new Player(gamefield,playername)

    "should be named as " + playername in {
      player.name should be(playername)
    }

    "should have a Board" in {
      player.board should be(gamefield)
    }

    val point = new Point(1,1)
    //val firedField = gamefield.fire(point)
    val gamefieldNew = player.fire(point)
    "can be fired" in {
      player.fire(point).name should be(gamefieldNew.name)
      player.fire(point).board.field should be(gamefieldNew.board.field)
      player.fire(point).board.ships should be(gamefieldNew.board.ships)
      //player.fire(point).board should be(gamefieldNew.board)
    }

    val shipsUpdate = List(Ship(2), Ship(2))
    val newShips = player.updateShips(shipsUpdate)
    "can be update" in {
      player.updateShips(shipsUpdate).name should be(newShips.name)
      player.updateShips(shipsUpdate).board.ships should be(newShips.board.ships)
      //gamefield.fire(point).ships should be(gamefieldNew.ships)
    }
  }
}
