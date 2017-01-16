package de.htwg.mps.battleship.view.gui

import akka.actor.ActorRef
import javafx.event.EventHandler
import javafx.scene.input.MouseEvent

import de.htwg.mps.battleship.controller.UpdateUI

import scala.swing.Reactor
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.event.ActionEvent
import scalafx.geometry.Insets
import scalafx.scene.Scene
import scalafx.scene.control.{Menu, MenuBar, MenuItem}
import scalafx.scene.layout.{BorderPane, Pane}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Polygon


class GUI (val controller: ActorRef) extends JFXApp{
  val WindowWidth = 520
  val WindowHeight = 620
  val InsetsSize = 20
  val Width = 30
  val ScaleX = 5

  lazy val menu = new Menu("B-Ship") {
    items = List(
      new MenuItem("Restart") {

      },
      new MenuItem("Exit") {
        onAction = (e: ActionEvent) => sys.exit(0)
      }
    )
  }

  lazy val menuBar = new MenuBar {
    useSystemMenuBar = true
    minWidth = WindowWidth
    menus.add(menu)
  }

  // main window stage
  stage = new PrimaryStage {
    title = "B-Ship"
    scene = new Scene(WindowWidth, WindowHeight) {
      fill = Color.Black
      content = new BorderPane {
        top = menuBar
      }
    }
  }

  def update(infos: UpdateUI){

  }
}
