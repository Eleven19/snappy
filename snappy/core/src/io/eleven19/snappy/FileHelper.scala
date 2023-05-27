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

  def firstMatchingAncestor[F[_]: Files](
      pattern: Regex,
      path: Path
  )(implicit F: Monad[F]): F[Path] = pwd[F].map { root =>
    PathHelper.firstMatchingAncestorWithin(pattern, path, root)
  }

}
