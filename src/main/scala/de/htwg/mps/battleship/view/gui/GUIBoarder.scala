package de.htwg.mps.battleship.view.gui

import akka.actor.ActorRef
import de.htwg.mps.battleship.controller.UpdateUI
import de.htwg.mps.battleship.controller.command.{NewGame, QuitGame}

import scala.swing.{BoxPanel, Reactor}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.Scene
import scalafx.scene.control._
import scalafx.scene.layout._
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.paint.{Color, LinearGradient, Stops}


class GUIBoarder(val controller: ActorRef) extends JFXApp {

  def buttonLeftSide(inputText: String) = new Button {
    text = inputText
    margin = Insets(10)
    minHeight = 40
    maxHeight = 40
    minWidth = 240
    maxWidth = 240
    //visible = false
  }

  def leftLabel = new VBox {
    minWidth = Double.MaxValue

      children = shipPanel(1)

  }


  val reg1 = new TilePane() {
    orientation = Orientation.VERTICAL
    minWidth = 260
    maxWidth = 260
    vgap = 30
    children = List(buttonLeftSide("Hallo1"),
      buttonLeftSide("Hallo2"),leftLabel)
  }

  val reg2 = new Pane {
    styleClass = List("rounded")
  }

  val backgroundImage = new ImageView{
    image = new Image(this.getClass.getResourceAsStream("/Battleship.jpg"))
    autosize()
    //fitHeight = Double.MaxValue
  }

  val splitPanel = new StackPane {
    children = new SplitPane {
      //background = backgroundImage
      orientation = Orientation.HORIZONTAL
      minHeight = Double.MaxValue
      items ++= Seq(reg1, reg2)
      id = "hiddenSplitter"
      //stylesheets += hiddenSplitPaneCss
    }
  }

  val topBar = new MenuBar {
    useSystemMenuBar = true
    menus = List(new Menu("Game Options") {
      items = List(new MenuItem("New Game") {
        onAction = (e: event.ActionEvent) => controller ! NewGame()
      },
        new MenuItem("Exit") {
          onAction = (e: event.ActionEvent) => sys.exit(0)
        })
    })
  }

  def topPanel = new VBox {
    children = List(new MenuBar {
      useSystemMenuBar = true
      menus = List(new Menu("Game Options") {
        items = List(new MenuItem("New Game") {
          onAction = (e: event.ActionEvent) => children = List(topBar, splitPanel)
        },
          new MenuItem("Exit") {
            onAction = (e: event.ActionEvent) => sys.exit(0)
          })
      })
    }
    ,backgroundImage)
  }

  def shipPanel(shipId: Int) = new VBox{
    minWidth = Double.MaxValue
    children = List(new ImageView{
      image = new Image(this.getClass.getResourceAsStream("/Ship"+shipId+".gif"))
    },new Label(
      text = "Ship for " +shipId+ " Fields."
    ))
  }

  stage = new JFXApp.PrimaryStage {
    title = "Battleship"
    minWidth = 1500
    minHeight = 800
    //icons = backgroundImage
    scene = new Scene {
      root =  topPanel
    }
  }


  def gamefiel(){}

  def update(infos: UpdateUI){

  }
}
