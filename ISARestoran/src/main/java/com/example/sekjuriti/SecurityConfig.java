package com.example.sekjuriti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.*;


@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/html/**", "/css/**").permitAll()
                .antMatchers("/", "/index.html").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
		 		.loginPage("/index.html")
		 		.permitAll()
		 		.usernameParameter("email")
		 		.loginProcessingUrl("/korisnik/login")
		 		.failureUrl("/index.html")
		 		.successHandler(authSuccessHandler())
		 		.and()
                .logout()
                .logoutUrl("/korisnik/logout")
                .deleteCookies("remember-me")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/index.html")
                .permitAll()
                .and()
                .csrf().disable()
                .rememberMe()
		 	    .and()
		 	    .apply(new SpringSocialConfigurer().signupUrl("/index.html")
		 	    								   .alwaysUsePostLoginUrl(true)
		 	    								   .postLoginUrl("/home.html"));
    }
	
	@Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
    
    
    @Bean
    public AuthenticationSuccessHandler authSuccessHandler(){
    	return new UrlAuthenticationSuccessHandler();
    }
   
    

}
