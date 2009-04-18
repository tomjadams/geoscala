package com.googlecode.furnace.analyse.blast

import java.io.Serializable
import analyse.OutputFormat
import util.io.FilePath

@serializable
@SerialVersionUID(1476000850333817230L)
final case class BlastAnalysisResult(identifier: SequenceIdentifier, analysisOutput: FilePath, outputFormat: OutputFormat) extends AnalysisResult with Serializable
