package controllers

import com.coffeeapp.repo.{Business, Outlet}
import play.api.data.Forms.{default, mapping, of}
import play.api.data.{Form, Mapping}

import play.api.data.{Form, Mapping}
import play.api.data.Forms._
object CoffeeBusinessForm {

  val timeInstant: Mapping[java.time.Instant] = of[java.time.Instant]
  val outlets: Mapping[Option[Seq[Outlet]]] = of[Option[Seq[Outlet]]]

  val coffeeBusinessForm: Form[Business] = ???

}
