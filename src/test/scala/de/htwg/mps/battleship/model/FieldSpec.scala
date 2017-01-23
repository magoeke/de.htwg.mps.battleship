package de.htwg.mps.battleship.model

import de.htwg.mps.battleship.Spec

import scala.de.htwg.mps.battleship.model.impl.Field


class FieldSpec extends Spec{
  "A new shotted Field" should {
    val field = new Field(true)

    "should be true" in {
    field.shot should be (true)
    }

    "should print" in {
      field.toString should be("Field: (shot=" + field.shot.toString() + ")")
    }
  }

  "A new not shotted Field" should {
    val field = new Field(false)

    "should be true" in {
      field.shot should be (false)
    }
    "should print" in {
      field.toString should be("Field: (shot=" + field.shot.toString() + ")")
    }
  }
}
