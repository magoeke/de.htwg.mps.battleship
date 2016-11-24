package de.htwg.mps.battleship.model

/**
  * Created by max on 24.11.16.
  */
trait IShip {
  def pos: List[IField]
  def size: Int
  def initialized: Boolean
  def isDead: Boolean
  def copyShip(pos: List[IField]) : IShip
}
