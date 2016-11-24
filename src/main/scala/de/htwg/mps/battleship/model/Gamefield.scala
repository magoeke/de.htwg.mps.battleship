package de.htwg.mps.battleship.model

case class Gamefield(field: Array[Array[Field]], ships: List[Ship]) {

  override def toString: String = "Gamefield( field.dimension=" + field.length + ", ships.length=" + ships.length + " )"

  def fire(point: Point): Gamefield = {
    val newF = Field(true)
    val oldF = field(point.x)(point.y)
    val newField = field.map(_.map(compareFields(_, oldF, newF)))
    val newShips = ships.map(ship => ship.copy(ship.pos.map(compareFields(_, oldF, newF))))
    copy(newField, newShips)
  }

  def updateShips(ships: List[Ship]): Gamefield = { copy(field, ships) }
  def compareFields(f1: Field, f2: Field, f3: Field) = if (f1 eq f2) f3 else f1
}