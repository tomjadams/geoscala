package geoscala.google.json

import net.sf.json.{JSONArray, JSONObject}, JSONObject._, JSONArray._
import scalaz.OptionW, OptionW._
import types.Placemark

final class JsonParser {
  def placemarks(json: String): Either[Throwable, List[Placemark]] =
    try {
      val result: JSONObject = JSONObject.fromObject(json)
      val maybePlacemarks: Option[JSONArray] = onull(result.optJSONArray("Placemark"))

      maybePlacemarks.map((placemarks: JSONArray) => {
        val x = placemarks.iterator().foreach(a => a.foreach(p => println(">> p: " + p)))
        1
      })

//      maybePlacemarks.map((placemarks: JSONArray) => {
//        toCollection(placemarks).map((j: JSONObject) => parsePlaceMark(j))
//      }).toRight[Throwable](exception("Unable to find Placemark array"))
//
      Right(Nil)
    }
    catch {
      case t: Throwable => Left(t)
    }

  def parsePlaceMark(placemark: JSONObject): Placemark = {
    error("huzzah")
  }

  def exception(msg: String) = new Exception(msg)
}
