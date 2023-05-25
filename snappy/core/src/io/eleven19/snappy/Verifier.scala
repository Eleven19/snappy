package io.eleven19.snappy

object Verifier {
  def verify[A: VerifierEngine](name:String, target: A)(implicit file:sourcecode.File, line:sourcecode.Line): A = {
    VerifierEngine[A].verify(name, target)
  }
}
