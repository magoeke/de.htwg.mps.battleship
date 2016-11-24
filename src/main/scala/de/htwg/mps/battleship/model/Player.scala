package de.htwg.mps.battleship.model

case class Player(gamefield: Gamefield, name: String) extends IPlayer {
  def updateShips(ships: List[Ship]) : Player = copy(gamefield.updateShips(ships), name)
  def fire(point: Point): Player = copy(gamefield.fire(point), name)
  def getGamefield : Gamefield = gamefield
  def getName : String = name
}