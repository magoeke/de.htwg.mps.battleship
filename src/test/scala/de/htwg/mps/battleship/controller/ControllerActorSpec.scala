package de.htwg.mps.battleship.controller

import akka.actor.ActorRef
import de.htwg.mps.battleship.Spec
import de.htwg.mps.battleship.model.IField

import scala.collection.mutable.ListBuffer
import scala.de.htwg.mps.battleship.model.impl.{Field, Gamefield, Player, Ship}


class ControllerActorSpec extends Spec{
//  "A Controller Actor" should {
//    val size = 10
//    val ships = List(Ship(2))
//    val gamefield = Gamefield(Array.fill[IField](size, size) { Field(false) }, ships)
//    val players = List(Player(gamefield, "player0"), Player(gamefield, "player1"))
//    val controllerActor = TestActorRef(new ControllerActor(players))
//
//    "have a userinterface" in {
//      controllerActor.userInterfaces shouldBe a[ListBuffer[ActorRef]]
//      controllerActor.actorToUser shouldBe a[Map[ActorRef, String]]
//      //controllerActor.userInterfaces shouldBe a[ActorRef]
//    }
//  }
}
