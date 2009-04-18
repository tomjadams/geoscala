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

import Bytes._
import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import java.io.ByteArrayInputStream
import parse.FastaParser._
import scalaz.OptionW._
import scalaz.list.NonEmptyList, NonEmptyList._
import sequence.GeneSequence._

final class AFastaParserWithNoSequenceToParse {
  private val noSequence = byteIterator("")

  @Specification
  def returnsNone {
    val sequences = parse(noSequence, 10)
    expect that sequences.isEmpty isEqualTo true
  }
}

final class AFastaParserWithASequenceWithNoHeader {
  private val noHeader = byteIterator("""
      ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG""")

  @Specification {
      val expectedException = classOf[RuntimeException],
      val withMessage = "Input sequence contains no header: ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG"}
  def throwsAnError {
    parse(noHeader, 10)
  }
}

final class AFastaParserWithASequenceContainingASingleLineOfBases {
  private val sequence = byteIterator("""
      >gi|15891923|ref|NC_003103.1| Rickettsia conorii str. Malish 7, complete genome
      ATGACAAAGCTAATTATTCACTTAGTTTCAGACTCTTCCGTGCAAACTGCAAAATATACAGCAAATTCTG""")

  @Specification
  def turnsAnIteratorOfBytesIntoAnIteratorOfGeneSequences {
    val result = parse(sequence, 10)
    expect.that(result.isEmpty).isEqualTo(false)
    val sequences = result.get.toList
    // Note. Scala bug https://lampsvn.epfl.ch/trac/scala/ticket/1246, this won't work.
    expect.that(sequences(0)).isEqualTo(geneSequence(baseSeq("ATGACAAAGC")))
//    expect.that(sequences(1)).isEqualTo(geneSequence(baseSeq("TAATTATTCA")))
//    expect.that(sequences(2)).isEqualTo(geneSequence(baseSeq("CTTAGTTTCA")))
//    expect.that(sequences(3)).isEqualTo(geneSequence(baseSeq("GACTCTTCCG")))
//    expect.that(sequences(4)).isEqualTo(geneSequence(baseSeq("TGCAAACTGC")))
//    expect.that(sequences(5)).isEqualTo(geneSequence(baseSeq("AAAATATACA")))
//    expect.that(sequences(6)).isEqualTo(geneSequence(baseSeq("GCAAATTCTG")))
  }
}

final class AFastaParserWithALotOfData {
  import util.io.FilePath
  import util.io.FilePath._
  import java.io.{File, FileInputStream}
  import scalaz.javas.InputStream._
  import spec.SpecificationHelper._

  @Specification
  def isFastAndDoesNotBlowMemory {
    val file = dataFile("sequences/NC_003103_r.conorii.fasta")
    val in = new FileInputStream(file)
    try {
      parse(in, 40).fold(error("No sequences found"), (s => s.foreach(_)))
    } finally {
      in.close
    }
  }
}

object Bytes {
  import sequence.Base, Base._

  def byteIterator(bases: String): Iterator[Byte] = bases.map(_.toByte).elements
  def baseSeq(bases: String): NonEmptyList[Base] = list(bases.map(_.toByte: Base).toList)
}