package newlaw.identities;

import java.util.Set;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface UserDetailsService {

	public abstract UserDetails getUserDetails();

	public abstract boolean hasAuthority(String role);

	Set<String> getUsersAuthorities();

}