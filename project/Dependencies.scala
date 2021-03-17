import sbt._

object Version {
  object Scala {
    val v212                   = "2.12.11"
    val supportedScalaVersions = List( v212)
  }

  val spark      = "3.0.0"
  val scalaTest  = "3.1.1"
  val pureConfig = "0.12.3"
  val cassandraConnector ="3.0.0"
  val joda = "2.10.9"
  val jnt ="3.1.4"
}

object Dependencies {

  object Spark {
    val core      = "org.apache.spark" %% "spark-core"           % Version.spark % Provided
    val sql       = "org.apache.spark" %% "spark-sql"            % Version.spark % Provided
  }


  object Cassandra {
    val connector =  "com.datastax.spark" %% "spark-cassandra-connector" % Version.cassandraConnector
  }

  object Common {
    val pureConfig = "com.github.pureconfig" %% "pureconfig" % Version.pureConfig
    val jntPosix= "com.github.jnr" % "jnr-posix" %  Version.jnt
    val jodaTime= "joda-time" % "joda-time" % Version.joda
  }

  object Testing {
    val scalaTest = "org.scalatest" %% "scalatest" % Version.scalaTest % Test
  }


  val commonSparkDependencies: Seq[ModuleID] = Seq(
    Spark.core,
    Spark.sql,
    Cassandra.connector,
    Common.pureConfig,
    Common.jodaTime,
    Common.jntPosix,
    Testing.scalaTest
  )
}
