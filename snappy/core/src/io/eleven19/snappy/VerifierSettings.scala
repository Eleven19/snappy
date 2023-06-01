package io.eleven19.snappy

import fs2.io.file.Path

case class VerifierSettings(snapshotFileRoot: Path)
object VerifierSettings {

  implicit def default(implicit sourceFile: sourcecode.File): VerifierSettings = {
    val sourceFilePath = sourceFile.toPath
    val root = PathHelper.firstMatchingAncestorWithin("test".r, sourceFilePath, Path("."))
    val snapshotFolder = root.resolve("resources/snapshots")
    VerifierSettings(
      snapshotFileRoot = snapshotFolder
    )
  }

  def get(implicit settings: VerifierSettings): VerifierSettings = settings

  def create(snapshotFileRoot: Path)(implicit sourceFile: sourcecode.File): VerifierSettings = {
    val sourcePath = sourceFile.toPath
    VerifierSettings(snapshotFileRoot)
  }
}
