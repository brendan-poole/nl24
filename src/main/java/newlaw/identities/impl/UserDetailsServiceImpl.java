package newlaw.identities.impl;

import java.util.HashSet;
import java.util.Set;

import newlaw.identities.UserDetailsService;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;



public class UserDetailsServiceImpl implements UserDetailsService {

	@Override
	public UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		UserDetails userDetails = null;
		if (principal instanceof UserDetails) {
			userDetails = (UserDetails) principal;
		}
		return userDetails;
	}
	
	@Override
	public boolean hasAuthority(String role) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for(GrantedAuthority ga : auth.getAuthorities()) {
			if (ga.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<String> getUsersAuthorities() {
		Set<String> as = new HashSet<String>();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		for(GrantedAuthority ga : auth.getAuthorities()) {
			as.add(ga.toString());
		}
		return as;
	}
}