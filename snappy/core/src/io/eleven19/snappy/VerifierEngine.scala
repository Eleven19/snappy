package io.eleven19.snappy

trait VerifierEngine[A] {
  def verify(target: A)(implicit file: sourcecode.File, line: sourcecode.Line): A = {
    println(s"Verifying ${file.value}:${line.value}")
    target
  }
}

object VerifierEngine extends VerifierEngineInstancesLowPriority {
  def apply[A](implicit ev: VerifierEngine[A]): VerifierEngine[A] = ev
}

trait VerifierEngineInstancesLowPriority {
  implicit def defaultVerifierEngine[A]: VerifierEngine[A] = new VerifierEngine[A] {}
}
