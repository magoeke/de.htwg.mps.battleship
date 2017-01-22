package de.htwg.mps.battleship.view.gui

import java.io.File
import java.util.concurrent.TimeUnit

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorRef}
import de.htwg.mps.battleship.Point
import de.htwg.mps.battleship.controller._
import de.htwg.mps.battleship.controller.command.{Fire, NewGame, QuitGame, SetShip}

import scala.util.Try
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.event
import scalafx.event.ActionEvent
import scalafx.geometry.{Insets, Orientation, Pos}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.{Node, PerspectiveCamera, Scene}
import scalafx.scene.control._
import scalafx.scene.layout.{TilePane, _}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.input.MouseEvent
import scalafx.scene.media.{Media, MediaPlayer, MediaView}
import scalafx.scene.paint.{Color, LinearGradient, Stops}
import scalafx.scene.shape.Rectangle
import scalafx.scene.text.Font
import scalafx.stage.{Popup, PopupWindow}

class GUIBoarder(val controller: ActorRef, gameSize : Int) extends JFXApp {

  val startWidth = 1695
  val startHeight = 800
  var setShips: List[Point] = List()
  var fireShips: List[Point] = List()
  var gameInformation = new GameInformation(null, null, null)
  //var gameInformation : GameInformation
  //implicit val timeout = akka.util.Timeout(5, TimeUnit.SECONDS)

  def buttonSetShips(visibility: Boolean) = new Button {
    text = "Set Ships"
    margin = Insets(10)
    minHeight = 40
    minWidth = 240
    visible = visibility
    onAction = (e: ActionEvent) => {
      if (setShips.length > 1) {
        if(setShips(0).x == setShips(1).x || setShips(0).y == setShips(1).y) controller ! {getPoints(setShips(0),setShips(1)) match { case (p1: Point, p2: Point)=> SetShip(p1, p2)}} else reDrawSplit()
        setShips = List()
      }
    }
  }

  def buttonFire(visibility: Boolean) = new Button {
    text = "Fire"
    margin = Insets(10)
    minHeight = 40
    minWidth = 240
    visible = visibility
    onAction = (e: ActionEvent) => {
      if (fireShips.length > 0) {
        drawNextPlayer()
        visible = false
      }
    }
  }

  def popupWindow: List[Node] = List(new ProgressIndicator {
    prefWidth = 100
    prefHeight = 100
  }, new Button {
    minWidth = 240
    minHeight = 30
  }
  )

  val popupButton = new Button {
    minWidth = 240
    minHeight = 30
  }
  val popupSpinner = new ProgressIndicator {
    maxWidth = 100
    maxHeight = 100
  }

  val popupDialog = new VBox {
    alignment = Pos.TopCenter

    children = List(popupSpinner,new Button {
      text = "Next Player"
      autosize()
      alignment = Pos.Center
      onAction = (e: ActionEvent) => {controller ! Fire(fireShips(0)); fireShips = List()}
      })
    //buttonTypes.add(ButtonType.Next)
    //content.add(popupButton)


    //show(stage)
  }

  def buttonNext(visibility: Boolean) = new Button {
    text = "Next Player"
    margin = Insets(10)
    minHeight = 40
    minWidth = 240
    visible = visibility
    //onAction = (e: ActionEvent) => {splitPanel.items.clear(); splitPanel.items ++= Seq(reg1,popupDialog)}
  }

  def shipPanel(shipId: Int, toSet: Int) = new BorderPane {
    maxHeight = 80
    maxWidth = 240
    center = shipImage(shipId)
    bottom = new Label(
      text = "Ship for " + shipId + " Fields.  To set: " + toSet
    )
  }

