package io.eleven19.snappy

import fs2.io.file._
import scala.annotation.tailrec
import scala.util.matching.Regex
import cats.syntax.all._
import cats._

object FileHelper {
  def makeOutputDir[F[_]: Files](path: Path)(implicit F: Monad[F]): F[Path] = {
    Files[F].exists(path).flatMap { exists =>
      if (exists) {
        F.pure(path)
      } else {
        Files[F].createDirectories(path) *> F.pure(path)
      }
    }
  }

  def pwd[F[_]: Files]: F[Path] = Files[F].currentWorkingDirectory

  def rootDirMatching[F[_]: Files](
      pattern: Regex,
      path: Path
  )(implicit F: Monad[F]): F[Path] = pwd[F].flatMap { pwd =>
    @tailrec
    def rootDir(path: Path): F[Path] = {
      (path, path.fileName.toString, path.parent) match {
        case (_, pathStr, _) if pattern.matches(pathStr) => F.pure(path)
        case (`pwd`, _, _) => F.pure(path)
        case (_, _, None) => F.pure(pwd)
        case (_, _, Some(parent)) => rootDir(parent)
      }
    }

    rootDir(path)
  }

}