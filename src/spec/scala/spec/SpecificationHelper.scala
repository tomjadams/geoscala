package spec

import java.io.File
import java.net.URL
import scalaz.OptionW
import scalaz.OptionW._
import ReadableFile._

object SpecificationHelper {
  def dataFileContents(path: String) = (dataFile(path): File).slurp

  def dataFile(path: String): FilePath = getResource(canonicalise(path)).err("No resource found at '" + path + "'")

  def getResource(path: String) = SpecificationHelper.getClass.getResource(path)

  def canonicalise(path: String) = if (path.startsWith("/")) path else "/" + path

  private implicit def toFilePath(url: URL): FilePath = new File(url.toURI)

  private implicit def toOptionW[A](a: A): OptionW[A] = (a: Option[A])
}
