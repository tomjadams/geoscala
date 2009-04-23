package geoscala.google.types

final case class PostalCode(postalCodeNumber: String)
final case class Thoroughfare(name: String)
final case class Locality(name: String, thoroughfare: Thoroughfare, postalCode: PostalCode)
final case class AdministrativeArea(name: String, locality: Locality)
final case class Country(nameCode: String, name: String, administrativeArea: AdministrativeArea)
final case class AddressDetails(country: Country, accuracy: GeoLocationSearchAccuracy)
final case class Placemark(identifier: String, address: String, details: AddressDetails)

object GeoLocationSearchAccuracy {
  def unknown: GeoLocationSearchAccuracy = SearchAccuracyUnknown
  def country: GeoLocationSearchAccuracy = SearchAccuracyCountry
  def region: GeoLocationSearchAccuracy = SearchAccuracyRegion
  def subregion: GeoLocationSearchAccuracy = SearchAccuracySubRegion
  def town: GeoLocationSearchAccuracy = SearchAccuracyTown
  def postcode: GeoLocationSearchAccuracy = SearchAccuracyPostcode
  def street: GeoLocationSearchAccuracy = SearchAccuracyStreet
  def intersection: GeoLocationSearchAccuracy = SearchAccuracyIntersection
  def address: GeoLocationSearchAccuracy = SearchAccuracyAddress
  def premise: GeoLocationSearchAccuracy = SearchAccuracyPremise
}

sealed trait GeoLocationSearchAccuracy {
  def accuracy: Int
}
case object SearchAccuracyUnknown extends GeoLocationSearchAccuracy { def accuracy = 0 }
case object SearchAccuracyCountry extends GeoLocationSearchAccuracy { def accuracy = 1 }
case object SearchAccuracyRegion extends GeoLocationSearchAccuracy { def accuracy = 2 }
case object SearchAccuracySubRegion extends GeoLocationSearchAccuracy { def accuracy = 3 }
case object SearchAccuracyTown extends GeoLocationSearchAccuracy { def accuracy = 4 }
case object SearchAccuracyPostcode extends GeoLocationSearchAccuracy { def accuracy = 5 }
case object SearchAccuracyStreet extends GeoLocationSearchAccuracy { def accuracy = 6 }
case object SearchAccuracyIntersection extends GeoLocationSearchAccuracy { def accuracy = 7 }
case object SearchAccuracyAddress extends GeoLocationSearchAccuracy { def accuracy = 8 }
case object SearchAccuracyPremise extends GeoLocationSearchAccuracy { def accuracy = 9 }
