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

package com.googlecode.furnace.sequence

import System._
import java.io.{BufferedOutputStream, File, FileOutputStream}
import analyse.SequenceIdentifier
import util.io.FilePath
import util.io.FilePath._

sealed trait SequenceFile {
  private lazy val tempDir = getProperty("java.io.tmpdir")

  def sequence: GeneSequence

  def write(identifier: SequenceIdentifier): FilePath = {
    val inputFile = new File(tempDir, "Sequence_" + identifier + ".fasta")
    val out = new BufferedOutputStream(new FileOutputStream(inputFile))
    try {
      (">Source=" + identifier.inputSequence + ",slice=" + identifier.sliceSize + ",split=" + identifier.splitId).foreach(out.write(_))
      out.write('\n')
      sequence.toString.foreach(out.write(_))
      out.write('\n')
      out.flush
    } finally {
      out.close
    }
    inputFile
  }
}

private case class SequenceFile_(sequence: GeneSequence) extends SequenceFile

object SequenceFile {
  implicit def geneSequenceToSequenceFile(sequence: GeneSequence): SequenceFile = SequenceFile_(sequence)
}