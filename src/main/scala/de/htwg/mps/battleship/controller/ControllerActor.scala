package de.htwg.mps.battleship.controller

import akka.actor.{Actor, ActorRef, Kill}
import de.htwg.mps.battleship.controller.command.Command
import de.htwg.mps.battleship.model.IPlayer

import scala.collection.mutable.ListBuffer

case object RegisterUI
case object DeregisterUI

class ControllerActor(val players: List[IPlayer]) extends Actor {
  val controller = new BattleshipController(players)
  val userInterfaces = new ListBuffer[ActorRef]()

  override def receive: Receive = {
    case RegisterUI => userInterfaces += sender(); sender() ! createUpdateUI()
    case DeregisterUI => userInterfaces -= sender()
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(_ ! createUpdateUI())
      case _ => {
        val winner = controller.getWinner
        if(winner.isDefined) { userInterfaces.foreach(_ ! winner.get.name) }
        context.system.terminate()
      }
    }
  }

  private def createUpdateUI() = {
    UpdateUI(controller.currentPlayer.name, controller.setableShips.toString(), controller.boardsView)
  }
}

case class UpdateUI(playerName: String, setableShips: String, boards: List[Array[Array[FieldState.Value]]])