package com.example.sekjuriti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.social.security.SocialUserDetails;

@Service
public class CustomSocialUserDetailService implements SocialUserDetailsService {

	@Autowired
	UserDetailsService userDetailsService;
		
	@Override
	public SocialUserDetails loadUserByUserId(String email) throws UsernameNotFoundException {
		 System.out.println("Social user details:" + email);
		 UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		 return (SocialUserDetails) userDetails;
	}

}
