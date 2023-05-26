package io.eleven19
import fs2.io.file._
package object snappy {
  implicit class PathExtensions(val self : Path) extends AnyVal {
    def fileNameWithoutExtension:String = {
      val ext = self.extName
      if(ext.nonEmpty) {
        self.fileName.toString.drop(ext.size)
      } else {
        self.fileName.toString
      }
    }
  }
}
