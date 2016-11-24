package de.htwg.mps.battleship.controller

import de.htwg.mps.battleship.model._
import de.htwg.mps.battleship.controller.command._

import scala.collection.mutable.ListBuffer
import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}

class BattleshipController(var players: List[IPlayer]) {
  require(players.length >= 2, "Number of player must be greater or equals two!")

  var turn = 0
  var losers = List[IPlayer]()

  def handleCommand(command: Command): Boolean = {
    command match {
      case QuitGame() => false
      case Nothing() => true
      case NewGame() => true
      case Fire(point) => fire(point)
      case SetShip(start, end) => setShip(start, end)
      case _ => true
    }
  }

  /*
   * Bombardes all enemy ships @point.
   */
  def fire(point: Point): Boolean = {
    if (setableShips.isEmpty) {
      players = for (player <- players) yield { if (player != currentPlayer) player.fire(point) else player }
      players = removeDeadPlayers
      if (getWinner.isDefined) return false
      turn += 1
    }
    true
  }

  def setShip(start: Point, end: Point) : Boolean = {
    val fieldList = if (start.x > start.x) getShipPos(end, start) else if (start.y > end.y) getShipPos(end, start) else getShipPos(start, end)
    val old_ships = currentPlayer.board.ships
    val ships = if (isAlreadySet(fieldList, old_ships)) transformShipList(fieldList) else old_ships

    players = for (player <- players) yield { if (currentPlayer == player) currentPlayer.updateShips(ships) else player }

    val available = for (ship <- ships if !ship.initialized) yield ship
    if (available.length == 0) turn += 1
    true
  }

  /*
   * Helper method to get ship positions.
   */
  private def getShipPos(start: Point, end: Point): List[IField] = {
    val field = currentPlayer.board.field
    var fieldListBuffer = new ListBuffer[IField]()

    if (start.x != end.x) {
      for (x <- (start.x to end.x).toList) yield field(x)(start.y)
    } else {
      for (y <- (start.y to end.y).toList) yield field(start.x)(y)
    }
  }

  /*
   * Checks if coordinate on board is already set by another ship of the player.
   */
  private def isAlreadySet(fieldList: List[IField], ships: List[IShip]): Boolean = {
    val set: List[Boolean] = ships.flatMap(ship => ship.pos.flatMap(pos => fieldList.map(field => field eq pos)))
    !set.contains(true)
  }

  private def transformShipList(fieldList: List[IField]): List[IShip] = {
    var set = false

    for (ship <- currentPlayer.board.ships) yield {
      if (ship.size == fieldList.length && !ship.initialized && !set) {
        set = true
        Ship(fieldList)
      } else {
        ship
      }
    }
  }

  /*
   * Represents current board state with enums.
   */
  def gamefieldView : Array[Array[FieldState.Value]] = {
    val gamefield = currentPlayer.board
    gamefield.field.map(row => row.map(field => chooseFieldState(gamefield.ships, field)))
  }

  /*
   * Represents the current field on board as an enumeration.
   */
  private def chooseFieldState(ships: List[IShip], field: IField) : FieldState.Value = {
    val shipFieldL = ships.flatMap(ship => ship.pos.map(pos => pos eq field))
    val shipField = shipFieldL.contains(true)

    if (shipField && field.shot) { FieldState.HIT }
    else if (shipField && !field.shot) { FieldState.SHIP }
    else if (!shipField && field.shot) { FieldState.MISS }
    else { FieldState.EMPTY }
  }

  def setableShips : List[IShip] = for (ship <- currentPlayer.board.ships if !ship.initialized) yield ship
  def createPoint(x: Int, y: Int) : Point = Point(x, y)
  def currentPlayer : IPlayer = players(turn % players.length)

  def removeDeadPlayers : List[IPlayer] = {
    val deadPlayers = getDeadPlayers
    losers = losers ::: deadPlayers
    for (player <- players if !deadPlayers.contains(player)) yield player
  }

  private def getDeadPlayers: List[IPlayer] = for (player <- players if areAllShipsDead(player)) yield player
  private def areAllShipsDead(player: IPlayer): Boolean = (for (ship <- player.board.ships if !ship.initialized || !ship.isDead) yield ship).length == 0
  def getWinner: Option[IPlayer] = if (players.length == 1) Some(players(0)) else None;

}

object BattleshipController {
  def setupGame: List[Player] = {
    val size = 10
    val ships = List(Ship(2))
    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
    List(Player(gamefield, "player0"), Player(gamefield, "player1"))
  }
}
