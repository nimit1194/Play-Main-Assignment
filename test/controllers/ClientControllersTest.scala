package controllers

import models.{ClientData, ClientRepository}
import org.scalatestplus.play.guice.GuiceOneAppPerSuite
import akka.stream.Materializer
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.i18n.MessagesApi
import play.api.test.FakeRequest
import play.api.test.Helpers._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}

class ClientControllersTest extends PlaySpec with MockitoSugar with GuiceOneAppPerSuite {
  implicit lazy val materializer: Materializer = app.materializer

  val m: MessagesApi = mock[MessagesApi]
  val c: ClientRepository = mock[ClientRepository]
  val f: ClientForm = mock[ClientForm]
  val clientControllerObj = new ClientControllers(m, c, f)

  "Client-Controller" should {
    "be able to create customer account" in {
      val customer1 = CustomerData("Nimit", "Jain", 23, "nimit1194@gmail.com", "password123", "password123", "Knoldus")
      val form = new ClientForm().customerFormConstraints.fill(customer1)
      when(f.customerFormConstraints).thenReturn(form)
      when(c.findByEmail("nimit1194@gmail.com")).thenReturn(Future(None))
      when(c.store(ClientData(0, "Nimit","Jain", 23,"nimit1194@gmail.com", "password123","Knoldus")))
        .thenReturn(Future(true))

      val result = clientControllerObj.registrationValidation.apply(FakeRequest(POST, "/customer_account")
        .withFormUrlEncodedBody(
        "firstName"->"Nimit",
        "lastName"->"Jain",
        "age"->"23",
        "userEmail" -> "nimit1194@gmail.com",
        "password" -> "password123",
        "confirmPassword"->"password123",
        "company"->"Knoldus"))


      redirectLocation(result) mustBe Some("/success")

    }

  }

}

