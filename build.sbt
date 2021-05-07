name := "n-school-scala"

version := "0.1"

scalaVersion := "2.13.5"

idePackagePrefix := Some("com.github.trackiss")

val mockitoV = "3.9.0"
val scalaTestV = "3.2.8"

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % scalaVersion.value,
  "org.mockito" % "mockito-core" % mockitoV % Test,
  "org.scalatest" %% "scalatest" % scalaTestV % Test
)

scalacOptions ++= Seq("-encoding", "-language:experimental.macros", "utf-8")

wartremoverErrors ++= Warts.allBut(
  Wart.Any,
  Wart.Equals,
  Wart.JavaSerializable,
  Wart.ListAppend,
  Wart.Overloading,
  Wart.Product,
  Wart.Recursion,
  Wart.Serializable,
  Wart.Throw,
  Wart.Var
)
