package io.eleven19.snappy

sealed trait Equality
object Equality {
  case object Equal extends Equality
  case object NotEqual extends Equality
  case object New extends Equality
}
