package io.eleven19.snappy
import java.nio.file.{Files, Path, Paths}
import scala.annotation.tailrec

trait VerifierEngine[A] { self =>
  def pwd:Path = sys.props.get("user.dir").map(Paths.get(_)).getOrElse {
    val path = Paths.get(".").toAbsolutePath
    // When you get the absolute path using "." it leaves "." at the end so strip it
    if(path.getFileName.toString == "." && path.getParent != null)
      path.getParent
    else
      path
  }

  @tailrec
  final def rootDir(path:Path):Path = {
    val pwd = self.pwd
    (path, path.getFileName.toString, path.getParent) match {
      case (_, "test", _) => path
      case (`pwd`, _, _) => path
      case (_,_,null) => path
      case (_,_,parent) => rootDir(parent)
    }

  }

  def makeOutputDir(path:Path): Path = {
    if(Files.exists(path)) {
      path
    }else {
      Files.createDirectories(path)
    }
  }

  def verify(name:String, target: A)(implicit file: sourcecode.File, line: sourcecode.Line): A = {
    val sourcePath = Paths.get(file.value)
    val root = rootDir(sourcePath)

    val relativized = root.relativize(sourcePath)
    val resourcesDir = root.resolve("resources")
    val outputPath = resourcesDir.resolve(relativized)
    val outputDir = outputPath.getParent

    println(s"Verifying ${file.value}:${line.value}")
    println(s"Root is $root")
    println(s"Relativized: $relativized; Resources: $resourcesDir; OutputDir: $outputDir")

    makeOutputDir(outputDir)

    val filePair = FilePair.atRoot(outputDir, sourcePath)
    println(s"FilePair: $filePair")

    target
  }
}

object VerifierEngine extends VerifierEngineInstancesLowPriority {
  def apply[A](implicit ev: VerifierEngine[A]): VerifierEngine[A] = ev
}

trait VerifierEngineInstancesLowPriority {
  implicit def defaultVerifierEngine[A]: VerifierEngine[A] = new VerifierEngine[A] {}
}
