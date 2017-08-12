package controllers

import javax.inject.Inject
import models.{ClientData, ClientRepository}
import play.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

case class ClientProfile (firstName: String, lastName: String,
                          age: Int, email: String)

class ClientControllers @Inject()
(val messagesApi: MessagesApi,
 val clientRepo: ClientRepository, val form: ClientForm)
  extends Controller with I18nSupport {

  def loginValidation = Action { implicit request =>
    form.loginFormConstraints.bindFromRequest.fold(
      formWithErrors => {
        Logger.error("Error in the form")
        BadRequest(views.html.loginUser(formWithErrors))
      },
      loginData => {
        clientRepo.store(ClientData(1, "N", "J", 18, "A", "A", "A"))
        Ok(views.html.LoginDetails(loginData))
      })
  }

  def registrationValidation = Action.async { implicit request =>
    form.customerFormConstraints.bindFromRequest.fold(
      formWithErrors => {
        Logger.error("Error in the form")
        Logger.info("Heloooooooooooooo")
        Future.successful(BadRequest(views.html.user(formWithErrors)))
      }, customerData => {
        Logger.info("Hiiiiii")
        clientRepo.findByEmail(customerData.userEmail).flatMap {
          case Some(_) => Logger.info("Email already exists")
            Future.successful(Redirect(routes.ClientView.login())
              .flashing("information" -> "Email already exists, Please Log In"))
          case None =>
            val customRepo = ClientData(0, customerData.firstName, customerData.lastName,
              customerData.age, customerData.userEmail, customerData.password,
              customerData.company)

            val clientProfile = ClientProfile(customerData.firstName, customerData.lastName,
              customerData.age, customerData.userEmail)

            clientRepo.store(customRepo).map {
              case true => Redirect(routes.ClientView.success())
                .flashing("success" -> "You are successfully registered!")
                .withSession("CustomerProfile" -> clientProfile.email)

              case false => Redirect(routes.ClientView.errors())
                .flashing("error" -> "Something went wrong")
            }
        }
      }
    )
  }
}
