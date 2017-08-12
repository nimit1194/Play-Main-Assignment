package controllers
import javax.inject.Inject
import models.ClientRepository
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{Action, Controller}

class ClientView @Inject() (val messagesApi: MessagesApi
                            , val clientRepo: ClientRepository)
  extends Controller with I18nSupport {
  val form = new ClientForm
  def main = Action { implicit request =>
    Ok(views.html.index())
  }
  def login = Action { implicit request =>
    Ok(views.html.loginUser(form.loginFormConstraints.fill(LoginData(
      "Enter Email", "Enter Password"))))
  }
  def register = Action { implicit request =>
    Ok(views.html.user(form.customerFormConstraints.fill(CustomerData(
      "ABC", "DEF", 22, "abc@gmail.com", "asd", "asd", "XYZ"))))
  }
  def errors = Action { implicit request =>
    Ok(views.html.error())
  }
  def success = Action { implicit request =>
    Ok(views.html.Details())
  }
}
