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
    case DeregisterUI => userInterfaces -= sender
    case command: Command => controller.handleCommand(command) match {
      case true => userInterfaces.foreach(actor => actor ! createUpdateUI(actor))
      case _ => {
        val winner = controller.getWinner
        if(winner.isDefined) { userInterfaces.foreach(_ ! Winner(winner.get.name)) }
        context.system.terminate()
      }
    }
  }

  private def createUpdateUI(actor: ActorRef) = {
    val player: String = actorToUser.getOrElse(actor, controller.currentPlayer.name)
    UpdateUI(controller.currentPlayer.name, controller.collectGameInformation)
  }
}

case class GameInformation(player: String, setableShips: List[Int], boards: List[Array[Array[FieldState.Value]]])
case class UpdateUI(currentPlayer: String, gameInformation: List[GameInformation])
case class Winner(player: String)