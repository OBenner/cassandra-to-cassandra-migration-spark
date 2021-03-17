package ru.omyagkov.cassandra.migration

import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}

trait SparkConfig {
  protected val jobConfig: ConfigJob = ConfigJob()

  private val sparkConf =
    setMasterIfExist(new SparkConf(), jobConfig.master)
      .setAppName(jobConfig.jobName)
      .set("spark.ui.port", jobConfig.sparkUiPort)


  protected val sparkContext = new SparkContext(sparkConf)
  sparkContext.setLogLevel(jobConfig.sparkLogLevel)
  protected val spark: SparkSession = SparkSession.builder.config(sparkContext.getConf)
    .getOrCreate()


  private def setMasterIfExist(srcConf: SparkConf, master: String): SparkConf = {
    if (master.isEmpty) srcConf else srcConf.setMaster(master)
  }


}
