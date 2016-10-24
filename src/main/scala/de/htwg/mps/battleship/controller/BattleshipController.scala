package de.htwg.mps.battleship.controller

import de.htwg.mps.battleship.model._

class BattleshipController(gamefield: Gamefield) {

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
  
  def set(start: Point, end: Point) {
    // TODO: check if point are in right order
    val field = gamefield.field
    var fieldList = List()
    if(start.x != end.x){
      for(x <- start.x until end.x) { field(x)(start.y) :: fieldList }
    }
    else{
      for(y <- start.y until end.y) { field(start.x)(y) :: fieldList }
    }
    
    fieldList
  }

}