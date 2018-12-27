package com.danfoad

import akka.actor.{ Actor, ActorLogging, Props }

final case class Labels(labels: Map[String, String])

object LabelRegistryActor {
  final case class ActionPerformed(description: String)
  final case object GetLabels

  def props: Props = Props[LabelRegistryActor]
}

class LabelRegistryActor extends Actor with ActorLogging {
  import LabelRegistryActor._

  var labels = Map(
    "general.name" -> "Dan Foad",
    "general.role" -> "Associate Software Developer",
    "home.header.subline" -> "Currently working with NowTV",
    "home.header.contact" -> "Contact Me",
    "app.nav.links.home" -> "Home",
    "app.nav.links.projects" -> "Projects",
    "app.nav.links.cv" -> "CV",
    "app.nav.links.blog" -> "Blog",
    "app.nav.links.contact" -> "Contact Me")

  def receive: Receive = {
    case GetLabels =>
      sender() ! Labels(labels)
  }
}