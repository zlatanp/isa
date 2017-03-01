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
            .antMatchers("/register.html").permitAll()
                .antMatchers("/", "/index.html").permitAll()
                .antMatchers("/korisnik/register").permitAll()
                .antMatchers("/smene/novaSmena/{tekst}", "/zaposleni.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/korisnik/active/{code}").permitAll()
                .antMatchers("/home.html").hasAuthority("ROLE_GOST")
                .antMatchers("/adminPage.html").hasAuthority("ROLE_MENADZERSISTEMA")
                .antMatchers("/changePassword.html").hasAnyAuthority("ROLE_MENADZERSISTEMA","ROLE_MENADZERRESTORANA","ROLE_KUVAR","ROLE_KONOBAR","ROLE_SANKER","ROLE_PONUDJAC")
                .antMatchers("/jelovnik.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/kartaPica.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/menadzerPage.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/ponude.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/sedenje.html").hasAuthority("ROLE_MENADZERRESTORANA")
                .antMatchers("/notFound.html").hasAnyAuthority("ROLE_MENADZERSISTEMA","ROLE_MENADZERRESTORANA","ROLE_KUVAR","ROLE_KONOBAR","ROLE_SANKER","ROLE_PONUDJAC")
                .anyRequest().authenticated()
                .and()
                .formLogin()
		 		.loginPage("/index.html")
		 		.permitAll()
		 		.usernameParameter("email")
		 		.loginProcessingUrl("/korisnik/login")
		 		.failureUrl("/notFound.html")
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
                .apply(new SpringSocialConfigurer().signupUrl("/register.html")
						   .alwaysUsePostLoginUrl(true)
						   .postLoginUrl("/postLogin.html"));
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
