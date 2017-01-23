package de.htwg.mps.battleship.controller

import de.htwg.mps.battleship.Point
import org.scalatest._
import org.scalatest.Matchers._
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.controller.BattleshipController
import de.htwg.mps.battleship.model.IPlayer
import de.htwg.mps.battleship.model._

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}

class BattleshipControllerTest extends WordSpec {

  "A controller" can {
    "new" should {
      "have at least two players" in {
        // Mock for this test

        // valid examples
        val valid1 = List(MockPlayer.mockPlayer,MockPlayer.mockPlayer,MockPlayer.mockPlayer)
        val valid2 = List(MockPlayer.mockPlayer, MockPlayer.mockPlayer)
        new BattleshipController(valid1) shouldBe a[BattleshipController]
        new BattleshipController(valid2) shouldBe a[BattleshipController]

        // not valid example
        val not_valid1 = List(MockPlayer.mockPlayer)
        val not_valid2 = List()
        an[IllegalArgumentException] should be thrownBy {
          new BattleshipController(not_valid1)
        }
        an[IllegalArgumentException] should be thrownBy {
          new BattleshipController(not_valid2)
        }
      }
    }

    "all" should {

      "handle commands right" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(2))
        c.handleCommand(NewGame()) should be(true)
        c.handleCommand(Nothing()) should be(true)
        c.handleCommand(Fire(Point(1, 1))) should be(true)
        c.handleCommand(SetShip(Point(1, 1), Point(1, 1))) should be(true)
        c.handleCommand(QuitGame()) should be(false)
      }

      "set a ship on free position" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(2))
        val old_gamefield = c.players(0)
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        (c.players(0) == old_gamefield) should be(false)
        c.turn should equal(0)
        c.handleCommand(SetShip(Point(1, 2), Point(2, 2)))
        c.turn should equal(1)
        c.handleCommand(SetShip(Point(1, 3), Point(2, 3)))
        c.turn should equal(1)
        c.handleCommand(SetShip(Point(1, 4), Point(2, 4)))
        c.turn should equal(2)
      }

      "can't set a ship on taken fields" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(2))
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        c.handleCommand(SetShip(Point(1, 1), Point(1, 2)))
        c.turn should equal(0)
        c.handleCommand(SetShip(Point(2, 1), Point(2, 2)))
        c.turn should equal(0)
        c.handleCommand(SetShip(Point(4, 1), Point(4, 2)))
        c.turn should equal(1)
      }

      "fire only if all ships are set" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        val old_player = c.players(0)
        c.handleCommand(Fire(Point(1, 1)))
        old_player eq c.players(0) should be(true)
        c.turn should equal(0)
        // set ships for both players
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        c.handleCommand(Fire(Point(1, 1)))
        c.turn should equal(3)
      }

      "doesn't find a winner when no ships are set" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        c.getWinner.isEmpty should be(true)
      }

      "find a winner when only one player is left" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        println("---------------------")
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        c.handleCommand(SetShip(Point(1, 1), Point(2, 1)))
        c.handleCommand(Fire(Point(1, 1)))
        c.handleCommand(Fire(Point(1, 1)))
        c.handleCommand(Fire(Point(2, 1)))
        c.getWinner.isEmpty should be(false)
      }

      "reset" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        println("---------------------")
        c.reset should be(true)
      }

      "Fieldstate" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        //val c2 = new BattleshipController(BattleshipControllerTest.setupTest(1))
        //c.boardsView should be(c.boardsView(c.currentPlayer))
      }

      "set Ships" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        c.setableShips should be(c.setableShips(c.currentPlayer))
      }

      "gameinformation" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        val gameInformation = c.collectGameInformation
      }

      "current Player" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest(1))
        val currentPlayer = c.currentPlayer
      }
    }
  }
}

object BattleshipControllerTest {
  def setupTest(nships: Int): List[Player] = {
    val size = 10
    val ships = (0 until nships).map(_ => Ship(2)).toList
    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
    List(Player(gamefield, "player0"), Player(gamefield, "player1"))
  }
}

object MockPlayer{
  def mockPlayer: Player = {
    val size = 10
    val ships = List(Ship(2))
    val gamefield = Gamefield(Array.fill[IField](size, size) {
      Field(false)
    }, ships)
    Player(gamefield, "player1")
  }
}

