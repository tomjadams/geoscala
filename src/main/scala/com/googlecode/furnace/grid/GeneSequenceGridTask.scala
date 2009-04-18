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

package com.googlecode.furnace.grid

import analyse.{AnalysisResult, SequenceIdentifier}
import java.util.{List => JavaList}
import org.gridgain.grid.{GridTaskSplitAdapter, GridJob, GridJobResult}
import sequence.GeneSequence

final class GeneSequenceGridTask extends GridTaskSplitAdapter[Iterator[GeneSequence], List[AnalysisResult]] {
  def split(gridSize: Int, inputSequences: Iterator[GeneSequence]) = {
    val jobs = new java.util.ArrayList[GridJob]
    inputSequences.foreach(s => jobs.add(SequenceGridJob(s)))
    jobs
  }

  def reduce(results: JavaList[GridJobResult]) = error("")
}

final case class SequenceGridJob(sequence: GeneSequence) extends GridJob {
  import analyse.{AnalysisResult, OutputFormat, SequenceIdentifier}
  import analyse.OutputFormat._
  import analyse.SequenceIdentifier._
  import analyse.blast.BlastAnalysisResult
  import java.io.Serializable
  import util.io.FilePath
  import util.io.FilePath._

  override def cancel {
  }

  override def execute = BlastAnalysisResult(id("/foo/bar.in", 0, 0), "/foo/bar.out", text)
}

object Gridity {
  import GridBootstrapper._
  import org.gridgain.grid.GridTaskFuture
  import scalaz.list.NonEmptyList, NonEmptyList._
  import sequence.GeneSequence._
  import sequence.Base

  def main(args: Array[String]): Unit = {
    startMasterNode
    try {
      val future: GridTaskFuture[List[AnalysisResult]] = masterNode.execute(classOf[GeneSequenceGridTask], sequences("ACGT", "ACGT"));
      val results: List[AnalysisResult] = future.get();
    } finally {
      stopMasterNode
    }
  }

  private def sequences(sequences: String*) = sequences.map(s => geneSequence(baseSeq(s))).elements

  private def baseSeq(bases: String): NonEmptyList[Base] = list(bases.map(_.toByte: Base).toList)
}
