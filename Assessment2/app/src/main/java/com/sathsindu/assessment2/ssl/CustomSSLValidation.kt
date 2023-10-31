package com.sathsindu.assessment2.ssl

import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

internal class CustomSSLValidationTrustManager : X509TrustManager {
    @Throws(CertificateException::class)
    override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        // Disable SSL client certificate validation
        // Note that this is recommended only for learning/local-dev-testing etc.
    }

    @Throws(CertificateException::class)
    override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        // Disable SSL server certificate validation
        // Note that this is recommended only for learning/local-dev-testing etc.
    }

    override fun getAcceptedIssuers(): Array<X509Certificate?> {
        return arrayOfNulls(0)
    }
}