import mill._, scalalib._, scalajslib._, publish._
//import $ivy.`de.tototec::de.tobiasroeser.mill.vcs.version::0.3.1`
//import de.tobiasroeser.mill.vcs.version.VcsVersion

val scalaVersions = Seq("2.13.10", "3.3.0")
val scalaJSVersions = scalaVersions.map((_, "1.13.1"))
val scalaNativeVersions = scalaVersions.map((_, "0.4.12"))

object Deps {
  val munit = "1.0.0-M7"
  val sourcecode = "0.3.0"
}

trait SnappyPublishModule extends PublishModule with CrossScalaModule {
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

object snappy extends Cross[SnappyModule](scalaVersions)

trait SnappyModule extends Cross.Module[String] {
  trait Shared extends SnappyPublishModule with InnerCrossModule with PlatformScalaModule {
    def ivyDeps = Agg(
      ivy"com.lihaoyi::sourcecode::${Deps.sourcecode}"
    )

    trait SnappyTestingModule extends Tests with TestModule.Munit {
      def ivyDeps = Agg(
        ivy"org.scalameta::munit::${Deps.munit}",
        ivy"org.scalameta::munit-scalacheck::${Deps.munit}"
      )
    }
  }

  trait SharedJS extends Shared with ScalaJSModule {
    def scalaJSVersion = scalaJSVersions.head._2
  }

  object core extends Module {
    object jvm extends Shared {
      object test extends Tests with SnappyTestingModule
    }

    object js extends SharedJS {
      object test extends Tests with SnappyTestingModule
    }
  }

}
