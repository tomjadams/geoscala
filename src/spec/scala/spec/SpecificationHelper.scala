package spec

import java.io.File
import java.net.URL
import scalaz.OptionW
import scalaz.OptionW._

object SpecificationHelper {
  // TODO Add an implicit param here for the data root directory (specDataRootDir).
  def dataFile(path: String): FilePath = dataFileFromAntBuild(canonicalise(path))

  def dataFileFromAntBuild(path: String) = getResource(canonicalise(path)) | dataFileFromIntelliJ(path)

  def dataFileFromIntelliJ(path: String) = {
    val intellijPath = canonicalise("geoscala" + canonicalise(path))
    getResource(intellijPath).err("No resource found at '" + path + "' or '" + intellijPath + "'")
  }

  def getResource(path: String) = SpecificationHelper.getClass.getResource(path)

  def canonicalise(path: String) = if (path.startsWith("/")) path else "/" + path

  private implicit def toFilePath(url: URL): FilePath = new File(url.toURI)

  private implicit def toOptionW[A](a: A): OptionW[A] = (a: Option[A])
}
