package de.htwg.mps.battleship.view.tui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.controller.{FieldState, IBattleshipController, RegisterUI}
import de.htwg.mps.battleship.controller.command._
import de.htwg.mps.battleship.model.Point

import scala.io.StdIn

class TUI(val controller: ActorRef) extends Actor {

  controller ! RegisterUI

  override def receive: Receive = {
    case boards: List[Array[Array[FieldState.Value]]] => update(boards)
  }

  val SetPattern = "set\\s+\\d+,\\d+\\s+end\\s+\\d+,\\d+".r
  val FirePattern = "fire\\s+\\d+,\\d+".r
  val NumberPattern = "\\d+,\\d+".r

  def update(boards: List[Array[Array[FieldState.Value]]]): Unit = {
    boards.foreach(printGamefield(_))
    println("Command: ")
    controller ! getCommand(StdIn.readLine())
  }

  def getCommand(input: String): Command = {
    input match {
      case "new" => NewGame()
      case "quit" => QuitGame()
      case SetPattern() => getPoints(input) match { case (p1: Point, p2: Point) => SetShip(p1, p2) }
      case FirePattern() => Fire(getPoint(input))
      case _ => Nothing()
    }
  }

  def getPoints(s: String): (Point, Point) = {
    val commands = s.split("end")
    Tuple2(getPoint(commands(0)), getPoint(commands(1)))
  }

  def getPoint(s: String): Point = {
    val coords = NumberPattern.findFirstIn(s).get.split(",")
    Point(coords(0).toInt, coords(1).toInt)
  }

  def printGamefield(gamefield: Array[Array[FieldState.Value]]): Unit = {
    println(s"""
${gamefield.map(row => row.map(state => fieldStateToString(state) + " ").mkString + "\n").mkString}
  """)
  }

  def fieldStateToString(state: FieldState.Value): String = {
    state match {
      case FieldState.EMPTY => "-"
      case FieldState.HIT => "X"
      case FieldState.MISS => "o"
      case FieldState.SHIP => "s"
    }
  }
}