// package io.eleven19.snappy

// import fs2.io.file._
// import scala.annotation.tailrec
// import scala.util.matching.Regex
// import cats.syntax.all._
// import cats._

// trait VerifierEngine[A] { self =>






//   def verify(name: String, target: A)(implicit file: sourcecode.File, line: sourcecode.Line): A = {
//     val sourcePath = Path(file.value)
//     val root = rootDir(sourcePath)

//     val relativized = root.relativize(sourcePath)
//     val resourcesDir = root.resolve("resources")
//     val outputPath = resourcesDir.resolve(relativized)
//     val outputDir = outputPath.parent

//     println(s"Verifying ${file.value}:${line.value}")
//     println(s"Root is $root")
//     println(s"Relativized: $relativized; Resources: $resourcesDir; OutputDir: $outputDir")

//     Files.createDirectories(outputDir)
//     //makeOutputDir(outputDir)

//     val filePair = FilePair.atRoot(outputDir, sourcePath)
//     println(s"FilePair: $filePair")

//     target
//   }
// }

// object VerifierEngine extends VerifierEngineInstancesLowPriority {
//   def apply[A](implicit ev: VerifierEngine[A]): VerifierEngine[A] = ev
// }

// trait VerifierEngineInstancesLowPriority {
//   implicit def defaultVerifierEngine[A]: VerifierEngine[A] = new VerifierEngine[A] {}
// }
