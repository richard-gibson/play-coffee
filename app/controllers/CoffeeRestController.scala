package controllers

import javax.inject.Inject

import com.coffeeapp.repo.{Business, BusinessRepository, Outlet, OutletRepository}
import play.api.libs.json.{Json, OFormat}
import play.api.mvc.{Action, Controller}


class CoffeeRestController @Inject()(businessRepository: BusinessRepository,
                                     outletRepository: OutletRepository) extends Controller {

  implicit val outletAsJson: OFormat[Outlet] = Json.format[Outlet]
  implicit val businessAsJson: OFormat[Business] = Json.format[Business]

  def listByLocation(location: String) = Action {
    val outlets = outletRepository.outletsByLocation(location).blocking
    Ok(Json.toJson(outlets))
  }

  def listByPostcode(postcode: String) = Action {
    val outlets = outletRepository.outletsByPostcode(postcode).blocking
    Ok(Json.toJson(outlets))
  }

  def listAllOutlets = Action {
    val outlets = outletRepository.allOutlets.blocking
    Ok(Json.toJson(outlets))
  }

  def listById(id: Int) = Action {
    val business = businessRepository.businessById(id).blocking
    Ok(Json.toJson(business))
  }

  def listAllBusinesses = Action {
    val business = businessRepository.allBusinesses.blocking
    Ok(Json.toJson(business))
  }

}
