package ru.omyagkov.cassandra.migration

import pureconfig._
import pureconfig.generic.auto.exportReader
import scala.util.Right

object ConfigJob {
  val empty: ConfigJob = new ConfigJob()

  def apply(): ConfigJob = {
    val config = ConfigSource.default.load[ConfigJob] match {
      case Right(b) => b
      case _ =>
        throw new IllegalArgumentException(
          "Invalid configuration found."
        )
    }
    config
  }
}

case class ConfigJob(
                      jobName: String = "",
                      master: String = "",
                      sparkLogLevel: String = "",
                      sparkUiPort: String = "",
                      cassandraSourceEndpoints: String = "",
                      cassandraSourceUser: String = "",
                      cassandraSourcePassword: String = "",
                      cassandraSourceKeySpace: String = "",
                      cassandraSourceTable: String = "",
                      cassandraTargetEndpoints: String = "",
                      cassandraTargetUser: String = "",
                      cassandraTargetPassword: String = "",
                      cassandraTargetKeySpace: String = "",
                      cassandraTargetTable: String = "",
                      cassandraSourceFetchSizeInRow: String = "",
                      cassandraSourceSplitSizeInMb: String = "",
                      cassandraSourceConsistencyLevel: String = "",
                      cassandraTargetGroupBufferSizeInRow:String = "",
                      cassandraTargetBatchSizeInByte:String = "",
                      cassandraTargetConcurrentWrites:String = "",
                      cassandraTargetConsistencyLevel: String = "",
                    )

