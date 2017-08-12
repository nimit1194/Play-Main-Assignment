package controllers

case class CustomerData (firstName: String,
                         lastName: String,
                         age: Int,
                         userEmail: String,
                         password: String,
                         confirmPassword: String,
                         company: String)

case class LoginData (
                       userEmail: String,
                       password: String
                     )