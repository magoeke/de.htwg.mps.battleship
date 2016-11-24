package scala.de.htwg.mps.battleship.model.impl

import de.htwg.mps.battleship.model.{IBoard, IField, IShip, Point}

case class Gamefield(field: Array[Array[IField]], ships: List[IShip]) extends IBoard {

  override def toString: String = "Gamefield( field.dimension=" + field.length + ", ships.length=" + ships.length + " )"

  def fire(point: Point): IBoard = {
    val newF : IField = Field(true)
    val oldF : IField = field(point.x)(point.y)
    val newField = field.map(_.map(compareFields(_, oldF, newF)))
    val newShips = ships.map(ship => ship.copyShip(ship.pos.map(compareFields(_, oldF, newF))))
    copy(newField, newShips)
  }

  def updateShips(ships: List[IShip]): IBoard = { copy(field, ships) }
  private def compareFields(f1: IField, f2: IField, f3: IField) : IField = if (f1 eq f2) f3 else f1
}