package de.htwg.mps.battleship.controller

import de.htwg.mps.battleship.controller.command.Command
import de.htwg.mps.battleship.model.{IPlayer, IShip}

trait IBattleshipController {
  def handleCommand(command: Command) : Boolean
  def boardsView : List[Array[Array[FieldState.Value]]]
  def setableShips: List[IShip]
  def currentPlayer : IPlayer
  def collectGameInformation: List[GameInformation]
  def getWinner: Option[IPlayer]
}
