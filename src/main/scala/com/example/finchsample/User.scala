package com.example.finchsample

import argonaut.Argonaut._
import argonaut.CodecJson
import com.twitter.finagle.mysql._
import com.twitter.util.Future

case class User(id: Long, email: String, screen_name: String)

object User {
  implicit val userCodec: CodecJson[User] =
    casecodec3(User.apply, User.unapply)("id", "email", "screen_name")

  def all()(implicit client: Client): Future[Seq[User]] =
    client.select("SELECT * FROM users")(convertToEntity)

  def find(id: Long)(implicit client: Client): Future[Option[User]] =
    client.prepare("SELECT * FROM users WHERE id = ?")(id) map { result =>
      result.asInstanceOf[ResultSet].rows.map(convertToEntity).headOption
    }

  def create(email: String, screen_name: String)(implicit client: Client): Future[Long] =
    client.prepare("INSERT INTO users (email, screen_name) VALUES(?, ?)")(email, screen_name) map { result =>
      result.asInstanceOf[OK].insertId
    }

  def convertToEntity(row: Row): User = {
    val LongValue(id)            = row("id").get
    val StringValue(email)       = row("email").get
    val StringValue(screen_name) = row("screen_name").get

    User(id, email, screen_name)
  }
}
