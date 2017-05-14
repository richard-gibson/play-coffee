package controllers

import javax.inject.Inject

import com.coffeeapp.repo.{BusinessRepository, OutletRepository}
import play.api.mvc._
import play.api.i18n.{I18nSupport, MessagesApi}


class CoffeeOutletController @Inject()(outletRepository: OutletRepository,
                                       businessRepository: BusinessRepository,
                                       val messagesApi: MessagesApi)
  extends Controller with I18nSupport {



}
