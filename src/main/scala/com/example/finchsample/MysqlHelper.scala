package com.example.finchsample
import com.twitter.finagle.Mysql

object MysqlHelper {
  implicit val client = Mysql.client
    .withCredentials("sampleuser", "changeme")
    .withDatabase("finchdb")
    .newRichClient("127.0.0.1:3306")
}
