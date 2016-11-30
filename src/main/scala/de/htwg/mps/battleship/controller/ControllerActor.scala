package de.htwg.mps.battleship.controller

import akka.actor.{ Actor, ActorRef }
import de.htwg.mps.battleship.controller.command.Command
import de.htwg.mps.battleship.model.IPlayer

import scala.collection.mutable.ListBuffer

case object RegisterUI
case object DeregisterUI

class ControllerActor(val players: List[IPlayer]) extends Actor {
  val controller = new BattleshipController(players)
  val userInterfaces = new ListBuffer[ActorRef]()

  override def receive: Receive = {
    case RegisterUI => userInterfaces += sender(); sender() ! controller.boardsView
    case DeregisterUI => userInterfaces -= sender()
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(_ ! controller.boardsView)
      case _ => userInterfaces.foreach(_ ! controller.boardsView)
    }
  }
}