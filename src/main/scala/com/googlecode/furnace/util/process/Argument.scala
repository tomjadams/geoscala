package com.googlecode.furnace.util.process

sealed trait Argument

private final case class ArgumentWithoutValue(option: String) extends Argument {
  override def toString = option
}

private final case class ArgumentWithValue(option: String, value: String) extends Argument {
  override def toString = option + " " + value
}

object Argument {
  def argument(option: String): Argument = ArgumentWithoutValue(option)
  def argument(option: String, value: String): Argument = ArgumentWithValue(option, value)
}
