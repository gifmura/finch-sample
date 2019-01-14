package com.example.finchsample

import cats.effect.IO
import com.twitter.finagle.http.{Request, Response}
import com.twitter.finagle.{Http, Service}
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._
import io.finch.catsEffect._

import com.example.finchsample.UserForm._
import com.example.finchsample.MysqlHelper._

object Main {

  def healthcheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  val listUser: Endpoint[IO, Seq[User]] = get("users") {
    User.all.map{
      case Seq() => NotFound(new Exception("Record Not Found"))
      case users => Ok(users)
    }
  }

  val showUser: Endpoint[IO, User] = get("users" :: path[Long]) {id: Long =>
    User.find(id).map {
      case None => NotFound(new Exception("Record Not Found"))
      case Some(u) => Ok(u)
    }
  }

  val createUser: Endpoint[IO, User] = post("users" :: userReqParams) { p: UserForm =>
    (for {
      id   <- User.create(p.email, p.screen_name)
      user <- User.find(id)
    } yield user) map {
      case Some(u) => Created(u)
      case _ => NotFound(new Exception("Record Not Found"))
    }
  }

  val createUserByJson: Endpoint[IO, Int] = post("usersjson" :: userJson) { p: Seq[UserForm] =>
    val insertedId = p.map(a => User.create(a.email, a.screen_name))
    Ok(insertedId.size)
  }

  val userService: Service[Request, Response] = (listUser :+: showUser :+: createUser)
    .handle{
      case fe: io.finch.Error => BadRequest(new Exception(fe.getMessage))
      case e: Exception => InternalServerError(new Exception(e.getMessage))
    }
    .toServiceAs[Application.Json]

  def main(args: Array[String]): Unit = {

    Await.ready(Http.serve(":8081", userService))
  }
}