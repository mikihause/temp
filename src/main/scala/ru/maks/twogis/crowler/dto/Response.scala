package ru.maks.twogis.crowler.dto

object Response {

  case class ResponsePageData(status: Int,
                              title: Option[String])

  case class ResponseLoadPage(url: String,
                              data: Option[ResponsePageData] = None,
                              errorLoadMessage: Option[String] = None)

  case class ResponseDTO(status: Int,
                         data: Option[List[ResponseLoadPage]] = None,
                         errorMessage: Option[String] = None)

}
