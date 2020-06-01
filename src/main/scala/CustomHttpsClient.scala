package ScalaSSLDebug

import javax.net.ssl.{HostnameVerifier, HttpsURLConnection, SSLContext, SSLSession, TrustManager}
import java.net.URL

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
      override def verify(hostName: String, sslSession: SSLSession): Boolean = {
        println("Host: ", hostName)
        println("Session Peer: ", sslSession.getPeerHost)
        true
      }
    }
    customHostnameVerifier
  }

  def testConnect(httpsUrl: String): Unit = {
    val url = new URL(httpsUrl)
    val connection:HttpsURLConnection = url.openConnection.asInstanceOf[HttpsURLConnection]
    connection.setHostnameVerifier(this.hostnameVerifier)
    connection.setSSLSocketFactory(this.sslContext.getSocketFactory)

    val responseCode: Int = connection.getResponseCode
    println("HTTPS connection response code is", responseCode)

  }



}
