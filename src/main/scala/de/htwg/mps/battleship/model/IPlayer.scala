package de.htwg.mps.battleship.model

import de.htwg.mps.battleship.Point

trait IPlayer {
  def board: IBoard
  def name: String
  def fire(point: Point): IPlayer
  def updateShips(ships: List[IShip]): IPlayer
}