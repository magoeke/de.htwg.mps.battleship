package de.htwg.mps.battleship.controller

class BattleshipController {

  var turn = 0;
  val SetPattern = "set\\s\\d*,\\d*".r
  val FirePattern = "fire\\s\\d*\\*".r

  def handleCommand(command: String) = {
    command match {
      case "new" => true
      case "quit" => false
      case SetPattern() => true
      case FirePattern() => true
      case _ => true
    }
  }

}