package io.eleven19.snappy
import fs2.io.file.Path
object CurrentFile {
  def path(implicit file: sourcecode.File): String = file.value
  def directory(implicit file: sourcecode.File): String = Path(file.value).parent.map(_.toString).orNull
  def getDirectory(implicit file: sourcecode.File): Option[String] = Path(file.value).parent.map(_.toString)
  def relative(relativePath: String)(implicit file: sourcecode.File): String = {
    val directory = getDirectory
    directory.map(Path(_).resolve(relativePath).toString).orNull
  }
}
