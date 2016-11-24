package de.htwg.mps.battleship

import de.htwg.mps.battleship.controller.BattleshipController
import de.htwg.mps.battleship.view.tui.TUI

import scala.de.htwg.mps.battleship.model.impl.Player
import scala.io.StdIn._

object Battleship {
  val players = BattleshipController.setupGame
  val c = new BattleshipController(players)
  val tui = new TUI(c)

  def main(args: Array[String]) {
    while (tui.handleInput(readLine())) {}
  }
}