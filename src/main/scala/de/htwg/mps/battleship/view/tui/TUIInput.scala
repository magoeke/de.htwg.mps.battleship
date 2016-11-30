package de.htwg.mps.battleship.view.tui

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.controller.command._

import scala.io.StdIn

class TUIInput(val controller: ActorRef) extends Actor {

  override def receive: Receive = {
    case _ => controller ! getCommand(StdIn.readLine())
  }

  val SetPattern = "set\\s+\\d+,\\d+\\s+end\\s+\\d+,\\d+".r
  val FirePattern = "fire\\s+\\d+,\\d+".r
  val NumberPattern = "\\d+,\\d+".r

  private def getCommand(input: String): Command = {
    input match {
      case "new" => NewGame()
      case "quit" => QuitGame()
      case SetPattern() => getPoints(input) match { case (p1: Point, p2: Point) => SetShip(p1, p2) }
      case FirePattern() => Fire(getPoint(input))
      case _ => Nothing()
    }
  }

  private def getPoints(s: String): (Point, Point) = {
    val commands = s.split("end")
    Tuple2(getPoint(commands(0)), getPoint(commands(1)))
  }

  private def getPoint(s: String): Point = {
    val coords = NumberPattern.findFirstIn(s).get.split(",")
    Point(coords(0).toInt, coords(1).toInt)
  }
}
