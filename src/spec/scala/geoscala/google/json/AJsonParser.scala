package geoscala.google.json

import com.googlecode.instinct.expect.Expect._
import com.googlecode.instinct.marker.annotate.Specification
import fjs.data.Either._
import spec.SpecificationHelper._
import types.Placemark

final class AJsonParser {
  @Specification
  def canParseJsonIntoAPlacemark {
    val json = dataFileContents("json/geo.json.js")
    val maybePlacemarks = new JsonParser().placemarks(json)
    expect.that(maybePlacemarks.isRight).isEqualTo(true)
    return
  }
}
