package models

import javax.inject.Inject
import play.api.db.slick._
import slick.driver.JdbcProfile
import slick.lifted.ProvenShape
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ClientData (id: Int, firstName: String,
                       lastName: String, age: Int,
                       userEmail: String, password: String, company: String)

class ClientRepository @Inject()
(protected val dbConfigProvider: DatabaseConfigProvider)
  extends Client {

  import driver.api._

  def store (client: ClientData): Future[Boolean] = {
    db.run(clientQuery += client).map(_ > 0)
  }

  def findByEmail (email: String): Future[Option[String]] = {
    val query = clientQuery.filter(_.userEmail === email).map(_.userEmail).result.headOption
    db.run(query)
  }
}

trait Client extends HasDatabaseConfigProvider[JdbcProfile] {

  import driver.api._

  val clientQuery: TableQuery[clientTableMapping] = TableQuery[clientTableMapping]

  class clientTableMapping (tag: Tag) extends Table[ClientData](tag, "client") {
    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def firstName: Rep[String] = column[String]("firstname")

    def lastName: Rep[String] = column[String]("lastname")

    def age: Rep[Int] = column[Int]("age")

    def userEmail: Rep[String] = column[String]("useremail")

    def password: Rep[String] = column[String]("password")

    def company: Rep[String] = column[String]("company")

    def * : ProvenShape[ClientData] = (id, firstName, lastName, age, userEmail, password, company) <> (ClientData.tupled, ClientData.unapply)
  }

}
