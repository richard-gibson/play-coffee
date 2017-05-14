package com.coffeeapp.repo

import com.coffeeapp.dal.Tables._
import javax.inject.Inject
import org.slf4j.LoggerFactory
import slick.jdbc.JdbcBackend.Database
import profile.api._

import scala.concurrent.{ExecutionContext, Future}


class DBOutletRepository @Inject() (db:Database)(implicit ec: ExecutionContext) extends OutletRepository {

  val logger = LoggerFactory.getLogger(classOf[DBOutletRepository])

  private def asOutlet(coffeeOutletRows: Seq[(Int, CoffeeOutletRow)]):
    Seq[Outlet] = coffeeOutletRows.map(rowToOutlet)

  override def outletsByLocation(location: String): Future[Seq[Outlet]] = {
    val locationQuery =
      for {
        (outlet, business) <- outletJoinBusiness
        if outlet.town.toLowerCase like s"%${location.toLowerCase}%"
      } yield (business.id, outlet)
    locationQuery.result.statements.headOption.foreach(logger.info)

    db.run(locationQuery.result).map(asOutlet)
  }


  override def outletsByPostcode(postcode: String): Future[Seq[Outlet]] = {
    val postcodeQuery =
      for {
        (outlet, business) <- outletJoinBusiness
        if outlet.postcode.toLowerCase like s"%${postcode.toLowerCase}%"
      } yield (business.id, outlet)
    postcodeQuery.result.statements.headOption.foreach(logger.info)
    db.run(postcodeQuery.result).map(asOutlet)
  }

  override def outletById(id: Int): Future[Option[Outlet]] = {
    val idQuery =
      for {
        (outlet, business) <- outletJoinBusiness
        if outlet.id === id
      } yield (business.id, outlet)
    idQuery.result.statements.headOption.foreach(logger.info)
    db.run(idQuery.result)
      .map(asOutlet)
      .map(_.headOption)
  }

  override def allOutlets: Future[Seq[Outlet]] ={
    val allQuery = for {
      (outlet, business) <- outletJoinBusiness
    } yield (business.id, outlet)
    allQuery.result.statements.headOption.foreach(logger.info)
    db.run(allQuery.result).map(asOutlet)
  }

  override def updateOutlet(outlet: Outlet): Future[Int] =
    outlet.id match {
      case Some(outletId) =>
        outletId.result.statements.headOption.foreach(logger.info)
        db.run(outletByIdQuery(outletId)
          .update(outletToCoffeeOutletRow(outlet)))
      case None => Future.successful(-1)
    }


  override def deleteOutlet(id: Int): Future[Int] =
    db.run(outletByIdQuery(id).delete)


  override def createOutlet(outlet: Outlet): Future[Int] =
    db.run(CoffeeOutlet += outletToCoffeeOutletRow(outlet))

}




