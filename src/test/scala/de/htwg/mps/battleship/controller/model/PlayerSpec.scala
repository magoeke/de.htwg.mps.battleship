package de.htwg.mps.battleship.controller.model

import de.htwg.mps.battleship.model.IField

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}

class PlayerSpec extends ModelSpec{
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
  }
}
