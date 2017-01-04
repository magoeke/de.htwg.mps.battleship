package de.htwg.mps.battleship.view.tui

import akka.actor.{Actor, ActorRef, Kill, Props}
import de.htwg.mps.battleship.controller._


class TUI(val controller: ActorRef, val input: Boolean) extends Actor {
  def this(controller: ActorRef) { this(controller, false) }
  if(input) { val ac_inp = context.actorOf(Props(new TUIInput(controller))) }

  controller ! RegisterUI

  override def receive: Receive = {
    case infos: UpdateUI => update(infos); if(input) {context.children.foreach(_ ! "")}
    case Winner(player) => println(player + " won!")
  }

  def update(infos: UpdateUI): Unit = {
    val gameInformation = infos.gameInformation.filter(info => info.player == infos.currentPlayer).head
    gameInformation.boards.foreach(printGamefield(_))
    println("Playername: " + infos.currentPlayer)
    println("SetableShips: " + gameInformation.setableShips.map(" " + _ + " ").toString().drop(4))
    println("Possible commands: \"new\", \"set X,X end X,X\", \"fire X,X\", \"quit\"")
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