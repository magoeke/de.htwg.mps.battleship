package de.htwg.mps.battleship.model

case class Field(shot: Boolean) {
  override def toString: String = "Field: (shot=" + shot.toString() + ")"
}