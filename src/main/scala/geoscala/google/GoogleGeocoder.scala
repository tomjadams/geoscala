package geoscala.google

import types.Placemark

// Geocoder, using Google services: http://code.google.com/apis/maps/documentation/geocoding/index.html.
object GoogleGeocoder {
  def geocoder(apiKey: String): GoogleGeocoder = new GoogleGeocoder_(apiKey)
}

sealed trait GoogleGeocoder {
  def reverseGeocode(latitude: Float, longitude: Float): Either[Throwable, List[Placemark]]
}

private final class GoogleGeocoder_(apiKey: String) extends GoogleGeocoder {
  def reverseGeocode(latitude: Float, longitude: Float): Either[Throwable, List[Placemark]] = error("huzzah!")
}
