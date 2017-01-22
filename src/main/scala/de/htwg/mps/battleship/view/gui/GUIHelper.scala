package de.htwg.mps.battleship.view.gui

import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive
import de.htwg.mps.battleship.controller.{RegisterUI, UpdateUI, Winner}

import scalafx.application.Platform

class GUIHelper(val controller: ActorRef, gUIBoarder: GUIBoarder, val input: Boolean) extends Actor{
  def this(controller: ActorRef, gUIBoarder: GUIBoarder) { this(controller,gUIBoarder, false) }
  controller ! RegisterUI

  override def receive: Receive = {
    case infos: UpdateUI => Platform.runLater(gUIBoarder.update(infos)); if(input) {context.children.foreach(_ ! "")}
    case Winner(player) => Platform.runLater(gUIBoarder.win())
  }

  //gui.launch()
  //gui.main(args)
}
