package com.datagrid.ocp4.remote.client;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.ClientIntelligence;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.infinispan.client.hotrod.impl.ConfigurationProperties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		System.out.println("server started ===================================");
		ConfigurationBuilder builder = new ConfigurationBuilder();
		builder.addServer()
				.host("cache-dr.apps-crc.testing")
				.port(443)
				.security().authentication()
				.username("admin")
				.password("password")
				.realm("default")
				.saslMechanism("DIGEST-MD5")
				.ssl()
				.sniHostName("cache-dr.apps-crc.testing")
				.trustStorePath("src/main/resources/tls.crt")
				.clientIntelligence(ClientIntelligence.BASIC);

		RemoteCacheManager cacheManager = new RemoteCacheManager(builder.build());
		RemoteCache<String, String> cache = cacheManager.getCache("TEST");
		cache.put("hello", "world");
		cache.put("Hey", "Whatsapp");
		// Retrieve the value and print it.
	//	cache.remove("hello");
		System.out.printf("key = %s\n", cache.get("hello"));

	}
}
