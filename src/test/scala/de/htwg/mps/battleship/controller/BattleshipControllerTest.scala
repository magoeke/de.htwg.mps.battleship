package de.htwg.mps.battleship.controller

import org.scalatest._
import org.scalatest.Matchers._

import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.Point
import de.htwg.mps.battleship.model.IPlayer
import de.htwg.mps.battleship.model._

class BattleshipControllerTest extends WordSpec {

  "A controller" can {
    "new" should {
      "have at least two players" in {
        // Mock for this test
        class PlayerMock extends IPlayer {
          def updateShips(s: List[Ship]) = null
          def fire(p: Point) = null
          def getGamefield = null
          def getName = null
        }

        // valid examples
        val valid1 = List(new PlayerMock(), new PlayerMock(), new PlayerMock())
        val valid2 = List(new PlayerMock(), new PlayerMock())
        new BattleshipController(valid1) shouldBe a[BattleshipController]
        new BattleshipController(valid2) shouldBe a[BattleshipController]

        // not valid example
        val not_valid1 = List(new PlayerMock())
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
        val c = new BattleshipController(BattleshipControllerTest.setupTest)
        c.handleCommand(NewGame()) should be(true)
        c.handleCommand(Nothing()) should be(true)
        c.handleCommand(Fire(Point(1, 1))) should be(true)
        c.handleCommand(SetShip(Point(1, 1), Point(1, 1))) should be(true)
        c.handleCommand(QuitGame()) should be(false)
      }

      "set a ship on free position" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest)
        val old_gamefield = c.players(0)
        c.setShip(Point(1, 1), Point(2, 1))
        (c.players(0) == old_gamefield) should be(false)
        c.turn should equal(0)
        c.setShip(Point(1, 2), Point(2, 2))
        c.turn should equal(1)
        c.setShip(Point(1, 3), Point(2, 3))
        c.turn should equal(1)
        c.setShip(Point(1, 4), Point(2, 4))
        c.turn should equal(2)
      }

      "can't set a ship on taken fields" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest)
        c.setShip(Point(1, 1), Point(2, 1))
        c.setShip(Point(1, 1), Point(1, 2))
        c.turn should equal(0)
        c.setShip(Point(2, 1), Point(2, 2))
        c.turn should equal(0)
        c.setShip(Point(4, 1), Point(4, 2))
        c.turn should equal(1)
      }
      
      "fire only if all ships are set" in {
        val c = new BattleshipController(BattleshipControllerTest.setupTest)
        c.setShip(Point(1, 1), Point(2, 1))
        val old_player = c.players(0)
        c.fire(Point(1,1))
        old_player eq c.players(0) should be (true)
        c.turn should equal(0)
      }

    }
  }
}

object BattleshipControllerTest {
  def setupTest: List[Player] = {
    val size = 10
    val ships = List(Ship(2), Ship(2))
    val gamefield = Gamefield(Array.fill[Field](size, size) { Field(false) }, ships)
    List(Player(gamefield, "player0"), Player(gamefield, "player1"))
  }
}