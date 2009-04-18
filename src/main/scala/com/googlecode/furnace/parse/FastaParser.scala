/*
 * Copyright 2006-2008 Workingmouse
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.googlecode.furnace.parse

import sequence.GeneSequence
import scala.collection.mutable.{ArrayBuffer => MutableArray}
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.{Base, GeneSequence}
import sequence.Base._
import sequence.GeneSequence._

/**
 * A <a href="http://en.wikipedia.org/wiki/Fasta_format">FASTA</a> parser.
 * The parser returns an iterator of sequences, where each sequence is <var>sliceSize</var> long, the header is ignored (if present).
 * Note that the parser is lazy, in that the underlying <code>input</code> will not be read until <code>next()</code> is called on the returned
 * iterator.
 */
object FastaParser {
  private lazy val lineSeperators = List('\r', '\n').map(_.toByte)
  private lazy val whitespace = List('\r', '\n', '\t', ' ').map(_.toByte)
  private lazy val validBases = bases.map(_.code.toByte)

  def parse(input: Iterator[Byte], sliceSize: Int): Option[Iterator[GeneSequence]] =
    if (input.hasNext) {
      val noLeadingWhitespace = input.dropWhile(whitespace.contains(_))
      val firstChar = noLeadingWhitespace.next
      if (!firstChar.equals('>'.toByte)) {
        error("Input sequence contains no header: " + firstChar.toChar + noLeadingWhitespace.takeWhile(!lineSeperators.contains(_)).map(_.toChar).mkString)
      } else {
        val noHeader = noLeadingWhitespace.dropWhile(!lineSeperators.contains(_))
        val iter = new Iterator[GeneSequence] {
          def hasNext = noHeader.hasNext

          def next = {
            val x = noHeader.filter(validBases.contains(_)).map(byteToBase(_)).zipWithIndex.takeWhile(_._2 < sliceSize).map(_._1).toList
            geneSequence(list(x))
          }
        }
        Some(iter)
      }
    } else {
      None
    }
}
