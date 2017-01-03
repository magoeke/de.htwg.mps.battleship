package de.htwg.mps.battleship.controller

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.IPlayer

import scala.collection.mutable.ListBuffer
import scala.collection.mutable.Map

case class RegisterUI(player: String)
case object RegisterUI
case object DeregisterUI

class ControllerActor(val players: List[IPlayer]) extends Actor {
  val controller: IBattleshipController = new BattleshipController(players)
  val userInterfaces = new ListBuffer[ActorRef]()
  val actorToUser = Map[ActorRef, String]()

  override def receive: Receive = {
    case RegisterUI => userInterfaces += sender; sender() ! createUpdateUI(sender())
    case RegisterUI(player) => register(player, sender()); sender() ! createUpdateUI(sender())
    case DeregisterUI => deregister(sender())
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(actor => actor ! createUpdateUI(actor))
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

  private def register(player: String, sender: ActorRef) = {
    userInterfaces += sender
    actorToUser += (sender -> player)
  }

  private def deregister(sender: ActorRef) = userInterfaces -= sender; actorToUser -= sender

  private def createUpdateUI(actor: ActorRef) = {
    val player: String = actorToUser.getOrElse(actor, controller.currentPlayer.name)
    UpdateUI(controller.currentPlayer.name, controller.setableShips.map(_.size), controller.boardsView(player))
  }
}

case class UpdateUI(currentPlayer: String, setableShips: List[Int], boards: List[Array[Array[FieldState.Value]]])