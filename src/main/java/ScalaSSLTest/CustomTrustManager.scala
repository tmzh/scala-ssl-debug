package ScalaSSLTest

import java.security.cert.X509Certificate

import javax.net.ssl.X509TrustManager


class CustomTrustManager {

  val trustManager:X509TrustManager = new X509TrustManager() {
    override def checkClientTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {
      println("Client certificate information")
      x509Certificates.zipWithIndex.foreach {
        case (certificate, index)  =>
          val certificateInfo = CertUtils.formatCertificate(certificate)
          println(certificateInfo)
      }
    }


    override def checkServerTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {
      println("Server certificates information")
      x509Certificates.zipWithIndex.foreach {
        case (certificate, index)  =>
          val certificateInfo = CertUtils.formatCertificate(certificate)
          println(s"chain [$index]")
          println(certificateInfo)
      }
    }


    override def getAcceptedIssuers: Array[X509Certificate] = {
      Array.empty
    }
  }

}