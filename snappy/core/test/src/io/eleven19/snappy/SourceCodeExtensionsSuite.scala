package io.eleven19.snappy

import fs2.io.file.Path

class SourceCodeExtensionsSuite extends munit.CatsEffectSuite {
  test("Calling fileName on sourcecode.File should work") {
    val file = implicitly[sourcecode.File]
    assertEquals(file.fileName, Path("SourceCodeExtensionsSuite.scala"))
  }

  test("Calling fileNameWithoutExtension on sourcecode.File should work") {
    val file = implicitly[sourcecode.File]
    assertEquals(file.fileNameWithoutExtension, "SourceCodeExtensionsSuite")
  }

  test ("Calling extName on a sourcecode.File should work "){
    val file = implicitly[sourcecode.File]
    assertEquals(file.extName, ".scala")
  }

  test("Calling toPath on sourcecode.File should work") {
    val file = implicitly[sourcecode.File]
    val expected = Path("snappy/core/test/src/io/eleven19/snappy/SourceCodeExtensionsSuite.scala")
    assertEquals(file.toPath, expected.absolute)
  }
}

