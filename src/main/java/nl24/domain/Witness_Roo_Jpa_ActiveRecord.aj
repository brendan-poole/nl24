// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl24.domain.Witness;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Witness_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Witness.entityManager;
    
    public static final EntityManager Witness.entityManager() {
        EntityManager em = new Witness().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Witness.countWitnesses() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Witness o", Long.class).getSingleResult();
    }
    
    public static List<Witness> Witness.findAllWitnesses() {
        return entityManager().createQuery("SELECT o FROM Witness o", Witness.class).getResultList();
    }
    
    public static Witness Witness.findWitness(Long id) {
        if (id == null) return null;
        return entityManager().find(Witness.class, id);
    }
    
    public static List<Witness> Witness.findWitnessEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Witness o", Witness.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Witness.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Witness.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Witness attached = Witness.findWitness(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Witness.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Witness.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Witness Witness.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Witness merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
