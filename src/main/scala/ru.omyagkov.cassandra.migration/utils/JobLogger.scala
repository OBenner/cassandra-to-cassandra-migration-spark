package ru.omyagkov.cassandra.migration.utils

import org.slf4j.{Logger, LoggerFactory}

trait JobLogger {
  protected val log: Logger = LoggerFactory.getLogger(this.getClass)
}
