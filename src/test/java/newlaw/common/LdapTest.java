package newlaw;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext*.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class LdapTest {
	
	@Autowired
	private LdapTemplate ldapTemplate;

	public void setLdapTemplate(LdapTemplate ldapTemplate) {
		this.ldapTemplate = ldapTemplate;
	}

	
	@Test 
	public void testLdap() {
	       LDAPSample ldap = new LDAPSample(this.ldapTemplate);
	        if (ldap.setPersonRecord("brendan.poole"))
	        {
	            System.out.println("Organizations: " + ldap.getOrgNames());
	            System.out.println("Users: " + ldap.getUserNames());
	            System.out.println("Full Name: " + ldap.getFullName());
	            System.out.println("Last Name: " + ldap.getLastName());
	            System.out.println("UserID: " + ldap.getUid());
	            System.out.println("Employee Number: " + ldap.getEmployeeNumber());
	        }
	        else
	        {
	            System.out.println("Error");
	        }
	    }

	@Test
	public void getAllPersonNames() {
		List l = ldapTemplate.search("ou=New law", "(ou=*)", new AttributesMapper() {
			public Object mapFromAttributes(Attributes attrs) throws NamingException {
				return attrs.get("cn").get();
			}
		});
		assert(l.size() != 0);
	}
}
