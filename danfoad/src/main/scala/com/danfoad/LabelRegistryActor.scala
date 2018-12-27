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

  var labels = Map("general.name" -> "Dan Foad")

  def receive: Receive = {
    case GetLabels =>
      sender() ! Labels(labels)
  }
}