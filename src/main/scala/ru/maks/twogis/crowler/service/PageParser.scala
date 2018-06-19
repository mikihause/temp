package ru.maks.twogis.crowler.service


import java.net.URL

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._


class PageParser {

  private lazy val browser = new JsoupBrowser()

  def parserTitle(html: String): Option[String] = {
    (browser.parseString(html) >?> element("head")).flatMap(_ >?> element("title")).map(_.text)
  }

}
