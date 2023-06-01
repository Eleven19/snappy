package io.eleven19.snappy

case class CompareResult (isEqual: Boolean, message: Option[String])

object CompareResult {
  def equal(isEqual: Boolean): CompareResult = {
    CompareResult(isEqual, None)
  }

  def notEqual(message: String): CompareResult = {
    CompareResult(false, Some(message))
  }

  def notEqual(): CompareResult = {
    CompareResult(false, None)
  }
}