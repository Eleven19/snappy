package io.eleven19.snappy

trait StringCompare[F[_]] {
  def apply(received: String, verified: String, context: Map[String, Any]): F[CompareResult]
  @inline final def compare(received: String, verified: String, context: Map[String, Any]): F[CompareResult] =
    apply(received, verified, context)
}
object StringCompare {

}
