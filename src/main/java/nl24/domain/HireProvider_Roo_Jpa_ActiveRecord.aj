// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.domain.HireProvider;
import org.springframework.transaction.annotation.Transactional;

privileged aspect HireProvider_Roo_Jpa_ActiveRecord {
    
    public static long HireProvider.countHireProviders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM HireProvider o", Long.class).getSingleResult();
    }
    
    public static List<HireProvider> HireProvider.findAllHireProviders() {
        return entityManager().createQuery("SELECT o FROM HireProvider o", HireProvider.class).getResultList();
    }
    
    public static HireProvider HireProvider.findHireProvider(Long id) {
        if (id == null) return null;
        return entityManager().find(HireProvider.class, id);
    }
    
    public static List<HireProvider> HireProvider.findHireProviderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM HireProvider o", HireProvider.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public HireProvider HireProvider.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        HireProvider merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
