/*
 * Copyright 2006-2008 Tom Adams, Workingmouse
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

package com.googlecode.furnace

import analyse.AnalysisResult
import analyse.SequenceIdentifier._
import grid.GridBootstrapper._
import invoke.LocalBlastInvoker
import java.io.{File, FileInputStream}
import java.lang.Integer.parseInt
import scalaz.OptionW._
import scalaz.javas.InputStream._
import parse.FastaParser
import util.Logger, Logger._
import util.io.FilePath
import util.io.FilePath._

object SequenceSearcher {
  lazy val invoker = new LocalBlastInvoker
  val defaultSliceSize = 40

  def main(args: Array[String]): Unit = args match {
    case Array(path, database) => run(path, database, defaultSliceSize)
    case Array(path, database, sliceSize) => run(path, database, parseInt(sliceSize))
    case _ => error("Usage: java -cp <classpath> SequenceSearcher <input_sequence_file> <path_to_database> [<bases_per_search_slice>]")
  }

  def run(inputSequence: FilePath, database: FilePath, sliceSize: Int) {
    configureLogging
    startMasterNode
    info("Processing sequence file: " + filePathToString(inputSequence) + ", slice size: " + sliceSize)
    val in = new FileInputStream(inputSequence)
    try {
//      masterNode
      FastaParser.parse(in, sliceSize).fold(Logger.error("No sequences were found in the input file"), (sequences => {
        sequences.zipWithIndex.foreach(sequenceIdPair => {
          val result = invoker.invoke(id(inputSequence, sliceSize, sequenceIdPair._2), database, sequenceIdPair._1)
          info("Completed processing split sequence " + result.identifier.inputSequenceName + "; split: " + result.identifier.splitId +
              "; slice size: " + result.identifier.sliceSize + "; output: " + result.analysisOutput)
        })
        info("Fin.")
      }))
    } finally {
      in.close
      stopMasterNode
    }
  }
}
