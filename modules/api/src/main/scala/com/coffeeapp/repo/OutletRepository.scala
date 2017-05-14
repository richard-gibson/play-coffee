package com.coffeeapp.repo

import scala.concurrent.Future


trait OutletRepository {

  def outletsByLocation(id: String): Future[Seq[Outlet]]
  def outletsByPostcode(id: String): Future[Seq[Outlet]]
  def outletById(id: Int): Future[Option[Outlet]]
  def allOutlets: Future[Seq[Outlet]]
  def updateOutlet(outlet: Outlet): Future[Int]
  def deleteOutlet(id: Int): Future[Int]
  def createOutlet(outlet: Outlet): Future[Int]
}


case class Outlet(id: Option[Int], businessId: Int, address: String, town: String, postcode: String)



