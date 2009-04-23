package geoscala.google.json

import com.googlecode.instinct.marker.annotate.Specification
import spec.SpecificationHelper._

final class AJsonParser {
  @Specification
  def canParseJsonIntoAPlacemark {
    val json = dataFileContents("json/geo.json.js")
    val x = new JsonParser().placemarks(json)
    //    expect.that(p.commandLine).isEqualTo("/opt/blast/bin/blastall")
    return
  }
}
