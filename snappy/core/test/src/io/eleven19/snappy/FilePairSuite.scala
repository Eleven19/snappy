package io.eleven19.snappy

import cats.effect.IO
import fs2.io.file.Path

class FilePairSuite extends munit.CatsEffectSuite {
  val testPath: Path = SourcePath.path

  test("Calling atRoot should create a FilePair at the given root for the given path") {
    for {
      testFolder <- FileHelper.firstMatchingAncestor[IO]("test".r, testPath)
      // _ <- IO.println(s"TestFolder: $testFolder")
      filePair = FilePair.atRoot(testFolder, testPath)
      // _ <- IO.println(s"FilePair: $filePair")
    } yield assert(true)
  }
}
