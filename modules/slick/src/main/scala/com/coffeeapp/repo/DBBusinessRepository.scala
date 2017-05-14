package com.coffeeapp.repo

import java.sql.Timestamp

import com.coffeeapp.dal.Tables._
import javax.inject.Inject
import org.slf4j.LoggerFactory
import slick.jdbc.JdbcBackend.Database
import profile.api._

import scala.concurrent.{ExecutionContext, Future}


class DBBusinessRepository @Inject()(db: Database)(implicit ec: ExecutionContext) extends BusinessRepository {

  val logger = LoggerFactory.getLogger(classOf[DBBusinessRepository])

  private def groupByBusiness(dataTuples: Seq[(CoffeeBusinessRow, Option[CoffeeOutletRow])]):
  List[Business] = {

    val groupedByBusiness = dataTuples.groupBy(_._1)
    groupedByBusiness.map {
      case (business, tuples) =>
        val outlets = tuples.map(_._2).distinct.flatten
        coffeeBusinessRowToBusiness(business, Some(outlets))
    }.toList
  }


  override def allBusinesses: Future[Seq[Business]] = {
    CoffeeBusiness.result.statements.headOption.foreach(logger.info)
    db.run(CoffeeBusiness.result).map(_.map(businessRow =>
      coffeeBusinessRowToBusiness(businessRow)))
  }

  override def updateBusiness(business: Business): Future[Int] =
    business.id match {
      case Some(businessId) =>
        db.run(businessByIdQuery(businessId)
          .update(businessToCoffeeBusinessRow(business)))
      case None => Future.successful(-1)
    }

  override def deleteBusiness(id: Int): Future[Int] =
    db.run(businessByIdQuery(id).delete)

  override def createBusiness(business: Business): Future[Int] =
    db.run(CoffeeBusiness += businessToCoffeeBusinessRow(business))

  override def businessById(id: Int): Future[Option[Business]] = {
    val businessQuery =
      for {
        (business, outlet) <- businessLeftJoinOutlet
        if business.id === id
      } yield (business, outlet)
    businessQuery.result.statements.headOption.foreach(logger.info)
    db.run(businessQuery.result)
      .map(groupByBusiness)
      .map(_.headOption)
  }

}
