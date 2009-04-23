package geoscala.google.json

import types.Placemark

final class JsonParser {
  def placemarks(json: String): Either[Exception, List[Placemark]] = error("huzzah")
}