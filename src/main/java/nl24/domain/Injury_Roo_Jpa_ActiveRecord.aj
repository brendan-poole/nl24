// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl24.domain.Injury;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Injury_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Injury.entityManager;
    
    public static final EntityManager Injury.entityManager() {
        EntityManager em = new Injury().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Injury.countInjurys() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Injury o", Long.class).getSingleResult();
    }
    
    public static List<Injury> Injury.findAllInjurys() {
        return entityManager().createQuery("SELECT o FROM Injury o", Injury.class).getResultList();
    }
    
    public static Injury Injury.findInjury(Long id) {
        if (id == null) return null;
        return entityManager().find(Injury.class, id);
    }
    
    public static List<Injury> Injury.findInjuryEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Injury o", Injury.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Injury.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Injury.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Injury attached = Injury.findInjury(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Injury.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Injury.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Injury Injury.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Injury merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
