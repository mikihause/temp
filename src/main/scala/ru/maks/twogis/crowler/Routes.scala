package ru.maks.twogis.crowler


import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, _}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Flow
import akka.util.Timeout
import org.json4s._
import org.json4s.jackson.Serialization.write
import ru.maks.twogis.crowler.dto.Response
import ru.maks.twogis.crowler.service.CrawlerService
import spray.json._

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class Routes(crawlerService: CrawlerService)(implicit actorSystem: ActorSystem,
                                             materializer: ActorMaterializer,
                                             executionContext: ExecutionContext) extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val formats = DefaultFormats
  implicit val timeout = Timeout(10, TimeUnit.SECONDS)

  val routes: Flow[HttpRequest, HttpResponse, Any] = {
    mainRoute
  }

  private lazy val mainRoute: Route = (path("") & entity(as[List[String]])) { request =>
    post {
        onComplete(crawlerService.loadPages(request)) {
          case Success(data) =>
            complete(HttpEntity(ContentTypes.`application/json`, write(Response.ResponseDTO(200, Some(data)))))
          case Failure(error) =>
            complete(HttpEntity(ContentTypes.`application/json`, write(Response.ResponseDTO(status = 500, errorMessage = Some(error.getMessage)))))
        }
    }

  }


}
