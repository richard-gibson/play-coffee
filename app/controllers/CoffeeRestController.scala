package controllers

import javax.inject.Inject

import com.coffeeapp.repo.{Business, BusinessRepository, Outlet, OutletRepository}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, Controller}

import scala.concurrent.ExecutionContext.Implicits.global

class CoffeeRestController @Inject()(businessRepository: BusinessRepository,
                                     outletRepository: OutletRepository) extends Controller {


  implicit val outletAsJson: OFormat[Outlet] = Json.format[Outlet]
  implicit val businessAsJson: OFormat[Business] = Json.format[Business]

  def listByLocation(location: String) = Action.async {
    implicit request =>
      outletRepository.outletsByLocation(location)
        .map(outlets => Ok(Json.toJson(outlets)))
  }

  def listByPostcode(postcode: String) = Action.async {
    implicit request =>
      outletRepository.outletsByPostcode(postcode)
        .map(outlets => Ok(Json.toJson(outlets)))
  }

  def listAllOutlets = Action.async {
    implicit request =>
      outletRepository.allOutlets
        .map(outlets => Ok(Json.toJson(outlets)))
  }

  def listById(id: Int) = Action.async {
    implicit request =>
      businessRepository.businessById(id)
        .map(business => Ok(Json.toJson(business)))
  }

  def listAllBusinesses = Action.async {
    implicit request =>
      businessRepository.allBusinesses
        .map(business => Ok(Json.toJson(business)))
  }

}
