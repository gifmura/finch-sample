package com.example.finchsample

import cats.effect.IO
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import io.finch.catsEffect._

case class UserForm(email: String, screen_name: String)
object UserForm {
  val mailRule = ValidationRule[String]("mail should be mailRule")(a => """(\w+)@([\w\.]+)""".r.unapplySeq(a).isDefined)
  val userReqParams:Endpoint[IO, UserForm]= (
      param("email").should(mailRule) ::
      param("screen_name")
    ).as[UserForm]

  val userJson:Endpoint[IO, Seq[UserForm]] = jsonBody[Seq[UserForm]]
}
