package controllers

import javax.inject.Inject

import com.coffeeapp.repo.{BusinessRepository, OutletRepository}
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

class CoffeeRestController @Inject()(businessRepository: BusinessRepository,
                                     outletRepository: OutletRepository) extends Controller {



}
