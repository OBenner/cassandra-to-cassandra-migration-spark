import sbt.Keys._
import sbt._
import sbtassembly.AssemblyKeys._
import sbtassembly.AssemblyPlugin.autoImport.{assemblyJarName, assemblyOption}
import sbtassembly.{MergeStrategy, PathList}

object ProjectSettings {
  val generalSettings = Seq(
    organization := "ru.omyagkov",
    version := "0.1",
    crossScalaVersions := Version.Scala.supportedScalaVersions,
    scalacOptions ++= CompileOptions.compileOptions(scalaVersion.value),
    libraryDependencies ++= Dependencies.commonSparkDependencies
  )

  val assemblySettings = Seq(

    assembly / assemblyOption := (assemblyOption in assembly).value.copy(includeScala = false),
    assembly / assemblyMergeStrategy := {
      case "module-info.class" => MergeStrategy.discard
      case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
      case x => (assemblyMergeStrategy in assembly).value(x)
    }
  )

  // Include "provided" dependencies back to default run task
  val runLocalSettings = Seq(
    Compile / run := Defaults
      .runTask(
        fullClasspath in Compile,
        mainClass in(Compile, run),
        runner in(Compile, run)
      )
      .evaluated
  )
}
