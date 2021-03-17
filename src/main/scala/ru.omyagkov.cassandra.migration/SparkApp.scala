package ru.omyagkov.cassandra.migration

object SparkApp {
  def main(args: Array[String]): Unit = {
    Migration().run()
  }
}
