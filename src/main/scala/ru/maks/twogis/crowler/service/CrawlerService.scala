package ru.maks.twogis.crowler.service

import ru.maks.twogis.crowler.Log
import ru.maks.twogis.crowler.dto.Response

import scala.concurrent.Future
import scala.util.control.NonFatal

class CrawlerService(pageLoader: PageLoader, pageParser: PageParser)(implicit executor: scala.concurrent.ExecutionContext) extends Log {

  def loadPages(urls: List[String]): Future[List[Response.ResponseLoadPage]] = {
    urls.map(url => (pageLoader.loadPage(url), url))
      .foldLeft(Future.successful(List[Response.ResponseLoadPage]())) { case (result, (pageDataF, url)) =>
      val page = pageDataF.map{ loadPageData =>
        val pageData = Response.ResponsePageData(status = loadPageData.status, title = loadPageData.body.flatMap(pageParser.parserTitle))
        Response.ResponseLoadPage(url, Some(pageData))
      }.recoverWith {
        case NonFatal(e) =>
          log.debug(s"Error download page: ${e.getMessage}")
          Future.successful(Response.ResponseLoadPage(url, errorLoadMessage = Some(e.getMessage)))
      }
      result.zipWith(page)(_ :+ _)
    }
  }
}
