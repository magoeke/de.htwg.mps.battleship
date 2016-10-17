package de.htwg.mps.battleship.model

case class Field(shot: Boolean) {
  override def toString: String = shot.toString()
}