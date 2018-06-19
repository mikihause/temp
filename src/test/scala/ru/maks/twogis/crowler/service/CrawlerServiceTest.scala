package ru.maks.twogis.crowler.service

import org.scalamock.scalatest.MockFactory
import org.scalatest._
import ru.maks.twogis.crowler.dto.{LoadPageDTO, Response}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

class CrawlerServiceTest extends FlatSpec with Matchers with MockFactory {

  val pageLoader = stub[PageLoader]
  val pageParser = new PageParser()

  it should "crawler testing" in {

    val mailLoadPage = LoadPageDTO(status = 200, body = None, url = "mail")
    val yandexLoadPage = LoadPageDTO(status = 200, body = Some(PagesData.yandexTitle), url = "yandex")
    val googleLoadPage = LoadPageDTO(status = 404, body = None, url = "google")


    (pageLoader.loadPage _) when ("mail") returns (Future.successful(mailLoadPage))
    (pageLoader.loadPage _) when ("yandex") returns (Future.successful(yandexLoadPage))
    (pageLoader.loadPage _) when ("google") returns (Future.successful(googleLoadPage))
    (pageLoader.loadPage _) when ("booble") returns (Future.failed(new Exception("error page")))


    val crawlerService = new CrawlerService(pageLoader = pageLoader, pageParser = pageParser)
    val pages = crawlerService.loadPages(List("mail", "yandex", "google", "booble"))
    val resultPages = Await.result(pages, Duration.Inf)

    resultPages.find(_.url == "mail") should be(Some(Response.ResponseLoadPage("mail", Some(Response.ResponsePageData(200, None)), None)))
    resultPages.find(_.url == "booble") should be(Some(Response.ResponseLoadPage("booble", None, Some("error page"))))
    resultPages.find(_.url == "google") should be(Some(Response.ResponseLoadPage("google", Some(Response.ResponsePageData(404, None)), None)))
    resultPages.find(_.url == "yandex") should be(Some(Response.ResponseLoadPage("yandex", Some(Response.ResponsePageData(200, Some("yandex"))), None)))
  }
}
