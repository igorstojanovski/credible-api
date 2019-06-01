package com.credible.configuration.services;

import com.credible.configuration.security.ApplicationSecurityException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Service
public class SecureHttpService {

    private static final String KEYSTOREPATH = "test.p12"; // or .p12
    private static final String KEYSTOREPASS = "qwerty123";
    private static final String KEYPASS = "qwerty123";
    private final CloseableHttpClient secureHttpClient;

    public SecureHttpService() {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

        try {
            SSLContext sslContext = SSLContexts.custom()
                    .loadKeyMaterial(readKeyStore(), KEYPASS.toCharArray())
                    .loadTrustMaterial(readTrustStore(), acceptingTrustStrategy)// use null as second param if you don't have a separate key password
                    .build();
            secureHttpClient = HttpClients.custom().setSSLContext(sslContext).build();
        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | UnrecoverableKeyException
                | IOException | CertificateException e) {
            throw new ApplicationSecurityException("Error when setting up the SSL context.", e);
        }
    }

    public HttpResponse execute(HttpUriRequest request) throws IOException {
        return secureHttpClient.execute(request);
    }

    KeyStore readKeyStore() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        try (InputStream keyStoreStream = ClassLoader.getSystemResourceAsStream(KEYSTOREPATH)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12"); // or "PKCS12"
            keyStore.load(keyStoreStream, KEYSTOREPASS.toCharArray());
            return keyStore;
        }
    }

    KeyStore readTrustStore() throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException {
        try (InputStream trustStoreStream = ClassLoader.getSystemResourceAsStream("cacerts")) {
            KeyStore trustStore = KeyStore.getInstance("JKS"); // or "PKCS12"
            trustStore.load(trustStoreStream, "changeit".toCharArray());
            return trustStore;
        }
    }
}
