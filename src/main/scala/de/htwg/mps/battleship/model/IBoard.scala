package de.htwg.mps.battleship.model

trait IBoard {
  def field: Array[Array[IField]]
  def ships: List[IShip]
  def fire(point: Point) : IBoard
  def updateShips(ships: List[IShip]) : IBoard
}
