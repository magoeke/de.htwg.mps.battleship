package de.htwg.mps.battleship

import de.htwg.mps.battleship.controller.BattleshipController
import de.htwg.mps.battleship.view.tui.TUI

import scala.io.StdIn._

object Battleship {
  val c = new BattleshipController()
  val tui = new TUI(c)

  def main(args: Array[String]) {
    while (tui.handleInput(readLine())) {}
  }
}