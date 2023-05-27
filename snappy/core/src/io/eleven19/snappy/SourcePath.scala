package io.eleven19.snappy

import fs2.io.file._
object SourcePath {
  def path(implicit file: sourcecode.File): Path = Path(file.value)
}
