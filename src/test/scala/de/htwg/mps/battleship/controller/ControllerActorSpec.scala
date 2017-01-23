package de.htwg.mps.battleship.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit, TestProbe}
import de.htwg.mps.battleship.{Point, Spec}
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.IField
import de.htwg.mps.battleship.view.gui.{GUIBoarder, GUIHelper}
import de.htwg.mps.battleship.view.tui.TUI

import scala.collection.mutable.ListBuffer
import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}


class ControllerActorSpec extends Spec{
  "A Controller Actor" should {
    val size = 10
    val ships = List(Ship(2))
    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
    val players = List(Player(gamefield, "player0"), Player(gamefield, "player1"))

    "have a userinterface" in {
      val parent = ActorSystem("ControllerActor")
      val controllerActor = parent.actorOf(Props(classOf[ControllerActor], players))
      ControllerFactory.create(parent,players)
      val resisterUi = RegisterUI

      val deresisterUi = DeregisterUI

      controllerActor ! resisterUi
      controllerActor ! deresisterUi
      controllerActor ! Nothing
      controllerActor ! SetShip(new Point(1,1), new Point(1,2))
      controllerActor ! SetShip(new Point(1,1), new Point(1,2))
      controllerActor ! Fire(new Point(1,1))
      controllerActor ! Fire(new Point(1,1))
      controllerActor ! Fire(new Point(1,2))

      //controllerActor.userInterfaces shouldBe a[ActorRef]
    }

    "wrong commends" in {
      val parent = ActorSystem("ControllerActor")
      val controllerActor = parent.actorOf(Props(classOf[ControllerActor], players))
      ControllerFactory.create(parent,players)
      val resisterUi = RegisterUI

      val deresisterUi = DeregisterUI

      controllerActor ! SetShip(new Point(1,1), new Point(2,2))


      //controllerActor.userInterfaces shouldBe a[ActorRef]
    }

    "RegisterUI" in {
      val parent = new RegisterUI("player0")
      parent.player should be ("player0")
    }

    "Winner" in {
      val parent = new Winner("player0")
      parent.player should be ("player0")
    }

    "GameInfo" in {
      val c = new BattleshipController(BattleshipControllerTest.setupTest(2))
      val parent = new GameInformation(players(0).name,c.setableShips(players(0)).map(_.size), c.boardsView(players(0)))
      val uu = new UpdateUI("player0",List(parent))
    }
  }
}
