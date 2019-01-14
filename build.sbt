val finchVersion = "0.26.0"
val circeVersion = "0.10.1"
val scalatestVersion = "3.0.5"
val finagleVersion = "19.1.0"

lazy val root = (project in file("."))
  .settings(
    organization := "com.example",
    name := "finch-sample",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    libraryDependencies ++= Seq(
      "com.github.finagle" %% "finchx-core"  % finchVersion,
      "com.github.finagle" %% "finchx-circe"  % finchVersion,
      "com.github.finagle" %% "finch-argonaut" % finchVersion,
      "com.twitter" %% "finagle-http" % finagleVersion,
      "com.twitter" %% "finagle-mysql" % finagleVersion,
      "io.circe" %% "circe-generic" % circeVersion,
      "io.circe" %% "circe-core" % circeVersion,
      "org.scalatest"      %% "scalatest"    % scalatestVersion % "test"
    )
  )
