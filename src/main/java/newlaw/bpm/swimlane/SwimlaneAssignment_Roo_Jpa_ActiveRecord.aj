// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.swimlane;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import newlaw.bpm.swimlane.SwimlaneAssignment;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SwimlaneAssignment_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager SwimlaneAssignment.entityManager;
    
    public static final EntityManager SwimlaneAssignment.entityManager() {
        EntityManager em = new SwimlaneAssignment() {
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SwimlaneAssignment.countSwimlaneAssignments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SwimlaneAssignment o", Long.class).getSingleResult();
    }
    
    public static List<SwimlaneAssignment> SwimlaneAssignment.findAllSwimlaneAssignments() {
        return entityManager().createQuery("SELECT o FROM SwimlaneAssignment o", SwimlaneAssignment.class).getResultList();
    }
    
    public static SwimlaneAssignment SwimlaneAssignment.findSwimlaneAssignment(Long id) {
        if (id == null) return null;
        return entityManager().find(SwimlaneAssignment.class, id);
    }
    
    public static List<SwimlaneAssignment> SwimlaneAssignment.findSwimlaneAssignmentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SwimlaneAssignment o", SwimlaneAssignment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void SwimlaneAssignment.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SwimlaneAssignment.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SwimlaneAssignment attached = SwimlaneAssignment.findSwimlaneAssignment(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SwimlaneAssignment.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SwimlaneAssignment.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SwimlaneAssignment SwimlaneAssignment.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SwimlaneAssignment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
