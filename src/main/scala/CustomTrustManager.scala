package ScalaSSLDebug

import java.security.KeyStore
import java.security.cert.X509Certificate

import com.typesafe.scalalogging.Logger
import javax.net.ssl.{SSLContext, TrustManager, X509TrustManager}


class CustomTrustManager {
  val logger = Logger("console")

  val trustManager = new X509TrustManager() {
    override def checkClientTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {
      logger.info("Client certificate information")
      x509Certificates.zipWithIndex.foreach {
        case (certificate, index)  => {
          val certificateInfo = CertUtils.formatCertificate(certificate)
          logger.info(certificateInfo)
        }
      }
    }


    override def checkServerTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {
      logger.info("Server certificates information")
      x509Certificates.zipWithIndex.foreach {
        case (certificate, index)  => {
          val certificateInfo = CertUtils.formatCertificate(certificate)
          logger.info(s"chain [${index}]")
          logger.info(certificateInfo)
        }
      }
    }


    override def getAcceptedIssuers: Array[X509Certificate] = {
      Array.empty
    }
  }

}