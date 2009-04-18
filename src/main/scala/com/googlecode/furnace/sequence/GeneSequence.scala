package com.googlecode.furnace.sequence

import Base._
import scalaz.list.NonEmptyList

sealed trait GeneSequence {
  def bases: NonEmptyList[Base]

  override def toString = bases.toList.mkString

  override def equals(obj: Any): Boolean = obj match {
    case GeneSequence_(b) => b.toList.equals(bases.toList)
    case _ => false
  }
}

private final case class GeneSequence_(bases: NonEmptyList[Base]) extends GeneSequence

object GeneSequence {
  implicit def geneSequence(bases: NonEmptyList[Base]): GeneSequence = GeneSequence_(bases)
}
