// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nl24.domain.SolicitorInstruction;
import org.springframework.transaction.annotation.Transactional;

privileged aspect SolicitorInstruction_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager SolicitorInstruction.entityManager;
    
    public static final EntityManager SolicitorInstruction.entityManager() {
        EntityManager em = new SolicitorInstruction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long SolicitorInstruction.countSolicitorInstructions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM SolicitorInstruction o", Long.class).getSingleResult();
    }
    
    public static List<SolicitorInstruction> SolicitorInstruction.findAllSolicitorInstructions() {
        return entityManager().createQuery("SELECT o FROM SolicitorInstruction o", SolicitorInstruction.class).getResultList();
    }
    
    public static SolicitorInstruction SolicitorInstruction.findSolicitorInstruction(Long id) {
        if (id == null) return null;
        return entityManager().find(SolicitorInstruction.class, id);
    }
    
    public static List<SolicitorInstruction> SolicitorInstruction.findSolicitorInstructionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM SolicitorInstruction o", SolicitorInstruction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void SolicitorInstruction.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void SolicitorInstruction.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            SolicitorInstruction attached = SolicitorInstruction.findSolicitorInstruction(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void SolicitorInstruction.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void SolicitorInstruction.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public SolicitorInstruction SolicitorInstruction.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        SolicitorInstruction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
