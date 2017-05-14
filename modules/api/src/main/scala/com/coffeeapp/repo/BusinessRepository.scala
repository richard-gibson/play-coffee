package com.coffeeapp.repo

import java.time.{Instant, LocalDateTime, ZoneOffset}

import scala.concurrent.Future

trait BusinessRepository {

  def allBusinesses: Future[Seq[Business]]
  def businessById(id: Int): Future[Option[Business]]
  def updateBusiness(business: Business): Future[Int]
  def deleteBusiness(id: Int): Future[Int]
  def createBusiness(business: Business): Future[Int]
}

case class Business(id: Option[Int], name: String, email: String, createdAt: Instant, outlets: Option[Seq[Outlet]] = None)