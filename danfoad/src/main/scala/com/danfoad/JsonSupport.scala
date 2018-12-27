package com.danfoad

import com.danfoad.LabelRegistryActor.ActionPerformed

//#json-support
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

trait JsonSupport extends SprayJsonSupport {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val labelsJsonFormat = jsonFormat1(Labels)

  implicit val actionPerformedJsonFormat = jsonFormat1(com.danfoad.LabelRegistryActor.ActionPerformed)
}
//#json-support
