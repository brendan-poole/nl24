package newlaw.identities;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface LdapService {

	public abstract User findUser(String user) throws IdentitiesException;

	public abstract List<User> findUsersBySubstring(String pattern);

}