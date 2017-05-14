import java.text.SimpleDateFormat
import java.time.Instant

import com.coffeeapp.repo.{Business, Outlet}
import play.api.data.FormError
import play.api.data.format.Formatter
import play.api.libs.json._

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}

package object controllers {

  implicit class FutureEnrichments[T](future: Future[T]) {
    def blocking: T = {
      Await.result(future, Duration.Inf)
    }
  }

  implicit val instantFormatter: Formatter[Instant] = new Formatter[Instant] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Instant] =
      try {
        Right(Instant.parse(data.getOrElse(key, "")))
      } catch {
        case e: Exception => Left(List(FormError(key, "Unable to parse to Timestamp")))
      }

    override def unbind(key: String, value: Instant): Map[String, String] =
      Map(key -> value.toString)
  }

  implicit val OutletsFormatter: Formatter[Option[Seq[Outlet]]] = new Formatter[Option[Seq[Outlet]]] {
    override def bind(key: String, data: Map[String, String]): Either[Seq[FormError], Option[Seq[Outlet]]] =
      try {
        Right(None)
      } catch {
        case e: Exception => Left(List(FormError(key, "outlet parse error")))
      }

    override def unbind(key: String, value: Option[Seq[Outlet]]): Map[String, String] =
      Map(key -> "")
  }

}
