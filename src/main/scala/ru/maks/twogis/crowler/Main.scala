package ru.maks.twogis.crowler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import ru.maks.twogis.crowler.service.{CrawlerService, PageLoader, PageParser}

object Main extends Log {

  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load("akka.conf")
    implicit val system = ActorSystem("my-system", config)
    implicit val materializer = ActorMaterializer()
    implicit val excecutionContext = system.dispatchers.lookup("main-dispatcher")

    val httpExp = Http()
    val pageLoader = new PageLoader(httpExp, system.dispatchers.lookup("page-loader-dispatcher"))
    lazy val pageParser = new PageParser()

    val crawlerService = new CrawlerService(pageLoader, pageParser)
    val routes = new Routes(crawlerService)


    Http().bindAndHandle(routes.routes, "localhost", 8800)
  }
}
