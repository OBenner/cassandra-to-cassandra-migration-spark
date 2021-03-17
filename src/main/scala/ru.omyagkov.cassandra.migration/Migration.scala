package ru.omyagkov.cassandra.migration


import com.datastax.spark.connector.cql.CassandraConnector
import com.datastax.spark.connector._
import com.datastax.spark.connector.rdd.CassandraTableScanRDD
import ru.omyagkov.cassandra.migration.utils.JobLogger

object Migration {
  def apply(): Migration = new Migration
}

class Migration extends SparkConfig with JobLogger {
  def run(): Unit = {
    log.info(s"Job ${jobConfig.jobName} started!")
    val connectorToClusterOne: CassandraConnector = CassandraConnector(sparkContext
      .getConf
      .set("spark.cassandra.connection.host", jobConfig.cassandraSourceEndpoints)
      .set("spark.cassandra.auth.username", jobConfig.cassandraSourceUser)
      .set("spark.cassandra.auth.password", jobConfig.cassandraSourcePassword)
      .set("spark.cassandra.input.fetch.sizeInRows", jobConfig.cassandraSourceFetchSizeInRow)
      .set("spark.cassandra.input.split.sizeInMB", jobConfig.cassandraSourceSplitSizeInMb)
      .set("spark.cassandra.input.consistency.level", jobConfig.cassandraSourceConsistencyLevel)
    )

    val connectorToClusterTwo: CassandraConnector = CassandraConnector(sparkContext
      .getConf
      .set("spark.cassandra.connection.host", jobConfig.cassandraTargetEndpoints)
      .set("spark.cassandra.auth.username", jobConfig.cassandraTargetUser)
      .set("spark.cassandra.auth.password", jobConfig.cassandraTargetPassword)
      .set("spark.cassandra.output.batch.grouping.buffer.size", jobConfig.cassandraTargetGroupBufferSizeInRow)
      .set("spark.cassandra.output.batch.size.bytes", jobConfig.cassandraTargetBatchSizeInByte)
      .set("spark.cassandra.output.concurrent.writes", jobConfig.cassandraTargetConcurrentWrites)
      .set("spark.cassandra.output.consistency.level", jobConfig.cassandraTargetConsistencyLevel)
    )

    val rddFromClusterOne: CassandraTableScanRDD[CassandraRow] = read(connectorToClusterOne)
    saveToCassandra(rddFromClusterOne, connectorToClusterTwo)
  }

  private def read(connectorToClusterOne: CassandraConnector): CassandraTableScanRDD[CassandraRow] = {
    implicit val source: CassandraConnector = connectorToClusterOne
    sparkContext.cassandraTable(jobConfig.cassandraSourceKeySpace, jobConfig.cassandraSourceTable)
  }

  private def saveToCassandra(rddFromClusterOne: CassandraTableScanRDD[CassandraRow], connectorToClusterTwo: CassandraConnector): Unit = {
    implicit val sink: CassandraConnector = connectorToClusterTwo
    rddFromClusterOne.saveToCassandra(jobConfig.cassandraTargetKeySpace, jobConfig.cassandraTargetTable)
  }
}
