package de.htwg.mps.battleship.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.IPlayer

import scala.collection.mutable.ListBuffer

case object RegisterUI
case object DeregisterUI

class ControllerActor(val players: List[IPlayer]) extends Actor {
  val controller: IBattleshipController = new BattleshipController(players)
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
    case Ships => sender() ! AShips(controller.setableShips.map(_.size))
    case Boards(view) => sender() ! ABoards(controller.boardsView(view))
    case Player => sender() ! APlayer(controller.currentPlayer.name)
  }

  private def createUpdateUI() = {
    UpdateUI(controller.currentPlayer.name, controller.setableShips.toString(), controller.boardsView)
  }
}

case class UpdateUI(playerName: String, setableShips: String, boards: List[Array[Array[FieldState.Value]]])