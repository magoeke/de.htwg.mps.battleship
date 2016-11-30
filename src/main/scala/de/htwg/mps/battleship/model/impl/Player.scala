package scala.de.htwg.mps.battleship.model.impl

import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.model.{IBoard, IPlayer, IShip}

case class Player(board: IBoard, name: String) extends IPlayer {
  def updateShips(ships: List[IShip]) : IPlayer = copy(board.updateShips(ships), name)
  def fire(point: Point): IPlayer = copy(board.fire(point), name)
//  def getGamefield : Gamefield = gamefield
//  def getName : String = name
}