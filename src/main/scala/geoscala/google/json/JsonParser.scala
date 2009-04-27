package geoscala.google.json

import net.sf.json.{JSONArray, JSONObject}, JSONObject._
import scalaz.OptionW, OptionW._
import types.Placemark

final class JsonParser {
  import NullSafeJSONObject._

  def placemarks(json: String): Either[Throwable, List[Placemark]] =
    try {
      val maybePlacemarks = fromObject(json).jsonArray("Placemark")
      val maybeParsedPlacemarks = maybePlacemarks.map(placemarks => 0.until(placemarks.size).map(placemarks.getJSONObject(_)).map(parsePlaceMark(_)).toList)
      maybeParsedPlacemarks.toRight[Throwable](exception("Unable to find Placemark array"))
    } catch {
      case t: Throwable => Left(t)
    }

  def parsePlaceMark(placemark: JSONObject): Placemark = {
    println("Parsing placemark: " + placemark)

    //    placemark.optString()
    //    new Placemark()

    error("huzzah")
  }

  def exception(msg: String) = new Exception(msg)
}

private object NullSafeJSONObject {
  implicit def JSONObjectToNullSafeJSONObject(source: JSONObject): NullSafeJSONObject = new NullSafeJSONObject(source)
}

private final class NullSafeJSONObject(source: JSONObject) {
  def jsonArray(key: String): Option[JSONArray] = onull(source.optJSONArray(key))

  def string(key: String): Option[String] = onull(source.optString(key))

  def double(key: String): Option[Double] = onull(source.optDouble(key))
}
