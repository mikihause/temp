package ru.maks.twogis.crowler.service

import akka.actor.ActorSystem
import akka.http.scaladsl.{Http, HttpExt}
import akka.http.scaladsl.model.{HttpRequest, StatusCode}
import akka.stream.Materializer
import akka.util.ByteString
import ru.maks.twogis.crowler.Log
import ru.maks.twogis.crowler.dto.LoadPageDTO

import scala.concurrent.{ExecutionContext, Future}
import scala.util.control.NonFatal

class PageLoader(httpService: HttpExt, exceutionContext: ExecutionContext)(implicit system: ActorSystem, materializer: Materializer) extends Log {

  implicit val ec = exceutionContext

  def loadPage(url: String): Future[LoadPageDTO] = {
    httpService.singleRequest(HttpRequest(uri=url)).flatMap { response =>
      if (response.status.isSuccess()) {
        val body  = response.entity.dataBytes.runFold(ByteString(""))(_ ++ _)
        body.map(body => LoadPageDTO(response.status.intValue(), Some(body.utf8String), url = url))
      } else {
        Future.successful(LoadPageDTO(response.status.intValue(), None, url = url))
      }
    }
  }

}
