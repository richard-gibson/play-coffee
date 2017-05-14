name := """coffee-app"""
organization := "com.coffeeapp"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.11"

libraryDependencies += filters

libraryDependencies ++= Seq(
  "org.webjars" % "bootstrap" % "3.3.6",
  "com.h2database" % "h2" % "1.4.192",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
)

lazy val api = (project in file("modules/api"))
  .settings(Common.projectSettings)


lazy val flyway = (project in file("modules/flyway"))
  .enablePlugins(FlywayPlugin)


lazy val slick = (project in file("modules/slick"))
  .settings(Common.projectSettings)
  .aggregate(api)
  .dependsOn(api)

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .aggregate(slick)
  .dependsOn(slick)
