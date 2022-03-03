package org.pisey.betterreadapp;

import java.nio.file.Path;

import org.pisey.betterreadapp.connection.DataStaxAstraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@RestController

public class BetterReadAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterReadAppApplication.class, args);
	}


	@RequestMapping("/users")
	public String user(@AuthenticationPrincipal OAuth2User principal){
		System.out.println(principal);
		return principal.getAttribute("name");
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();

		return Builder -> Builder.withCloudSecureConnectBundle(bundle);
	}



	
}
