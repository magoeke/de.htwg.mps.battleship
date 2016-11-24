package scala.de.htwg.mps.battleship.model.impl

import de.htwg.mps.battleship.model.{IField, IShip}

case class Ship(pos: List[IField], size: Int, initialized: Boolean) extends IShip{
  def isDead : Boolean = (for (p <- pos if p.shot == false) yield p).length == 0
  def copyShip(pos: List[IField]) : IShip = copy(pos)
  override def toString : String = "Ship(size=" + size + ", initialized=" + initialized + ")"
}

object Ship {
  def apply(size: Int) : Ship = new Ship(List(), size, false)
  def apply(pos: List[IField]) : Ship = new Ship(pos, pos.length, true)
}
