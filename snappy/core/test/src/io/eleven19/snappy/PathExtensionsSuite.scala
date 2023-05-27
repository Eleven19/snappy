package io.eleven19.snappy

import fs2.io.file.Path

class PathExtensionsSuite extends munit.CatsEffectSuite {
  test("Calling fileNameWithoutExtension should work") {
    val sut = Path("index.html")
    assertEquals(sut.fileNameWithoutExtension, "index")
  }

  test("Calling fileNameWithoutExtension should strip single extension if file has two"){
    val sut = Path("Foo.verified.txt")
    assertEquals(sut.fileNameWithoutExtension, "Foo.verified")
  }

  test("Calling dropExtension should strip the extension from the Path") {
    val sut = Path("home/alpha/beta/gamma.json")
    assertEquals(sut.dropExtension, Path("home/alpha/beta/gamma"))
  }

  test("Calling dropExtensions should strip the extension from the Path"){
    val sut = Path("/User/tester/home/myfile.foo.bar.baz.json")
    assertEquals(sut.dropExtensions, Path("/User/tester/home/myfile"))
  }

  test("Calling fileBaseNameAndExtension should return the file's base name and its extension") {
    val sut = Path("not/rooted/Hello.txt")
    assertEquals(sut.fileBaseNameAndExtension, ("Hello", ".txt"))
  }

  test("Calling dropRight should drop the n characters from the right of the path"){
    val sut = Path("/Home/File.12345")
    assertEquals(sut.dropRight(6), Path("/Home/File"))
  }

  test("Calling dropRight with n larger than the Path's String length should return an empty path"){
    val sut = Path("/Home/Foo")
    assertEquals(sut.dropRight(sut.toString.size), Path(""))
  }

  test("Calling splitOnExtension should give you the file path (minus the extension) and the extension") {
    val sut = Path("tests/splitOn/testFile.csv")
    assertEquals(sut.splitOnExtension, (Path("tests/splitOn/testFile"), ".csv"))
  }

  test("Calling splitOnExtension should give you the file path (minus the last extension) and the extension for multipart extensions") {
    val sut = Path("tests/splitOn/testFile.db.bak")
    assertEquals(sut.splitOnExtension, (Path("tests/splitOn/testFile.db"), ".bak"))
  }

  test("Calling splitOnExtensions should properly handle multipart extensions") {
    val sut = Path("home/subdir/baseName.docx.tar.gz")
    assertEquals(sut.splitOnExtensions, (Path("home/subdir/baseName"), ".docx.tar.gz"))
  }

}
