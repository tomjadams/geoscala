package geoscala.google.json

import net.sf.json.{JSONArray, JSONObject}
import types.Placemark

final class JsonParser {
  def placemarks(json: String): Either[Throwable, List[Placemark]] =
    try {
      val result: JSONObject = JSONObject.fromObject(json)
      val placemarks: JSONArray = result.getJSONArray("Placemark")
      Right(Nil)
    }
    catch {
      case t: Throwable => Left(t)
    }
}
