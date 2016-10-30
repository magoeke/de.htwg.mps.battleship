package de.htwg.mps.battleship.model

case class Ship(pos: List[Field], size: Int, initialized: Boolean) {
  def isDead = (for (p <- pos if (p.shot == false)) yield p).length == 0
  override def toString = "Ship(size=" + size + ", initialized=" + initialized + ")"
}

object Ship {
  def apply(size: Int) = new Ship(List(), size, false)
  def apply(pos: List[Field]) = new Ship(pos, pos.length, true)
}
