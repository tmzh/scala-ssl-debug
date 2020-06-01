package ScalaSSLTest
import java.security.cert.X509Certificate

object CertUtils {

  def formatCertificate(certificate:X509Certificate): String = {
    val version = certificate.getVersion
    val serialNumber = certificate.getSerialNumber
    val signatureAlgorithm = certificate.getSigAlgName
    val issuer = certificate.getIssuerX500Principal
    val notBefore = certificate.getNotBefore
    val notAfter = certificate.getNotAfter
    val subject = certificate.getSubjectX500Principal
    val subjectAlternativeNames = certificate.getSubjectAlternativeNames
    val subjectPublicKey = certificate.getPublicKey

    val certMessage =
      s"""
        |version                   : v${version}
        |serial number             : ${serialNumber}
        |signature algorithm       : ${signatureAlgorithm}
        |issuer                    : ${issuer}
        |not before                : ${notBefore}
        |not  after                : ${notAfter}
        |subject                   : ${subject}
        |subject alternative names : ${subjectAlternativeNames}
        |subject public key        : ${subjectPublicKey}
        """.stripMargin

    certMessage
  }


}
