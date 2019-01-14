package com.example.finchsample

import io.finch._
import org.scalatest.FunSuite

class MainTest extends FunSuite {
  test("healthcheck") {
    assert(Main_bak.healthcheck(Input.get("/")).awaitValueUnsafe() == Some("OK"))
  }

  test("helloWorld") {
    assert(
      Main_bak.helloWorld(Input.get("/hello")).awaitValueUnsafe() == Some(
        Main_bak.Message("World")))
  }

  test("hello") {
    assert(Main_bak.hello(Input.get("/hello/foo")).awaitValueUnsafe() == Some(Main_bak.Message("foo")))
  }
}