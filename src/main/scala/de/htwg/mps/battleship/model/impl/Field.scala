package scala.de.htwg.mps.battleship.model.impl

import de.htwg.mps.battleship.model.IField

case class Field(shot: Boolean) extends IField {
  override def toString: String = "Field: (shot=" + shot.toString() + ")"
}