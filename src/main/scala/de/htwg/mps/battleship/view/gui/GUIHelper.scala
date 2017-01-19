package de.htwg.mps.battleship.view.gui

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import de.htwg.mps.battleship.controller.{RegisterUI, UpdateUI, Winner}

class GUIHelper(val controller: ActorRef, args: Array[String], val input: Boolean) extends Actor{
  def this(controller: ActorRef, args: Array[String]) { this(controller, args, false) }
  controller ! RegisterUI

  val gui = new GUIBoarder(controller)
  gui.main(args)

  override def receive: Receive = {
    case infos: UpdateUI => gui.update(infos); if(input) {context.children.foreach(_ ! "")}
    case Winner(player) => println(player + " won!")
  }


}
