package de.htwg.mps.battleship

import akka.actor.{ActorSystem, Props}
import de.htwg.mps.battleship.controller.ControllerFactory
import de.htwg.mps.battleship.model.{IField, IPlayer}
import de.htwg.mps.battleship.view.gui.{GUIBoarder, GUIHelper}
import de.htwg.mps.battleship.view.tui.TUI

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}
import scalafx.application.Platform

object Battleship {

  def main(args: Array[String]) {
    val actorSystem = ActorSystem.create("battleship")
    val controller = ControllerFactory.create(actorSystem, setUp())
    val gui = new GUIBoarder(controller, 10)
    actorSystem.actorOf(Props(new TUI(controller, true)))
    println("Started Game")

    actorSystem.actorOf(Props(new GUIHelper(controller,gui)))
    gui.main(Array())


    //val gUIHelper = new GUIHelper(controller, gui)


  }

  def setUp(): List[IPlayer] = setUp("player0", "player1")
  def setUp(playerOne: String, playerTwo: String): List[IPlayer] = {
    val size = 10
    val ships = List(Ship(2))
    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
    List(Player(gamefield, playerOne), Player(gamefield, playerTwo))
  }
}