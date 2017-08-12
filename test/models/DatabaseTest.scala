package models

import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec



class DatabaseTest extends PlaySpec with MockitoSugar  {

  "Client Repository" should {

    val clientRepository = mock[ClientRepository]
    "Store User" in {
      val client1 = ClientData(1, "nim", "jain", 22, "xyz@gmail.com", "hello", "knoldus")
      val result = clientRepository.store(client1)
      result === 1
    }
  }
}

