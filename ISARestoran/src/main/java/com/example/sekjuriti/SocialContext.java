package com.example.sekjuriti;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.AuthenticationNameUserIdSource;

@Configuration
@EnableSocial
public class SocialContext implements SocialConfigurer {

	@Autowired
	DataSource dataSource;
	
	
	@Override
	public void addConnectionFactories(ConnectionFactoryConfigurer cfConfig, Environment env) {
//		cfConfig.addConnectionFactory(new FacebookConnectionFactory(
//                env.getProperty("spring.social.facebook.app-id"),
//                env.getProperty("spring.social.facebook.app-secret")
//        ));
	}

	@Override
	public UserIdSource getUserIdSource() {
		return new AuthenticationNameUserIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator cfl) {
		return new JdbcUsersConnectionRepository(
				dataSource, 
				cfl,
				Encryptors.noOpText());

	}
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(
				ConnectionFactoryLocator connectionFactoryLocator,
				UsersConnectionRepository connectionRepository) {
		return new ProviderSignInUtils(connectionFactoryLocator,
                   connectionRepository
		);
	}
	
	

}
