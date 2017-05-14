import sbt.Keys._
import sbt.{Resolver, _}

object Common {

  def projectSettings = Seq(
    scalaVersion := "2.11.11",
    javacOptions ++= Seq("-source", "1.8", "-target", "1.8"),
    scalacOptions ++= Seq(
      "-encoding", "UTF-8",
      "-deprecation",
      "-feature",
      "-unchecked",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-numeric-widen",
      "-Xfatal-warnings"
    ),
    resolvers ++= Seq(
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases",
       Resolver.sonatypeRepo("releases"),
       Resolver.sonatypeRepo("snapshots")),
    libraryDependencies ++= Seq(
      "javax.inject" % "javax.inject" % "1",
      "com.google.inject" % "guice" % "4.0",
      "org.slf4j" %  "slf4j-api" % "1.7.25",
      "ch.qos.logback"  % "logback-classic" % "1.2.2"
    ),
    scalacOptions in Test ++= Seq("-Yrangepos")
  )
}
