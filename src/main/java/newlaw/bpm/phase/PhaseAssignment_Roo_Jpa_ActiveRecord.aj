// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.phase;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import newlaw.bpm.phase.PhaseAssignment;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PhaseAssignment_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PhaseAssignment.entityManager;
    
    public static final EntityManager PhaseAssignment.entityManager() {
        EntityManager em = new PhaseAssignment() {
        }.entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PhaseAssignment.countPhaseAssignments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PhaseAssignment o", Long.class).getSingleResult();
    }
    
    public static List<PhaseAssignment> PhaseAssignment.findAllPhaseAssignments() {
        return entityManager().createQuery("SELECT o FROM PhaseAssignment o", PhaseAssignment.class).getResultList();
    }
    
    public static PhaseAssignment PhaseAssignment.findPhaseAssignment(Long id) {
        if (id == null) return null;
        return entityManager().find(PhaseAssignment.class, id);
    }
    
    public static List<PhaseAssignment> PhaseAssignment.findPhaseAssignmentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PhaseAssignment o", PhaseAssignment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PhaseAssignment.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PhaseAssignment.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PhaseAssignment attached = PhaseAssignment.findPhaseAssignment(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PhaseAssignment.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PhaseAssignment.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PhaseAssignment PhaseAssignment.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PhaseAssignment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}