package com.example.sekjuriti;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.example.beans.korisnici.Korisnik;



public class UrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		
		LoggedUser korisnik = (LoggedUser) authentication.getPrincipal();
		Korisnik user = korisnik.Korisnik();
		System.out.println("user ispromeniolozinku: +" + user.isPromenioLozinku());
		
		if (!user.isPromenioLozinku()){
			System.out.println("Ne dam!");
			redirectStrategy.sendRedirect(request, response, "/changePassword.html");
		} else {
			handle(request,response,authentication);
			clearAuthenticationAttributes(request);		
		}
	}
	
	public void handle(HttpServletRequest request,HttpServletResponse response,Authentication auth) throws IOException{		
		String targetURL = determineURL(auth);		
		System.out.println(targetURL);		
		redirectStrategy.sendRedirect(request, response, targetURL);		
				
	}		
	
	 public String determineURL(Authentication auth){		
			boolean gost = false; 		
			boolean menadzer = false;		
			boolean kuvar = false ,sanker = false ,konobar = false;		
			boolean admin=false;		
			boolean ponudjac=false;		
					
			Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();		
			for (GrantedAuthority grantedAuthority : authorities){		
				System.out.println(grantedAuthority.getAuthority());		
				if (grantedAuthority.getAuthority().equals("ROLE_GOST")){		
					gost= true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_MENADZERRESTORANA")){		
					menadzer = true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_KUVAR")){		
					kuvar = true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_KONOBAR")){		
					konobar = true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_SANKER")){		
					sanker = true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_MENADZERSISTEMA")){		
					admin= true;		
					break;		
				}		
				else if (grantedAuthority.getAuthority().equals("ROLE_PONUDJAC")){		
					ponudjac= true;		
					break;		
				}		
			}		
					
			if (gost)		
				return "/home.html";		
			else if (menadzer)		
				return "/menadzerPage.html";		
			else if (konobar || kuvar || sanker)		
				return "/profilZaposlenog.html";		
			else if (admin)		
				return "/adminPage.html";		
			else if (ponudjac)		
				return "/profilPonudjaca.html";		
			else 		
				throw new IllegalStateException();		
	   }		

	 protected void clearAuthenticationAttributes(HttpServletRequest request) {		
	       HttpSession session = request.getSession(false);		
	       if (session == null) {		
	           return;		
	       }		
	       session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);		
	   }		
			
		public RedirectStrategy getRedirectStrategy() {		
			return redirectStrategy;		
		}		
				
		public void setRedirectStrategy(RedirectStrategy redirectStrategy) {		
			this.redirectStrategy = redirectStrategy;		
		}		
}
