package geoscala.google.json

import net.sf.json.{JSONArray, JSONObject}, JSONObject._
import scalaz.OptionW, OptionW._
import types.{Country, AddressDetails, GeoLocationSearchAccuracy, Placemark}

final class JsonParser {
  import GeoLocationSearchAccuracy._, NullSafeJSONObject._

  def placemarks(json: String): Either[Throwable, List[Placemark]] =
    try {
      val maybePlacemarks = fromObject(json).jsonArray("Placemark")
      val maybeParsedPlacemarks = maybePlacemarks.map(placemarks => 0.until(placemarks.size).map(placemarks.getJSONObject(_)).map(parsePlaceMark(_)).toList)
      maybeParsedPlacemarks.toRight[Throwable](exception("Unable to find Placemark array"))
    } catch {
      case t: Throwable => Left(t)
    }

  def parsePlaceMark(placemark: JSONObject) = {
    val details = parseAddressDetails(placemark.getJSONObject("AddressDetails"))
    new Placemark(placemark.getString("id"), placemark.getString("address"), details)
  }

  def parseAddressDetails(addressDetails: JSONObject) = {
    val country = parseCountry(addressDetails.getJSONObject("Country"))
    new AddressDetails(country, addressDetails.getInt("Accuracy"))
  }

  def parseCountry(country: JSONObject) = {
    val administrativeArea = None
    new Country(country.getString("CountryNameCode"), country.getString("CountryName"), administrativeArea)
  }

  def parseAccuracy(accuracy: Int) = {}

  def exception(msg: String) = new Exception(msg)
}

private object NullSafeJSONObject {
  implicit def JSONObjectToNullSafeJSONObject(source: JSONObject): NullSafeJSONObject = new NullSafeJSONObject(source)
}

private final class NullSafeJSONObject(source: JSONObject) {
  def jsonArray(key: String): Option[JSONArray] = onull(source.optJSONArray(key))

  def string(key: String): Option[String] = onull(source.optString(key))
}
