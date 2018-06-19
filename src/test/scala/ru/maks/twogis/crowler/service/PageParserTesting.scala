package ru.maks.twogis.crowler.service

import org.scalatest._

class PageParserTesting extends FlatSpec with Matchers {

  private lazy val pageParser = new PageParser()

  it should "testing title" in {
    pageParser.parserTitle(PagesData.xitrum) should be(Some("Xitrum"))
    pageParser.parserTitle(PagesData.developersLife) should be(Some("Когда обернул старый говнокод красивой оберткой.  /  Жизнь разработчиков\n        / Смешные gif анимации о жизни разработчиков"))
    pageParser.parserTitle(PagesData.ejb) should be (Some("EJB (Java EE 6 )"))
    pageParser.parserTitle(PagesData.spring) should be (Some("Spring"))
    pageParser.parserTitle(PagesData.withoutTitle) should be (None)
  }
}
