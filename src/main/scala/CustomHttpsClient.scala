package ScalaSSLDebug

import javax.net.ssl.{HostnameVerifier, HttpsURLConnection, SSLContext, SSLSession, TrustManager}
import java.io.FileOutputStream
import java.net.URL
import java.security.cert.X509Certificate
import sun.security.ssl.SSLLogger

class CustomHttpsClient(acceptAnyCertificate: Boolean,
                        acceptAnyHost: Boolean) {
  val sslContext = if (!acceptAnyCertificate) SSLContext.getDefault else {
    val customTrustManager = new CustomTrustManager()
    val trustManager = customTrustManager.trustManager
    val ctx = SSLContext.getInstance("TLS")

    ctx.init(Array.empty, Array(trustManager), null)
    ctx
  }

  val hostnameVerifier = if (!acceptAnyHost) {
    HttpsURLConnection.getDefaultHostnameVerifier
  } else {
    val customHostnameVerifier = new HostnameVerifier {
      override def verify(s: String, sslSession: SSLSession): Boolean = true
    }
    customHostnameVerifier
  }

  def connect(httpsUrl: String): Unit = {
    val url = new URL(httpsUrl)
    val connection:HttpsURLConnection = url.openConnection.asInstanceOf[HttpsURLConnection]
    connection.setHostnameVerifier(this.hostnameVerifier)
    connection.setSSLSocketFactory(this.sslContext.getSocketFactory)

    val responseCode: Int = connection.getResponseCode
    println("HTTPS connection response code is", responseCode)

  }



}
