// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.domain;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import newlaw.domain.Organisation;

privileged aspect Organisation_Roo_Finder {
    
    public static TypedQuery<Organisation> Organisation.findOrganisationsByUserGroupEquals(String userGroup) {
        if (userGroup == null || userGroup.length() == 0) throw new IllegalArgumentException("The userGroup argument is required");
        EntityManager em = Organisation.entityManager();
        TypedQuery<Organisation> q = em.createQuery("SELECT o FROM Organisation AS o WHERE o.userGroup = :userGroup", Organisation.class);
        q.setParameter("userGroup", userGroup);
        return q;
    }
    
}