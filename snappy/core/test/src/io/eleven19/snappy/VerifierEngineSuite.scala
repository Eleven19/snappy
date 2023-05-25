package io.eleven19.snappy

class VerifierEngineSuite extends munit.FunSuite {
  test("verify") {
    val filePair = FilePair(".txt", "received.txt", "expected.txt")
    Verifier.verify("verification", filePair)
    assert(true)
  }
}
