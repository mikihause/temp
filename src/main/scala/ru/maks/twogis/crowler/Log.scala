package ru.maks.twogis.crowler
import org.slf4s.LoggerFactory

trait Log {

  lazy val log = LoggerFactory.getLogger(getClass)

}
