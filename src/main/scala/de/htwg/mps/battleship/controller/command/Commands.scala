package de.htwg.mps.battleship.controller.command

import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.controller.FieldState

trait Command {}
case class Fire(point: Point) extends Command
case class SetShip(start: Point, end: Point) extends Command
case class QuitGame() extends Command
case class NewGame() extends Command
case class Nothing() extends Command

case class Ships()
case class Boards(view: String)
case class Player()
case class AShips(ships: List[Int])
case class ABoards(boards: List[Array[Array[FieldState.Value]]])
case class APlayer(name:String)

