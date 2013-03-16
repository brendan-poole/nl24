// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.propertychangetracker;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import newlaw.propertychangetracker.PropertyChange;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PropertyChange_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PropertyChange.entityManager;
    
    public static final EntityManager PropertyChange.entityManager() {
        EntityManager em = new PropertyChange().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PropertyChange.countPropertyChanges() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PropertyChange o", Long.class).getSingleResult();
    }
    
    public static List<PropertyChange> PropertyChange.findAllPropertyChanges() {
        return entityManager().createQuery("SELECT o FROM PropertyChange o", PropertyChange.class).getResultList();
    }
    
    public static PropertyChange PropertyChange.findPropertyChange(Long id) {
        if (id == null) return null;
        return entityManager().find(PropertyChange.class, id);
    }
    
    public static List<PropertyChange> PropertyChange.findPropertyChangeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PropertyChange o", PropertyChange.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PropertyChange.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PropertyChange.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PropertyChange attached = PropertyChange.findPropertyChange(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PropertyChange.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PropertyChange.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PropertyChange PropertyChange.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PropertyChange merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
