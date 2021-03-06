// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.bpm;

import java.util.List;
import nl24.bpm.Nl24PhaseAssignment;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Nl24PhaseAssignment_Roo_Jpa_ActiveRecord {
    
    public static long Nl24PhaseAssignment.countNl24PhaseAssignments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Nl24PhaseAssignment o", Long.class).getSingleResult();
    }
    
    public static List<Nl24PhaseAssignment> Nl24PhaseAssignment.findAllNl24PhaseAssignments() {
        return entityManager().createQuery("SELECT o FROM Nl24PhaseAssignment o", Nl24PhaseAssignment.class).getResultList();
    }
    
    public static Nl24PhaseAssignment Nl24PhaseAssignment.findNl24PhaseAssignment(Long id) {
        if (id == null) return null;
        return entityManager().find(Nl24PhaseAssignment.class, id);
    }
    
    public static List<Nl24PhaseAssignment> Nl24PhaseAssignment.findNl24PhaseAssignmentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Nl24PhaseAssignment o", Nl24PhaseAssignment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Nl24PhaseAssignment Nl24PhaseAssignment.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Nl24PhaseAssignment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
