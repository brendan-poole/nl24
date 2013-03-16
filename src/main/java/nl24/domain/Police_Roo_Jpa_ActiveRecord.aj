// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.domain.Police;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Police_Roo_Jpa_ActiveRecord {
    
    public static long Police.countPolices() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Police o", Long.class).getSingleResult();
    }
    
    public static List<Police> Police.findAllPolices() {
        return entityManager().createQuery("SELECT o FROM Police o", Police.class).getResultList();
    }
    
    public static Police Police.findPolice(Long id) {
        if (id == null) return null;
        return entityManager().find(Police.class, id);
    }
    
    public static List<Police> Police.findPoliceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Police o", Police.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Police Police.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Police merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}