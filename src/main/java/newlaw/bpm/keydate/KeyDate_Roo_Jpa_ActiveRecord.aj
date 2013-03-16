// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.keydate;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import newlaw.bpm.keydate.KeyDate;
import org.springframework.transaction.annotation.Transactional;

privileged aspect KeyDate_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager KeyDate.entityManager;
    
    public static final EntityManager KeyDate.entityManager() {
        EntityManager em = new KeyDate() {
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long KeyDate.countKeyDates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM KeyDate o", Long.class).getSingleResult();
    }
    
    public static List<KeyDate> KeyDate.findAllKeyDates() {
        return entityManager().createQuery("SELECT o FROM KeyDate o", KeyDate.class).getResultList();
    }
    
    public static KeyDate KeyDate.findKeyDate(Long id) {
        if (id == null) return null;
        return entityManager().find(KeyDate.class, id);
    }
    
    public static List<KeyDate> KeyDate.findKeyDateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM KeyDate o", KeyDate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void KeyDate.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void KeyDate.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            KeyDate attached = KeyDate.findKeyDate(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void KeyDate.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void KeyDate.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public KeyDate KeyDate.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        KeyDate merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}