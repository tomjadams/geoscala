package com.googlecode.furnace.analyse.blast

import System._
import analyse.OutputFormat._
import java.io.File
import util.Logger.info
import util.io.FilePath
import util.io.FilePath._
import util.process.Process._
import util.process.CommandLineProcess
import util.process.CommandLineProcess._

sealed trait CommandLineBlast {
  private lazy val tempDir = getProperty("java.io.tmpdir")

  def unary_!(): AnalysisResult = this match {
    case CommandLineBlast_(indentifier, config) => execute(indentifier, config)
  }

  private def execute(identifier: SequenceIdentifier, config: BlastConfiguration) = {
    val outputFile = new File(tempDir, "BlastReport_" + identifier + config.outputFormat.fileExtension)
    val executable = config.blastHome + "/bin/" + config.searchUtility.name
    val c = command(executable)("-p", config.program.name)("-e", config.expectation.toString)("-d", config.database)("-i", config.inputSequence)("-o", outputFile)("-T", config.outputFormat.isHtml)
    info("Invoking BLAST synchronously using command: " + c.commandLine)
    val p = c.executeInDir(config.blastHome)
    if (p.waitFor == 0) {
      BlastAnalysisResult(identifier, outputFile, config.outputFormat)
    } else {
      error("Error running blast command, command output:\n" + p.error)
    }
  }

  private implicit def fileToString(f: File): String = f: FilePath

  private implicit def booleanToBlastCommandString(b: Boolean): String = if (b) "T" else "F"
}

private final case class CommandLineBlast_(indentifier: SequenceIdentifier, config: BlastConfiguration) extends CommandLineBlast

object CommandLineBlast {
  def blast(identifier: SequenceIdentifier, config: BlastConfiguration): CommandLineBlast = CommandLineBlast_(identifier, config)
}
