package io.eleven19.snappy

import java.io.InputStream

sealed trait Target {
  def extension: String
  def name: Option[String]
}

object Target {

  def fromString(extension: String, data: String): Target = FromStringData(extension, new StringBuilder(data), None)
  def fromString(extension: String, data: String, name: String): Target =
    FromStringData(extension, new StringBuilder(data), Option(name))

  final case class FromStringData(extension: String, data: StringBuilder, name: Option[String]) extends Target
  final case class FromStreamData(extension: String, data: InputStream, name: Option[String]) extends Target
}
