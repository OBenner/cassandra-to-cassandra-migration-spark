import sbt._
lazy val sparkApp =
  Project(id = "cassandra-to-cassandra-migration-spark", base = file("."))
    .settings(
      ProjectSettings.generalSettings,
      ProjectSettings.assemblySettings,
      ProjectSettings.runLocalSettings,
      assemblyJarName in assembly := s"${name.value}.jar"
    )
