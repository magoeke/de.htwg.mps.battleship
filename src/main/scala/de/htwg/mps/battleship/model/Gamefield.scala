package de.htwg.mps.battleship.model

case class Gamefield(size: Int, field: Array[Array[Field]], ships: List[Ship]) {
  //  var field = Array.fill[Field](size, size) { Field(false) }
  override def toString: String = "Gamefield: " + size
}