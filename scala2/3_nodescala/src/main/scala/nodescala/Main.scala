package nodescala

import scala.language.postfixOps
import scala.concurrent._
import scala.concurrent.duration._
import ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

object Main {

  def main(args: Array[String]) {
    // 1. instantiate the server at 8191, relative path "/test",
    //    and have the response return headers of the request
    val myServer = new NodeScala.Default(8191)
    val myServerSubscription = myServer.start("/test") { req =>
      req.toList.map(_._2).flatten.iterator
    }

    // 2. create a future that expects some user input `x`
    //    and continues with a `"You entered... " + x` message
    val userInterrupted: Future[String] = Future.userInput("Enter message:").map("You entered... " + _)

    // 3. create a future that completes after 20 seconds
    //    and continues with a `"Server timeout!"` message
    val timeOut: Future[String] = Future.delay(20 seconds, "Server timeout!")

    // 4. create a future that completes when either 10 seconds elapse
    //    or the user enters some text and presses ENTER
    val terminationRequested: Future[String] = Future.any(List(timeOut, userInterrupted))

    // 5. unsubscribe from the server
    terminationRequested onSuccess {
      case msg => {
        myServerSubscription.unsubscribe
        println(msg)
        println("Bye!")
      }
    }
    Await.ready(terminationRequested, 1 minute)
  }

}