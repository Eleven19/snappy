import mill._, scalalib._, scalajslib._, scalanativelib._, scalafmt._, publish._
//import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.3.1`
//import de.tobiasroeser.mill.vcs.version.VcsVersion

val scalaVersions = Seq("2.13.10", "3.3.0")
val scalaJSVersions = scalaVersions.map((_, "1.13.1"))
val scalaNativeVersions = scalaVersions.map((_, "0.4.12"))

object Deps {
  val munit = "1.0.0-M7"
  val sourcecode = "0.3.0"
}

trait SnappyPublishModule extends PublishModule with CrossScalaModule with ScalafmtModule {
  def publishVersion: T[String] = T { "0.0.1-M01" }

  def pomSettings = PomSettings(
    description = "Snapshot testing in Scala",
    organization = "Eleven19",
    url = "https://github.com/Eleven19/snappy",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("Eleven19", "snappy"),
    developers = Seq(
      Developer("DamianReeves", "Damian Reeves", "https://github.com/DamianReeves")
    )
  )
}

trait SnappyPlatformScalaModule extends PlatformScalaModule {
  def allPlatforms : T[Seq[String]] = T { Seq("jvm", "js", "native")}

  def otherPlatforms : T[Seq[String]] = T {
    allPlatforms().filterNot(_.equalsIgnoreCase(platformScalaSuffix))
  }

  def platformPairs:T[Seq[(String,String)]] = T {
    otherPlatforms().flatMap { platform =>
      if(platform.equalsIgnoreCase(platformScalaSuffix)) {
        Seq.empty
      } else if(platform < platformScalaSuffix) {
        Seq((platform,platformScalaSuffix))
      } else {
        Seq((platformScalaSuffix, platform))
      }
    }
  }

  def platformPairSuffixes:T[Seq[String]] = T {
    platformPairs().map { case (p1, p2) => s"${p1}-${p2}"}
  }
}

object snappy extends Cross[SnappyModule](scalaVersions)

trait SnappyModule extends Cross.Module[String] {
  trait Shared extends SnappyPublishModule with InnerCrossModule with SnappyPlatformScalaModule {

    // Add additional source folders
    override def sources: T[Seq[PathRef]] = T.sources {
      super.sources().flatMap { source =>
        if(source.path.last.endsWith(platformScalaSuffix)) {
          Seq(source)
        } else {
          val crossPlatformSources = platformPairSuffixes().map { suffix =>
            PathRef(source.path / _root_.os.up / s"${source.path.last}-${suffix}")
          }
          Seq(source) ++ crossPlatformSources
        }
      }
    }

    override def ivyDeps = Agg(
      ivy"com.lihaoyi::sourcecode::${Deps.sourcecode}"
    )

    trait SnappyTestingModule extends Tests with TestModule.Munit {
      override def ivyDeps = Agg(
        ivy"org.scalameta::munit::${Deps.munit}",
        ivy"org.scalameta::munit-scalacheck::${Deps.munit}"
      )
    }
  }

  trait SharedJS extends Shared with ScalaJSModule {
    def scalaJSVersion = scalaJSVersions.head._2
  }

  trait SharedNative extends Shared with ScalaNativeModule {
    def scalaNativeVersion = scalaNativeVersions.head._2
  }

  object core extends Module {
    object jvm extends Shared {
      object test extends Tests with SnappyTestingModule
    }

    object js extends SharedJS {
      object test extends Tests with SnappyTestingModule
    }

    object native extends SharedNative {
      object test extends Tests with SnappyTestingModule
    }
  }

}

import mill.eval.{Evaluator, EvaluatorPaths}
// With this we can now just do ./mill reformatAll __.sources
// instead of ./mill -w mill.scalalib.scalafmt.ScalafmtModule/reformatAll __.sources
def reformatAll(evaluator: Evaluator, sources: mill.main.Tasks[Seq[PathRef]]) = T.command {
  ScalafmtModule.reformatAll(sources)()
}