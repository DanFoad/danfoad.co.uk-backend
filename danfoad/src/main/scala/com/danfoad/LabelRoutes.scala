package com.danfoad

import akka.actor.{ ActorRef, ActorSystem }
import akka.event.Logging

import scala.concurrent.duration._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.directives.MethodDirectives.get
import akka.http.scaladsl.server.directives.RouteDirectives.complete
import akka.http.scaladsl.server.directives.PathDirectives.path

import scala.concurrent.Future
import com.danfoad.LabelRegistryActor._
import akka.pattern.ask
import akka.util.Timeout

trait LabelRoutes extends JsonSupport {

  implicit def system: ActorSystem

  lazy val log = Logging(system, classOf[LabelRoutes])

  def labelRegistryActor: ActorRef

  implicit lazy val timeout = Timeout(5.seconds)

  lazy val labelRoutes: Route =
    pathPrefix("labels") {
      get {
        val labels: Future[Labels] =
          (labelRegistryActor ? GetLabels).mapTo[Labels]
        complete(labels)
      }
    }
}
