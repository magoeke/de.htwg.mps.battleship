package de.htwg.mps.battleship.view.gui

import javafx.embed.swing.JFXPanel

import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.controller.{RegisterUI, UpdateUI, Winner}

import scalafx.application.Platform

class GUIHelper(val controller: ActorRef, gUIBoarder: GUIBoarder, val input: Boolean) extends Actor{
  def this(controller: ActorRef, gUIBoarder: GUIBoarder) { this(controller,gUIBoarder, false) }
  controller ! RegisterUI

  // cool bug fix
  new JFXPanel();
  override def receive: Receive = {
    case infos: UpdateUI => Platform.runLater(gUIBoarder.update(infos)); if(input) {context.children.foreach(_ ! "")}
    case Winner(player) => Platform.runLater(gUIBoarder.win())
  }
}
