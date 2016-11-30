package de.htwg.mps.battleship.controller

import akka.actor.{ActorRef, ActorSystem, Props}
import de.htwg.mps.battleship.model.IPlayer

object ControllerFactory {
  def create(actorSystem: ActorSystem, players: List[IPlayer]): ActorRef = actorSystem.actorOf(Props(new ControllerActor(players)))
}
