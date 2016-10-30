package de.htwg.mps.battleship.model

trait IPlayer {
  def getGamefield: Gamefield
  def getName: String
  def fire(point: Point): IPlayer
  def updateShips(ships: List[Ship]): IPlayer
}