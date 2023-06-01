package io.eleven19.snappy

import fs2.io.file._

final case class FilePair(extension: String, receivedPath: String, verifiedPath: String) {}
object FilePair {
  def atRoot(rootPath: Path, path: Path): FilePair = {
    val extension = path.extName
    val fileNameWithoutExtension = path.fileNameWithoutExtension
    path.parent.map { parent =>
      val received = rootPath.resolve(parent.resolve(s"$fileNameWithoutExtension.received.txt"))
      val expected = rootPath.resolve(parent.resolve(s"$fileNameWithoutExtension.verified.txt"))
      FilePair(extension, received.toString, expected.toString)
    } getOrElse {
      val received = rootPath.resolve(s"$fileNameWithoutExtension.received.txt")
      val expected = rootPath.resolve(s"$fileNameWithoutExtension.verified.txt")
      FilePair(extension, received.toString, expected.toString)
    }
  }
}
