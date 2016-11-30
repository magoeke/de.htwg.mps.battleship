package de.htwg.mps.battleship

import akka.actor.{ActorSystem, Props}
import de.htwg.mps.battleship.controller.{ControllerFactory}
import de.htwg.mps.battleship.model.{IField, IPlayer}
import de.htwg.mps.battleship.view.tui.TUI

import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}

object Battleship {

  def main(args: Array[String]) {
    val actorSystem = ActorSystem.create("battleship")
    val controller = ControllerFactory.create(actorSystem, setUp())
    actorSystem.actorOf(Props(new TUI(controller, true)))
    println("Started Game")
  }

  def setUp(): List[IPlayer] = {
    val size = 10
    val ships = List(Ship(2))
    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
    List(Player(gamefield, "player0"), Player(gamefield, "player1"))
  }
}