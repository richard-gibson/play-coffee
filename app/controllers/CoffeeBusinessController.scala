package controllers

import javax.inject.Inject

import com.coffeeapp.repo._
import controllers.CoffeeBusinessForm.coffeeBusinessForm
import play.api.Logger
import play.api.data.Form
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

  def editBusiness(id:Int) = Action {
    businessRepository.businessById(id).blocking match {
      case Some(business) =>
        Ok(views.html.admin.businessform("Update Business", coffeeBusinessForm fill business))
      case None =>
        Ok(views.html.admin.msg("Business not found"))
    }
  }

  def createBusiness = Action {
    Ok(views.html.admin.businessform("Create Business", coffeeBusinessForm))
  }

  def submitBusinessForm = Action { implicit request =>

    val formErrorFn: (Form[Business]) => Result = {
      errorForm =>
        BadRequest(views.html.admin.businessform("Business Error", errorForm))
    }


    val formSuccessFn: Business => Result = {
      business => {
        //create or update business depending on whether business from form has an id assigned
        business.id match {
          case Some(_) =>
            businessRepository.updateBusiness(business).blocking
          case None =>
            businessRepository.createBusiness(business).blocking
        }
        Redirect(routes.CoffeeBusinessController.listBusinesses())
      }
    }

    coffeeBusinessForm.bindFromRequest().fold(formErrorFn, formSuccessFn)
  }

}
