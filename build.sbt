import sbt.Keys._
import sbtassembly.AssemblyPlugin.autoImport.assemblyJarName

organization := "ru.maks.2gis.test"
name         := "crawler-akka"
version      := "1.0-SNAPSHOT"

scalaVersion := "2.12.6"
scalacOptions ++= Seq("-deprecation", "-feature", "-unchecked")

// Xitrum requires Java 8
javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// https://mvnrepository.com/artifact/org.json4s/json4s-jackson
libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.4"


// Xitrum uses SLF4J, an implementation of SLF4J is needed
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"

libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.13"
)

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % "2.5.13",
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.5.13" % Test
)

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

libraryDependencies += "net.ruippeixotog" %% "scala-scraper" % "2.1.0"

libraryDependencies ++= Seq(
  "org.slf4s" %% "slf4s-api" % "1.7.25",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)

libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.3"

libraryDependencies += "org.codehaus.janino" % "janino" % "3.0.8"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % Test

libraryDependencies += "org.scalamock" %% "scalamock" % "4.1.0" % Test