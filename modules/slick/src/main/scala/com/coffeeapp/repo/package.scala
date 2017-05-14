package com.coffeeapp

import java.sql.Timestamp

import com.coffeeapp.dal.Tables._
import profile.api._


package object repo {

  val businessLeftJoinOutlet =
    CoffeeBusiness joinLeft CoffeeOutlet on (_.id === _.businessId)

  val outletJoinBusiness =
    CoffeeOutlet join CoffeeBusiness on (_.businessId === _.id)

  def outletByIdQuery(id: Int) =
    CoffeeOutlet filter(_.id === id)

  def businessByIdQuery(id: Int) =
    CoffeeBusiness filter(_.id === id)

  def rowToOutlet(row: (Int, CoffeeOutletRow)) = {
    val (businessId, o) = row
    Outlet(Some(o.id), businessId, o.address, o.town, o.postcode)
  }

  def outletToCoffeeOutletRow(outlet: Outlet): CoffeeOutletRow =
    CoffeeOutletRow(outlet.id.getOrElse(-1), outlet.businessId, outlet.address, outlet.town, outlet.postcode)


  def coffeeBusinessRowToBusiness(coffeeBusinessRow: CoffeeBusinessRow,
                                  businessOutlets: Option[Seq[CoffeeOutletRow]] = None): Business =
    Business(id = Some(coffeeBusinessRow.id),
              name = coffeeBusinessRow.name,
              email = coffeeBusinessRow.email,
              createdAt = coffeeBusinessRow.createdAt.toInstant,
              outlets = businessOutlets.map(_.map(busOutlet =>
                rowToOutlet((coffeeBusinessRow.id, busOutlet))))
            )

  //CoffeeBusinessRow id set to -1 if business id not set, jdbc will assign next auto in value automatically
  def businessToCoffeeBusinessRow(business: Business): CoffeeBusinessRow =
    CoffeeBusinessRow(id = business.id.getOrElse(-1),
                       name = business.name,
                       email = business.email,
                       createdAt = Timestamp.from(business.createdAt))
}
