lazy val mockitoV = "3.9.0"
lazy val parserCombinatorsV = "1.1.2"
lazy val scalaTestV = "3.2.8"

lazy val globalSettings = Seq(
  idePackagePrefix := Some("com.github.trackiss"),
  libraryDependencies ++= Seq(
    "org.scala-lang" % "scala-reflect" % scalaVersion.value,
    "org.mockito" % "mockito-core" % mockitoV % Test,
    "org.scala-lang.modules" %% "scala-parser-combinators" % parserCombinatorsV,
    "org.scalatest" %% "scalatest" % scalaTestV % Test
  ),
  scalacOptions ++= Seq("-encoding", "-language:experimental.macros", "utf-8"),
  scalaVersion := "2.13.5",
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

lazy val root = (project in file("."))
  .settings(globalSettings: _*)
  .settings(
    name := "n-school-scala",
    version := "0.0.1"
  )
