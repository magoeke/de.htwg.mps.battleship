package de.htwg.mps.battleship.controller.command

import de.htwg.mps.battleship.Point

trait Command {}
case class Fire(point: Point) extends Command
case class SetShip(start: Point, end: Point) extends Command
case class QuitGame() extends Command
case class NewGame() extends Command
case class Nothing() extends Command

