package io.eleven19.snappy
import fs2.io.file._
import cats.effect.IO
import scala.annotation.tailrec
import scala.util.matching.Regex
import cats.syntax.all._
import cats._
import munit.CatsEffectSuite
class FileHelperSuite extends CatsEffectSuite {
  test("Calling pwd should return the working directory") {
    val actual = FileHelper.pwd[IO]
    actual
      .map(it => it.endsWith("snappy"))
      .assert
  }

  test("Calling firstMatchingAncestor with a test subfolder should work") {
    val myPath = implicitly[sourcecode.File].toPath
    for {
      testDir <- FileHelper.firstMatchingAncestor[IO]("test".r, myPath)
      // _ <- IO.println(s"TestDir: $testDir")
    } yield assert(testDir.endsWith("snappy/core/test"))
  }
}
