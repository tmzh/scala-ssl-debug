package ScalaSSLDebug

import java.security.KeyStore

object Main {
  def main(args: Array[String]) = {
    val httpsUrl = args(0)

    val trustStore = System.getProperty("javax.net.ssl.trustStore")
    val javaHome = System.getProperty("java.home")
    val trustStoreType = System.getProperty("javax.net.ssl.trustStoreType", KeyStore.getDefaultType)
    val trustStoreProvider = System.getProperty("javax.net.ssl.trustStoreProvider", "")
    println(s"trustStore is: ${trustStore}")
    println(s"trustStore type is : ${trustStoreType}")
    println(s"trustStore provider is : ${trustStoreProvider}")
    println(s"javaHome is : ${javaHome}")

    val httpsClient = new CustomHttpsClient(true, true)
    httpsClient.testConnect(httpsUrl)
  }

}
