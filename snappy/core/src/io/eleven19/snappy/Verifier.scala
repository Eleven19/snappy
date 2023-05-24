package io.eleven19.snappy

object Verifier {
  def verify[A: VerifierEngine](target: A): A = {
    VerifierEngine[A].verify(target)
  }
}