  def shipImage(shipId: Int) = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/Ship" + shipId + ".gif"))
    fitWidth = 140 + 20 * (shipId - 1)
    fitHeight = 35
  }

  def getMenuBarLeft(visSet: Boolean, visFire: Boolean, visNext: Boolean, ship1toSet: Int, ship2toSet: Int,ship3toSet: Int,ship4toSet: Int,ship5toSet: Int): List[Node] = {
    List(buttonSetShips(visSet),
      buttonFire(visFire), shipPanel(5, ship5toSet), shipPanel(4,  ship4toSet), shipPanel(3, ship3toSet), shipPanel(2, ship2toSet),
      buttonNext(visNext))
  }

  val reg1 = new TilePane() {
    orientation = Orientation.VERTICAL
    minWidth = 260
    maxWidth = 260
    vgap = 30
    children = getMenuBarLeft(true,false, false,0,0,0,0,0)
  }

  def rightSplit(playerBoard: Array[Array[FieldState.Value]], enemeyBoard: Array[Array[FieldState.Value]]) = new SplitPane {

    items ++= Seq(game(Pos.TopLeft, Color.Green, false, playerBoard), game(Pos.TopRight, Color.OrangeRed, false, enemeyBoard))
  }

  def reg2(player: String, playerBoard: Array[Array[FieldState.Value]], enemeyBoard: Array[Array[FieldState.Value]]) = new VBox {
    children = List(new BorderPane() {
      minHeight = 20
      left = new Label {
        text = "Current Player: " + player
      }
      right = new Label {
        text = "Enemy Player"
      }
    }, rightSplit(playerBoard, enemeyBoard))
  }

  //val playerField = new TilePane{children = List(square,square,square)}
  // val enemeyField = new TilePane{children = List(square,square,square)}

  val backgroundImage = new ImageView {
    image = new Image(this.getClass.getResourceAsStream("/Battleship.jpg"))
    fitWidth = startWidth
    fitHeight = startHeight
    //fitHeight = Double.MaxValue
  }

  val splitPanel = new SplitPane {
    //background = backgroundImage
    orientation = Orientation.HORIZONTAL
    minHeight = Double.MaxValue
    items ++= Seq(reg1, reg2("",null, null))
    id = "hiddenSplitter"
    //stylesheets += hiddenSplitPaneCss
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
          onAction = (e: ActionEvent) => {children = List(topBar, splitPanel); controller ! NewGame()}
        },
          new Button {
            text = "Exit Game"
            minWidth = 75
            onAction = (e: ActionEvent) => sys.exit(0)
          },
          new Label {
            minWidth = Double.MaxValue
            minHeight = 20
          })
      }, backgroundImage)
  }


  stage = new JFXApp.PrimaryStage {
    title = "Battleship"
    minWidth = startWidth
    minHeight = startHeight
    maxHeight = startHeight
    maxWidth = startWidth
    //icons = backgroundImage
    scene = new Scene {
      root = topPanel

    }
  }

  def square(col: Int, row: Int, state: FieldState.Value, color: Color, board: Array[Array[FieldState.Value]]) = new ownRectagle(new Point(row, col)) {
    width = 70
    height = 70
    state match {
      case FieldState.EMPTY =>  fill = Color.LightBlue
      case FieldState.HIT => fill = Color.Red
      case FieldState.MISS => fill = Color.Yellow
      case FieldState.SHIP => fill = Color.Gray
      case FieldState.SHOT => fill = Color.Red
      case FieldState.SELECT => fill = color
    }

    //if(fired)fill = Color.Red else fill = Color.Bisque
    //fill = Color.Bisque
    stroke = Color.Burlywood
    //fill <== when(hover) choose Color.Green otherwise Color.Bisque
    onMouseClicked = (e: MouseEvent) => {
      if (state match {case FieldState.EMPTY => true }) {
        if (color.equals(Color.Green)) {
          setShips ::= p
          if (setShips.length > 2)
            board(setShips(2).x)(setShips(2).y) = FieldState.EMPTY
        } else {
          fireShips ::= p
          if (fireShips.length > 1)
            board(fireShips(1).x)(fireShips(1).y) = FieldState.EMPTY
        }
        board(p.x)(p.y) = FieldState.SELECT
        reDrawSplit()
      }
    }
  }

  def game(pos: Pos, player: Color, disabling: Boolean, board: Array[Array[FieldState.Value]]): GridPane = new GridPane {
    alignmentInParent = pos
    disable = disabling

    val rowInfo = new RowConstraints(minHeight = 70, prefHeight = 70, maxHeight = 70)
    val colInfo = new ColumnConstraints(minWidth = 70, prefWidth = 70, maxWidth = 70)
    gridLinesVisible = true
    for (i <- 0 to 9) {
      rowConstraints += rowInfo
      columnConstraints += colInfo
    }
    for (i <- 0 to gameSize - 1)
      for (x <- 0 to gameSize - 1) {
        //GridPane.setConstraints(square(i,x,false, player), i, x)
        val state = if (board != null) board(x)(i) else FieldState.EMPTY
        add(square(i, x, state, player, board), i, x)
      }
  }

  val borderGameField = new BorderPane() {
    minHeight = 20
    left = new Label {
      text = "Current Player"
    }
    right = new Label {
      text = "Enemy Player"
    }
  }

  def reDrawSplit(): Unit ={
    splitPanel.items.clear()
    if(this.gameInformation.player.equals("player0")) splitPanel.items ++= Seq(reg1, reg2(this.gameInformation.player, this.gameInformation.boards(0), this.gameInformation.boards(1))) else splitPanel.items ++= Seq(reg1, reg2(this.gameInformation.player, this.gameInformation.boards(1), this.gameInformation.boards(0)))
  }

  def drawNextPlayer() = {
    splitPanel.items.clear()
    splitPanel.items ++= Seq(reg1, popupDialog)
  }

  def reDraw(): Unit = {
    if (!this.gameInformation.boards.isEmpty ){
      if (this.gameInformation.setableShips.isEmpty) {
        reg1.children = getMenuBarLeft(false, true, false,0,0,0,0,0)
      }else{
        reg1.children = getMenuBarLeft(true, false, false,
          this.gameInformation.setableShips.filter(_==1).length,
          this.gameInformation.setableShips.filter(_==2).length,
          this.gameInformation.setableShips.filter(_==3).length,
          this.gameInformation.setableShips.filter(_==4).length,
          this.gameInformation.setableShips.filter(_==5).length)
      }
    }
    reDrawSplit()
  }

  def update(infos: UpdateUI): Unit = {
    this.gameInformation = infos.gameInformation.filter(info => info.player == infos.currentPlayer).head

    // another cool bugfix
    Try(reDraw())

  }

  def win()={
    splitPanel.items.clear()
    splitPanel.items ++= Seq(reg1, winPanel)
  }

  def winPanel = new VBox{
    alignment = Pos.TopCenter
    children = new Label {
      text = gameInformation.player + " has won!"
      font = new Font("Arial", 30)
    }
    autosize()
  }

  private def getPoints(p1 : Point, p2 : Point):(Point, Point)={
    if(p1.x > p2.x) Tuple2(p2,p1) else Tuple2(p1, p2)
  }

  case class ownRectagle(p: Point) extends Rectangle()

}
