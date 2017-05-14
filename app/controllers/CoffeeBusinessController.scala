package controllers

import javax.inject.Inject

import com.coffeeapp.repo._
import play.api.Logger
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._

import scala.util.{Failure, Success, Try}


class CoffeeBusinessController @Inject()(businessRepository: BusinessRepository,
                                         val messagesApi: MessagesApi)
  extends Controller with I18nSupport {

  def listBusinesses = Action {
    implicit request =>
      Logger.debug("listBusinesses called")
      val businesses = businessRepository.allBusinesses.blocking
      Ok(views.html.admin.businesses(businesses))
  }

  def listBusinessById(id:Int) = Action {
    implicit request =>
      Logger.debug(s"listBusinesses called with id $id")
      businessRepository.businessById(id).blocking match {
        case Some(business) => Ok(views.html.admin.outlets(business))
        case None => Ok(views.html.admin.msg("Business not found"))
      }
  }

  def deleteBusinessById(id: Int) = Action {
    implicit request =>
      Logger.debug(s"deleteBusinessById called with id $id")
      Try(businessRepository.deleteBusiness(id).blocking) match {
        case Failure(_) => Ok(views.html.admin.msg("Error occurred deleting business"))
        case Success(_) =>
          Redirect(routes.CoffeeBusinessController.listBusinesses())
      }

  }

}
