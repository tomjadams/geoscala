package spec

import java.io.File
import java.net.URL
//import spec.FilePath._
import scalaz.OptionW
import scalaz.OptionW._

object SpecificationHelper {
   def dataFile(path: String): FilePath = dataFileFromAntBuild(canonicalise(path))

  def dataFileFromAntBuild(path: String) = getResource(canonicalise(path)) | dataFileFromIntelliJ(path)

  def dataFileFromIntelliJ(path: String) = {
    val intellijPath = canonicalise("furnace" + canonicalise(path))
    getResource(intellijPath).err("No resource found at '" + path + "' or '" + intellijPath + "'")
  }

  def getResource(path: String) = SpecificationHelper.getClass.getResource(path)

  def canonicalise(path: String) = if (path.startsWith("/")) path else "/" + path

  private implicit def toFilePath(url: URL): FilePath = new File(url.toURI)

  private implicit def toOptionW[A](a: A): OptionW[A] = (a: Option[A])
}
