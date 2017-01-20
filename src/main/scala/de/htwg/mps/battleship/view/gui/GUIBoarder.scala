package de.htwg.mps.battleship.view.gui

import java.io.File

import akka.actor.ActorRef
import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.controller.UpdateUI
import de.htwg.mps.battleship.controller.command.{Fire, NewGame, QuitGame, SetShip}

import scala.swing.event.MouseEvent
import scala.swing.{BoxPanel, Reactor}
import scalafx.Includes._
import scalafx.application.JFXApp
import scalafx.event
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.{PerspectiveCamera, Scene}
import scalafx.scene.control._
import scalafx.scene.layout.{TilePane, _}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.scene.paint.{Color, LinearGradient, Stops}
import scalafx.scene.shape.Rectangle


class GUIBoarder(val controller: ActorRef) extends JFXApp {

  val startWidth = 1500
  val startHeight = 800

  def buttonSetShips() = new Button {
    text = "Set Ships"
    margin = Insets(10)
    minHeight = 40
    minWidth = 240
    visible = true
    onAction = (e: ActionEvent) => controller ! SetShip(Point(1,1),Point(1,2))
  }

  def buttonFire(visibility :Boolean) = new Button {
    text = "Fire"
    margin = Insets(10)
    minHeight = 40
    minWidth = 240
    visible = visibility
    onAction = (e: ActionEvent) => controller ! Fire(Point(1,1))
  }

  def shipPanel(shipId: Int, toSet: Int) = new BorderPane{
    maxHeight = 80
    maxWidth = 240
    center = shipImage(shipId)
    bottom = new Label(
      text = "Ship for " +shipId+ " Fields.  To set: "+toSet
    )
  }

  def shipImage(shipId: Int) = new ImageView{
    image = new Image(this.getClass.getResourceAsStream("/Ship"+shipId+".gif"))
    fitWidth = 140 + 20*(shipId-1)
    fitHeight = 35
  }

  val reg1 = new TilePane() {
    orientation = Orientation.VERTICAL
    minWidth = 260
    maxWidth = 260
    vgap = 30
    children = List(buttonSetShips(),
      buttonFire(false),shipPanel(5,1), shipPanel(4,2),shipPanel(3,2),shipPanel(2,2),shipPanel(1,2))
  }

  def reg2 = new VBox {
    children = List(new BorderPane(){
      minHeight = 20
      left = new Label{text = "Current Player"}
      right = new Label{text = "Enemy Player"}
    },
      new TilePane{children = List(square,square,square)})
    //fill = Color.Blue
    //stroke = Color.Burlywood
  }

  val backgroundImage = new ImageView{
    image = new Image(this.getClass.getResourceAsStream("/Battleship.jpg"))
    autosize()
    //fitHeight = Double.MaxValue
  }

  val splitPanel = new VBox {
    children = new SplitPane {
      //background = backgroundImage
      orientation = Orientation.HORIZONTAL
      minHeight = Double.MaxValue
      items ++= Seq(reg1, reg2)
      id = "hiddenSplitter"
      //stylesheets += hiddenSplitPaneCss
    }
  }

  val topBar = new ToolBar() {
    content = List(new Button {
      text = "New Game"
      minWidth = 75
      onAction = (e: ActionEvent) => controller ! NewGame()
    },
      new Button {
        text = "Exit Game"
        minWidth = 75
        onAction = (e: ActionEvent) => sys.exit(0)
      })
  }


  def topPanel = new VBox {
    children = List(
      new ToolBar {
      content = List(new Button {
          text = "New Game"
          minWidth = 75
          onAction = (e: ActionEvent) => children = List(topBar, splitPanel)
        },
          new Button {
            text = "Exit Game"
            minWidth = 75
            onAction = (e: ActionEvent) => sys.exit(0)
          },
        new Label{
          minWidth = Double.MaxValue
          minHeight = 20
        })
      },backgroundImage)
  }

  stage = new JFXApp.PrimaryStage {
    title = "Battleship"
    minWidth = startWidth
    minHeight = startHeight
    //icons = backgroundImage
    scene = new Scene {
      root =  topPanel

    }
  }

  def square = new Rectangle {
    width = 70
    height = 70
    fill = Color.Bisque
    stroke = Color.Burlywood

    onMouseClicked = () => fill = Color.Blue
  }

  //val file = new File("media/StartScreen.flv")

  //val mediaPlayers = new MediaPlayer(new Media(new File("media/StartScreen.flv").toURI.toString())){
  //  autoPlay = true
  //}
  //val mediaViews = new MediaView {
  //  mediaPlayer = mediaPlayers
  //  fitWidth = 800
  //  fitHeight = 400
//
  //  translateX = fitWidth()  / 2 + 200
  //  translateY = fitHeight() / 2 + 200
  //  preserveRatio = true
  //}

  def gamefiel(){}

  def update(infos: UpdateUI){
    println("Get Infos")
  }

  //private def getPoints(s: String): (Point, Point) = {
  //  val commands = s.split("end")
  //  Tuple2(getPoint(commands(0)), getPoint(commands(1)))
  //}

  //private def getPoint(s: String): Point = {
    //val coords = NumberPattern.findFirstIn(s).get.split(",")
    //Point(coords(0).toInt, coords(1).toInt)
  //}
}
