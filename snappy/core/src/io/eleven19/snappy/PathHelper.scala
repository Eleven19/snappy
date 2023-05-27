package io.eleven19.snappy

import fs2.io.file.Path

import scala.annotation.tailrec
import scala.util.matching.Regex

object PathHelper {
  def dropExtension(path:Path):Path = {
    val res = path.normalize
    val ext = res.extName
    if (ext.nonEmpty) {
      Path(res.toString.dropRight(ext.size))
    } else {
      res
    }
  }

  def dropRight(path:Path, n:Int):Path =
    Path(path.toString.dropRight(n))

  def dropExtensions(path:Path): Path = {
    @tailrec
    def loop(path: Path): Path = {
      val res = dropExtension(path)
      if (res == path) res
      else
        loop(res)
    }
    loop(path)
  }

  def firstMatchingAncestorWithin(pattern: Regex, path: Path, root:Path): Path = {
    val stableRoot = root
    @tailrec
    def rootDir(path: Path): Path = {
      (path, path.fileName.toString, path.parent) match {
        case (_, pathStr, _) if pattern.matches(pathStr) => path
        case (`stableRoot`, _, _) => path
        case (_, _, None) => root
        case (_, _, Some(parent)) => rootDir(parent)
      }
    }
    rootDir(path)
  }

  def fileBaseNameAndExtension(path:Path):(String,String) = {
    val ext = path.extName
    val fileNameWithoutExtension  =
      if(ext.nonEmpty) path.fileName.toString.dropRight(ext.size)
      else path.fileName.toString
    (fileNameWithoutExtension, ext)
  }

  def fileNameWithoutExtension(path:Path): String = {
    val ext = path.extName
    if (ext.nonEmpty) {
      path.fileName.toString.dropRight(ext.size)
    } else {
      path.fileName.toString
    }
  }

  def splitOnExtension(path:Path):(Path,String) = {
    val ext = path.extName
    if(ext.nonEmpty){
      (Path(path.toString.dropRight(ext.size)), ext)
    } else {
      (path,ext)
    }
  }

  def splitOnExtensions(path:Path):(Path, String) = {
    @tailrec
    def loop(path:Path, extension:String):(Path,String) = {
      val ext = path.extName
      if(ext.isEmpty) {
        (path, extension)
      } else {
        loop(dropRight(path, ext.size), ext + extension)
      }
    }
    loop(path, "")
  }
}
