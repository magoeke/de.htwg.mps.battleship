package de.htwg.mps.battleship.view.tui

import de.htwg.mps.battleship.controller.BattleshipController

class TUI(val controller: BattleshipController) {

  printTUI

  def handleInput(input: String) = {
    println("Input " + input)
    controller.handleCommand(input)
  }

  def printTUI = {
    println("Possible commands: \"new\", \"set X,X\", \"fire X,X\", \"quit\"")
  }
}