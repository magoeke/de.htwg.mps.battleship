package de.htwg.mps.battleship.model

trait IPlayer {
  def board: IBoard
  def name: String
  def fire(point: Point): IPlayer
  def updateShips(ships: List[IShip]): IPlayer
}