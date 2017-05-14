
libraryDependencies ++= Seq(
  "com.zaxxer" % "HikariCP" % "2.4.1",
  "com.typesafe.slick" %% "slick" % "3.1.1",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.1"
)

lazy val databaseUrl = sys.env.getOrElse("DB_DEFAULT_URL", "jdbc:h2:./coffee")
lazy val databaseUser = sys.env.getOrElse("DB_DEFAULT_USER", "sa")
lazy val databasePassword = sys.env.getOrElse("DB_DEFAULT_PASSWORD", "")

slickCodegenSettings
slickCodegenDatabaseUrl := databaseUrl
slickCodegenDatabaseUser := databaseUser
slickCodegenDatabasePassword := databasePassword
slickCodegenDriver := slick.driver.H2Driver
slickCodegenJdbcDriver := "org.h2.Driver"
slickCodegenOutputPackage := "com.coffeeapp.dal"
slickCodegenExcludedTables := Seq("schema_version")

sourceGenerators in Compile += slickCodegen.taskValue
