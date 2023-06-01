package io.eleven19
import fs2.io.file._
package object snappy {

  implicit class SourceCodeFileExtensions(val self: sourcecode.File) extends AnyVal {

    def extName: String = Path(self.value).extName
    def fileName: Path = Path(self.value).fileName

    def fileNameWithoutExtension: String = PathHelper.fileNameWithoutExtension(Path(self.value))

    def toPath: Path = Path(self.value)
  }

  implicit class PathExtensions(val self: Path) extends AnyVal {

    @inline def dropExtension: Path = PathHelper.dropExtension(self)
    @inline def dropExtensions: Path = PathHelper.dropExtensions(self)

    @inline def dropRight(n: Int): Path = PathHelper.dropRight(self, n)
    @inline def fileBaseNameAndExtension: (String, String) = PathHelper.fileBaseNameAndExtension(self)
    @inline def fileNameWithoutExtension: String = PathHelper.fileNameWithoutExtension(self)

    @inline def splitOnExtension: (Path, String) = PathHelper.splitOnExtension(self)
    @inline def splitOnExtensions: (Path, String) = PathHelper.splitOnExtensions(self)
  }
}
