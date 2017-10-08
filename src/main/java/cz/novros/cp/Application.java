package cz.novros.cp;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.annotation.Nonnull;
import javax.net.ssl.SSLContext;

import com.google.common.collect.ImmutableSet;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import cz.novros.cp.entity.User;
import cz.novros.cp.service.user.UserSecurityService;

@SpringBootApplication
@EnableScheduling
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner demo(@Nonnull final UserSecurityService userSecurityService) {
		return (args) -> {
			// TODO Remove after implementation of registration
			userSecurityService.registerUser(new User("test@test.cz", "password", ImmutableSet.of()));
		};
	}

	@Bean
	public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		final TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		final SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
		final CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLSocketFactory(csf)
				.build();
		final HttpComponentsClientHttpRequestFactory requestFactory =
				new HttpComponentsClientHttpRequestFactory();
		requestFactory.setHttpClient(httpClient);

		return new RestTemplate(requestFactory);
	}
}
