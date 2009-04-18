package com.googlecode.furnace.analyse.blast

import analyse.OutputFormat
import util.io.FilePath

sealed trait BlastConfiguration {
  def blastHome: FilePath

  def searchUtility: BlastSearchUtility

  def program: BlastProgram

  def expectation: Double

  def database: FilePath

  def inputSequence: FilePath

  def outputFormat: OutputFormat
}

private final case class BlastConfiguration_(blastHome: FilePath, searchUtility: BlastSearchUtility, program: BlastProgram, expectation: Double, database: FilePath, inputSequence: FilePath, outputFormat: OutputFormat) extends BlastConfiguration {
  def searchUtility(searchUtility: BlastSearchUtility): BlastConfiguration = this match {
    case BlastConfiguration_(b, s, p, e, d, i, o) => BlastConfiguration_(b, searchUtility, p, e, d, i, o)
  }

  def program(program: BlastProgram): BlastConfiguration = this match {
    case BlastConfiguration_(b, s, p, e, d, i, o) => BlastConfiguration_(b, s, program, e, d, i, o)
  }

  def expectation(expectation: Double): BlastConfiguration = this match {
    case BlastConfiguration_(b, s, p, e, d, i, o) => BlastConfiguration_(b, s, p, expectation, d, i, o)
  }

  def outputFormat(outputFormat: OutputFormat): BlastConfiguration = this match {
    case BlastConfiguration_(b, s, p, e, d, i, o) => BlastConfiguration_(b, s, p, e, d, i, outputFormat)
  }
}

object BlastConfiguration {
  import BlastProgram._
  import BlastSearchUtility._
  import OutputFormat._
  import System._

  def blastConfig(database: FilePath, inputSequence: FilePath): BlastConfiguration =
    if (getenv("BLAST_HOME") == null) {
      error("You did not suppy a blast home directory and the BLAST_HOME environment variable is not defined. It must point to the root of the BLAST installation directory, e.g. BLAST_HOME=/opt/blast")
    } else {
      blastConfig(getenv("BLAST_HOME"), database, inputSequence)
    }

  def blastConfig(blastHome: FilePath, database: FilePath, inputSequence: FilePath): BlastConfiguration = BlastConfiguration_(blastHome, blastall, blastn, 10.0, database, inputSequence, text)
}

sealed trait BlastSearchUtility {
  def name: String
  override def toString = name.toString
}

private final case object BlastAll extends BlastSearchUtility {
  override def name = "blastall"
}

object BlastSearchUtility {
  def blastall: BlastSearchUtility = BlastAll
}

sealed trait BlastProgram {
  def name: String
}

private final case object BlastP extends BlastProgram {
  override def name = "blastp"
}

private final case object BlastN extends BlastProgram {
  override def name = "blastn"
}

private final case object BlastX extends BlastProgram {
  override def name = "blastx"
}

private final case object TBlastN extends BlastProgram {
  override def name = "tblastn"
}

private final case object TBlastX extends BlastProgram {
  override def name = "tblastx"
}

object BlastProgram {
  def blastp: BlastProgram = BlastP
  def blastn: BlastProgram = BlastN
  def blastx: BlastProgram = BlastX
  def tblastn: BlastProgram = TBlastN
  def tblastx: BlastProgram = TBlastX
}
