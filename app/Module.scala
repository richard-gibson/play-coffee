import javax.inject.{Inject, Provider, Singleton}

import com.coffeeapp.repo.{BusinessRepository, DBBusinessRepository, DBOutletRepository, OutletRepository}
import com.google.inject.AbstractModule
import com.typesafe.config.Config
import play.api.inject.ApplicationLifecycle
import play.api.{Configuration, Environment, Logger}
import slick.jdbc.JdbcBackend.Database

import scala.concurrent.Future

/**
 * Module to bind API module to Slick implementation
**/
class Module(environment: Environment,
             configuration: Configuration) extends AbstractModule {
  override def configure(): Unit = {
    bind(classOf[Config]).toInstance(configuration.underlying)
    bind(classOf[Database]).toProvider(classOf[DatabaseProvider])

    bind(classOf[OutletRepository]).to(classOf[DBOutletRepository])
    bind(classOf[BusinessRepository]).to(classOf[DBBusinessRepository])

    bind(classOf[DBCloseHook]).asEagerSingleton()
  }
}

@Singleton
class DatabaseProvider @Inject() (config: Config) extends Provider[Database] {
  lazy val get = Database.forConfig("coffeeapp.database", config)
}

/** Closes database connections safely.  Important on dev restart. */
class DBCloseHook @Inject()(db:Database, lifecycle: ApplicationLifecycle) {
  lifecycle.addStopHook { () =>
    Future.successful(db.close())
  }
}
