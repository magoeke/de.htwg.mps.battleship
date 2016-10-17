package de.htwg.mps.battleship.model

case class Ship(pos: List[Field]) {
  val size = pos.length
  def isDead = (for (p <- pos if (p.shot == false)) yield p).length == 0
  override def toString = "Ship: (size=" + size + ")"
}
