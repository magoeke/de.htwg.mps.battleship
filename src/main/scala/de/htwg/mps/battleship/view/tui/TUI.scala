package de.htwg.mps.battleship.view.tui

import de.htwg.mps.battleship.controller.BattleshipController
import de.htwg.mps.battleship.controller.FieldState
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.Point

class TUI(val controller: BattleshipController) {

  val SetPattern = "set\\s+\\d+,\\d+\\s+end\\s+\\d+,\\d+".r
  val FirePattern = "fire\\s+\\d+,\\d+".r
  val NumberPattern = "\\d+,\\d+".r

  printTUI

  def handleInput(input: String): Boolean = {
    val result = controller.handleCommand(input match {
      case "new" => NewGame()
      case "quit" => QuitGame()
      case SetPattern() => getPoints(input) match { case (p1: Point, p2: Point) => SetShip(p1, p2) }
      case FirePattern() => Fire(getPoint(input))
      case _ => Nothing()
    })

    printTUI
    result
  }

  def getPoints(s: String): (Point, Point) = {
    val commands = s.split("end")
    Tuple2(getPoint(commands(0)), getPoint(commands(1)))
  }

  def getPoint(s: String): Point = {
    val coords = NumberPattern.findFirstIn(s).get.split(",")
    controller.createPoint(coords(0).toInt, coords(1).toInt)
  }

  def printTUI : Unit = {
    printGamefield(controller.gamefieldView)
    println(controller.currentPlayer.name)
    println(controller.setableShips.mkString)
    println("Possible commands: \"new\", \"set X,X end X,X\", \"fire X,X\", \"quit\"")
    val winner = controller.getWinner
    if (winner.isDefined) println("Winner is " + winner.get)
  }

  def printGamefield(gamefield: Array[Array[FieldState.Value]]) : Unit= {
    println(s"""
${gamefield.map(row => row.map(state => fieldStateToString(state) + " ").mkString + "\n").mkString}  
  """)
  }

  def fieldStateToString(state: FieldState.Value) : String= {
    state match {
      case FieldState.EMPTY => "-"
      case FieldState.HIT => "X"
      case FieldState.MISS => "o"
      case FieldState.SHIP => "s"
    }
  }

  // TODO: not used yet
  def update = {
    println("Output")
  }
}