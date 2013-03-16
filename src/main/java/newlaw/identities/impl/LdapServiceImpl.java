package newlaw.identities.impl;

import java.util.ArrayList;
import java.util.List;

import newlaw.identities.IdentitiesException;
import newlaw.identities.LdapService;
import newlaw.identities.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.LikeFilter;
import org.springframework.stereotype.Service;

@Service
public class LdapServiceImpl implements LdapService {

	@Autowired
	private LdapTemplate ldapTemplate;

    private static class UserContextMapper implements ContextMapper
    {
        public Object mapFromContext(Object ctx)
        {
            DirContextAdapter context = (DirContextAdapter) ctx;
            User p = new User();
            p.setFirstName(context.getStringAttribute("givenName"));
            p.setLastName(context.getStringAttribute("sn"));
            p.setUserName(context.getStringAttribute("sAMAccountName"));
            p.setEmail(context.getStringAttribute("mail"));
            //p.setEmployeeNumber(context.getStringAttribute("employeeID"));
            return p;
        }
    }


	/* (non-Javadoc)
	 * @see newlaw.identities.LdapService#findUser(java.lang.String)
	 */
	@Override
	public User findUser(String user) throws IdentitiesException {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass","person"));
        andFilter.and(new EqualsFilter("sAMAccountName",user));
        List<User> users = ldapTemplate.search("", andFilter.encode(),new UserContextMapper());
        if(users.size() != 1) {
        	throw new IdentitiesException("Expected one result but got "+users.size());
        }
		return users.get(0);
	}


	/* (non-Javadoc)
	 * @see newlaw.identities.LdapService#findUsersBySubstring(java.lang.String)
	 */
	@Override
	public List<User> findUsersBySubstring(String pattern) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectcategory","person"));
        andFilter.and(new LikeFilter("cn","*"+pattern+"*")); // LikeFilter allows *
        List<User> users = ldapTemplate.search("", andFilter.encode(),new UserContextMapper());
        //TODO: investigate why some users are returned with a blank username
        List<User> retUsers = new ArrayList<User>();
        for(User u : users) {
        	if(u.getUserName() != null && !u.getUserName().isEmpty()) {
        		retUsers.add(u);
        	}
        }
		return retUsers;
	}

}
