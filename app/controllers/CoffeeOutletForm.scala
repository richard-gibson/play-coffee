package controllers

import com.coffeeapp.repo.Outlet
import play.api.data.Form
import play.api.data.Forms._

object CoffeeOutletForm {

  val outletForm: Form[Outlet] =
    Form(mapping(
      "id"          -> optional(number),
      "businessId"  -> number,
      "address"     -> nonEmptyText,
      "town"        -> nonEmptyText,
      "postcode"    -> nonEmptyText
    )(Outlet.apply)(Outlet.unapply))

}
