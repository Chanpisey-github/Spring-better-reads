package org.pisey.betterreadapp;

import org.pisey.betterreadapp.connection.DataStaxAstraProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@EnableCassandraRepositories
=======

@SpringBootApplication
>>>>>>> parent of 40ba633 (oauth-to-github)
public class BetterReadAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BetterReadAppApplication.class, args);
	}

}
