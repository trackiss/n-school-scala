lazy val mockitoV = "3.9.0"
lazy val parserCombinatorsV = "1.1.2"
lazy val scalaTestV = "3.2.8"

lazy val globalSettings = Seq(
  idePackagePrefix := Some("com.github.trackiss"),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Xlint",
    "-Wdead-code"
  ),
  scalaVersion := "2.13.5"
)

lazy val root = (project in file("."))
  .aggregate(advance, concurrent)
  .settings(
    name := "n-school-scala",
    version := "0.0.1"
  )

lazy val advance = (project in file("advance"))
  .settings(globalSettings: _*)
  .settings(
    libraryDependencies ++= Seq(
      "org.scala-lang" % "scala-reflect" % scalaVersion.value,
      "org.mockito" % "mockito-core" % mockitoV % Test,
      "org.scala-lang.modules" %% "scala-parser-combinators" % parserCombinatorsV,
      "org.scalatest" %% "scalatest" % scalaTestV % Test
    ),
    name := "advance",
    scalacOptions ++= Seq(
      "-encoding",
      "-language:experimental.macros",
      "utf-8"
    ),
    version := "0.0.1",
    wartremoverErrors ++= Warts.allBut(
      Wart.Any,
      Wart.Equals,
      Wart.JavaSerializable,
      Wart.ListAppend,
      Wart.Overloading,
      Wart.PlatformDefault,
      Wart.Product,
      Wart.Recursion,
      Wart.Serializable,
      Wart.StringPlusAny,
      Wart.Throw,
      Wart.ToString,
      Wart.Var
    )
  )

lazy val concurrent = (project in file("concurrent"))
  .settings(globalSettings: _*)
  .settings(
    name := "concurrent",
    version := "0.0.1"
  )
