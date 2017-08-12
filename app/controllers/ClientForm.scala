package controllers

import play.api.data.Form
import play.api.data.Forms.{mapping, number, text, email}
import play.api.data.validation.Constraints.{max, min}
import play.api.data.validation.{Constraint, Invalid, Valid, ValidationError}
import scala.util.matching.Regex

class ClientForm {
  val Number: Regex = """\d*""".r
  val Letters: Regex = """[A-Za-z]*""".r
  val passwordValidationConstraint: Constraint[String] =
    Constraint[String]("constraints.password.check") {
      case "" => Invalid(ValidationError("Password missing"))
      case Number() => Invalid(ValidationError("Password is all numbers"))
      case Letters() => Invalid(ValidationError("Password is all letters"))
      case password if password.length() < 5 => Invalid(ValidationError("Password is short"))
      case _ => Valid
    }

  val loginFormConstraints = Form(mapping
  (
    "userEmail" -> email,
    "password" -> text.verifying(passwordValidationConstraint)
  )
  (LoginData.apply)(LoginData.unapply))

  val customerFormConstraints = Form(mapping
  ("firstName" -> text.verifying("Please enter youtr first name", firstName => !firstName.isEmpty),
    "lastName" -> text.verifying("Please enter your last name", lastName => !lastName.isEmpty),
    "age" -> number.verifying(min(0), max(100)),
    "userEmail" -> text,
    "password" -> text.verifying(passwordValidationConstraint),
    "confirmPassword" -> text,
    "company" -> text)
  (CustomerData.apply)(CustomerData.unapply)
    verifying("Passwords do not match", customer => customer.password.equals(customer.confirmPassword)))
}
