package newlaw;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Service;

public class LDAPSample
{

	public LDAPSample(LdapTemplate lt) {
		this.ldapTemplate = lt;
	}
    public LdapTemplate ldapTemplate;

    private Person person;
    
    
    private static class PersonContextMapper implements ContextMapper
    {
        public Object mapFromContext(Object ctx)
        {
            DirContextAdapter context = (DirContextAdapter) ctx;
            Person p = new Person();
            p.setFullName(context.getStringAttribute("cn"));
            p.setLastName(context.getStringAttribute("sn"));
            p.setUid(context.getStringAttribute("sAMAccountName"));
            p.setEmployeeNumber(context.getStringAttribute("employeeID"));
            return p;
        }
    }

    public Person findByPrimaryKey()
    {
        // String name, String company, String country) {
        String dn = "cn=john doe,cn=Users";
        return (Person) ldapTemplate.lookup(dn, new PersonContextMapper());
    }

    public boolean setPersonRecord(String uid) 
    {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass","person"));
        andFilter.and(new EqualsFilter("sAMAccountName",uid));
        List<Person> people = ldapTemplate.search("", andFilter.encode(),new PersonContextMapper());
        if (people.size() > 0)
        {
            person = people.get(0);
            return true;
        }
        return false;
    }

    public List getOrgNames()
    {
        return ldapTemplate.list("");
    }

    public List getUserNames()
    {
        return ldapTemplate.list("cn=Users");
    }

    public String getFullName()
    {
        //return findByPrimaryKey().getFullName();
        return person.getFullName();
    }

    public String getLastName()
    {

        //return findByPrimaryKey().getLastName();
        return person.getLastName();
    }

    public String getUid()
    {
        //return findByPrimaryKey().getUid();
        return person.getUid();
    }

    public String getEmployeeNumber()
    {
        //return findByPrimaryKey().getEmployeeNumber();
        return person.getEmployeeNumber();
    }
    
    public void setLdapTemplate(LdapTemplate ldapTemplate) {
    	this.ldapTemplate = ldapTemplate;
    }
}