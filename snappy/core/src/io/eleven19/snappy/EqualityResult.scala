package io.eleven19.snappy

case class EqualityResult(
    equality: Equality,
    message: Option[String],
    receivedText: Option[StringBuilder],
    verifiedText: Option[StringBuilder]
)
