logLevel := Level.Warn

val ideSettingsV = "1.1.0"
val scoverageV = "1.7.3"
val wartremoverV = "2.4.13"

addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % ideSettingsV)
addSbtPlugin("org.scoverage" % "sbt-scoverage" % scoverageV)
addSbtPlugin("org.wartremover" % "sbt-wartremover" % wartremoverV)
