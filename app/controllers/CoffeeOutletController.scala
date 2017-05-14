package controllers

import javax.inject.Inject

import com.coffeeapp.repo.{BusinessRepository, Outlet, OutletRepository}
import controllers.CoffeeOutletForm.outletForm
import play.api.data.Form
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}

import scala.util.Try


class CoffeeOutletController @Inject()(outletRepository: OutletRepository,
                                       businessRepository: BusinessRepository,
                                       val messagesApi: MessagesApi)
  extends Controller with I18nSupport {

  def editOutlet(id: Int) = Action {
    outletRepository.outletById(id).blocking match {
      case Some(outlet) =>
        Ok(views.html.admin.outletForm("Update Outlet", outletForm fill outlet, outlet.businessId))
      case None =>
        Ok(views.html.admin.msg("Outlet not found"))
    }
  }

  def createOutlet(businessId: Int) = Action {
    implicit render =>
      businessRepository.businessById(businessId).blocking match {
        case Some(business) =>
          Ok(views.html.admin.outletForm("Create Outlet", outletForm, businessId))
        case None =>
          Ok(views.html.admin.msg("Business not found"))
      }

  }

  def deleteOutletById(id: Int, businessId: Int) = Action {
    implicit request =>
      outletRepository.deleteOutlet(id).blocking
      Redirect(routes.CoffeeBusinessController.listBusinessById(businessId))
  }


  def submitOutletForm = Action { implicit request =>
    val formErrorFn: (Form[Outlet]) => Result = {
      errorForm =>
        val businessId =
          errorForm.data.get("businessId")
            .flatMap(id => Try(id.toInt).toOption)
        BadRequest(views.html.admin.outletForm("Outlet Error", errorForm, businessId.get))
    }


    val formSuccessFn: Outlet => Result = {
      outlet => {
        //create or update outlet depending on whether outlet from form has an id assigned
        outlet.id match {
          case Some(_) =>
            outletRepository.updateOutlet(outlet).blocking
          case None =>
            outletRepository.createOutlet(outlet).blocking
        }
        Redirect(routes.CoffeeBusinessController.listBusinessById(outlet.businessId))
      }
    }

    outletForm.bindFromRequest.fold(formErrorFn, formSuccessFn)
  }


}
