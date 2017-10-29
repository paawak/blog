package com.swayam.demos.apachehttpclient;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.client.CookieStore;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SelfSignedCertificateConnectorDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(SelfSignedCertificateConnectorDemo.class);

	public static void main(String[] a) throws IOException {
		String selfSignedUrl = "https://localhost:8443/docs/security-howto.html";
		Response response = new SelfSignedCertificateConnectorDemo().connectUnTrusted(selfSignedUrl);
		LOGGER.info("The content is: {}", response.returnContent().asString());
	}

	public Response connectUnTrusted(String selfSignedUrl) throws IOException {
		TrustStrategy trustStrategy = new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				return true;
			}
		};

		SSLContext sslContext;

		try {
			sslContext = new SSLContextBuilder().loadTrustMaterial(null, trustStrategy).build();
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e);
		}

		CloseableHttpClient httpClient = HttpClients.custom().setSSLHostnameVerifier(NoopHostnameVerifier.INSTANCE)
				.setSSLContext(sslContext).build();

		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie cookie = new BasicClientCookie("foo", "value");
		cookie.setDomain("palashray.com");

		Executor executor = Executor.newInstance(httpClient).use(cookieStore);

		Request request = Request.Post(selfSignedUrl);

		return executor.execute(request);

	}

}
