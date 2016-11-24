package de.htwg.mps.battleship.model

case class Ship(pos: List[Field], size: Int, initialized: Boolean) {
  def isDead : Boolean = (for (p <- pos if p.shot == false) yield p).length == 0
  override def toString : String = "Ship(size=" + size + ", initialized=" + initialized + ")"
}

object Ship {
  def apply(size: Int) : Ship = new Ship(List(), size, false)
  def apply(pos: List[Field]) : Ship = new Ship(pos, pos.length, true)
}
