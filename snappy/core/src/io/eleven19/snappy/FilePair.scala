package io.eleven19.snappy
import java.nio.file.{Files, Path}
final case class FilePair(extension: String, receivedPath: String, expectedPath: String)
object FilePair {
  def atRoot(rootPath:Path, path:Path):FilePair = {
    val fileName = path.getFileName.toString
    val extensionStart = fileName.lastIndexOf('.')
    val (fileNameWithoutExtension, extension) =
      if(extensionStart >= 0) {
        fileName.substring(0, extensionStart) -> fileName.substring(extensionStart + 1, fileName.size)
      } else {
        fileName -> ""
      }

    val received = rootPath.resolve(path.getParent.resolve(s"$fileNameWithoutExtension.received.txt"))
    val expected = rootPath.resolve(path.getParent.resolve(s"$fileNameWithoutExtension.expected.txt"))
    FilePair(extension, received.toString, expected.toString)
  }
}