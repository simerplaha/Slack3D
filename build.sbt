import xerial.sbt.Sonatype.GitHubHosting

val scalaParallelCollectionsVersion = "1.0.3"
val lwjglVersion = "3.2.3"
val scalaTestVersion = "3.2.9"
val logbackClassicVersion = "1.2.3"
val scalaLoggingVersion = "3.9.3"
val spireVersion = "0.17.0"

val scala213 = "2.13.6"

val scalaOptions =
  Seq(
    "-language:postfixOps",
    "-deprecation",
    "-encoding",
    "UTF-8",
    "-feature",
    "-unchecked",
    "-language:higherKinds",
    "-language:implicitConversions",
    "-Xlint"
  )

lazy val commonSettings =
  Seq(
    organization := "com.github.simerplaha",
    version := "0.1.0",
    scalaVersion := scalaVersion.value,
    scalaVersion in ThisBuild := scala213,
    parallelExecution in ThisBuild := false,
    scalacOptions ++= scalaOptions
  )

val publishSettings = Seq[Setting[_]](
  crossScalaVersions := Seq(scala213),
  sonatypeProfileName := "com.github.simerplaha",
  publishMavenStyle := true,
  licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt")),
  publish := {},
  publishLocal := {},
  sonatypeProjectHosting := Some(GitHubHosting("simerplaha", "Slack3D", "simer.j@gmail.com")),
  developers := List(
    Developer(id = "simerplaha", name = "Simer Plaha", email = "simer.j@gmail.com", url = url("https://github.com/simerplaha/Slack3D"))
  ),
  publishTo := sonatypePublishTo.value
)

lazy val operatingSystem =
  System.getProperty("os.name").toLowerCase match {
    case mac if mac.contains("mac") => "macos"
    case win if win.contains("win") => "windows"
    case linux if linux.contains("linux") => "linux"
    case osName => throw new RuntimeException(s"Unknown operating system $osName")
  }

lazy val testDependencies =
  Seq(
    "org.scala-lang.modules" %% "scala-parallel-collections" % scalaParallelCollectionsVersion % Test,
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test
  )

lazy val lwjglDependencies =
  Seq(
    "lwjgl",
    "lwjgl-opengl",
    "lwjgl-glfw",
    "lwjgl-stb",
    "lwjgl-assimp"
  ).flatMap {
    module => {
      Seq(
        "org.lwjgl" % module % lwjglVersion,
        "org.lwjgl" % module % lwjglVersion classifier s"natives-$operatingSystem"
      )
    }
  }

lazy val commonDependencies =
  Seq(
    "ch.qos.logback" % "logback-classic" % logbackClassicVersion,
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion,
    "org.typelevel" %% "spire" % spireVersion
  ) ++ testDependencies

lazy val Slack3D =
  (project in file("."))
    .settings(name := "Slack3D")
    .settings(commonSettings)
    .settings(publishSettings)
    .dependsOn(graphics)
    .aggregate(graphics)
    .aggregate(`linear-algebra`)

lazy val `linear-algebra` =
  project
    .settings(commonSettings)
    .settings(publishSettings)
    .settings(libraryDependencies ++= commonDependencies)

lazy val graphics =
  project
    .settings(commonSettings)
    .settings(publishSettings)
    .settings(libraryDependencies ++= commonDependencies ++ lwjglDependencies)
    .dependsOn(`linear-algebra`)

lazy val examples =
  project
    .settings(commonSettings)
    .settings(publishSettings)
    .settings(libraryDependencies ++= commonDependencies ++ lwjglDependencies)
    .dependsOn(Slack3D)
