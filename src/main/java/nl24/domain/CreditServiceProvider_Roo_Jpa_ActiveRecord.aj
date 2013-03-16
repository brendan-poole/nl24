// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.domain.CreditServiceProvider;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CreditServiceProvider_Roo_Jpa_ActiveRecord {
    
    public static long CreditServiceProvider.countCreditServiceProviders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CreditServiceProvider o", Long.class).getSingleResult();
    }
    
    public static List<CreditServiceProvider> CreditServiceProvider.findAllCreditServiceProviders() {
        return entityManager().createQuery("SELECT o FROM CreditServiceProvider o", CreditServiceProvider.class).getResultList();
    }
    
    public static CreditServiceProvider CreditServiceProvider.findCreditServiceProvider(Long id) {
        if (id == null) return null;
        return entityManager().find(CreditServiceProvider.class, id);
    }
    
    public static List<CreditServiceProvider> CreditServiceProvider.findCreditServiceProviderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CreditServiceProvider o", CreditServiceProvider.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public CreditServiceProvider CreditServiceProvider.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CreditServiceProvider merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}