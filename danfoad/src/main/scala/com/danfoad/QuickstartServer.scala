package com.danfoad

//#quick-start-server
import scala.concurrent.{ Await, ExecutionContext, Future }
import scala.concurrent.duration.Duration
import scala.util.{ Failure, Success }

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

//#main-class
object QuickstartServer extends App with LabelRoutes {

  // set up ActorSystem and other dependencies here
  //#main-class
  //#server-bootstrapping
  implicit val system: ActorSystem = ActorSystem("danfoad")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContext = system.dispatcher
  //#server-bootstrapping

  val labelRegistryActor: ActorRef = system.actorOf(LabelRegistryActor.props, "labelRegistryActor")

  //#main-class
  lazy val routes: Route = labelRoutes
  //#main-class

  //#http-server
  val serverBinding: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "localhost", 5000)

  serverBinding.onComplete {
    case Success(bound) =>
      println(s"Server online at http://${bound.localAddress.getHostString}:${bound.localAddress.getPort}/")
    case Failure(e) =>
      Console.err.println(s"Server could not start!")
      e.printStackTrace()
      system.terminate()
  }

  Await.result(system.whenTerminated, Duration.Inf)
  //#http-server
  //#main-class
}
//#main-class
//#quick-start-server
