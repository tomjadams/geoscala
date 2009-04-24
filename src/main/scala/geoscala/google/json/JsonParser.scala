package geoscala.google.json

import net.sf.json.{JSONArray, JSONObject}, JSONObject._, JSONArray._
import scalaz.OptionW, OptionW._
import types.Placemark

final class JsonParser {
  def placemarks(json: String): Either[Throwable, List[Placemark]] =
    try {
      val maybePlacemarks = onull(JSONObject.fromObject(json).optJSONArray("Placemark"))
      val maybeParsedPlacemarks = maybePlacemarks.map(placemarks => 0.until(placemarks.size).map(placemarks.getJSONObject(_)).map(parsePlaceMark(_)).toList)
      maybeParsedPlacemarks.toRight[Throwable](exception("Unable to find Placemark array"))
    }
    catch {
      case t: Throwable => Left(t)
    }

  def parsePlaceMark(placemark: JSONObject): Placemark = {
    error("huzzah")
  }

  def exception(msg: String) = new Exception(msg)
}
