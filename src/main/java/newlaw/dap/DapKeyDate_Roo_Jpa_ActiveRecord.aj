// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.dap;

import java.util.List;
import newlaw.dap.DapKeyDate;
import org.springframework.transaction.annotation.Transactional;

privileged aspect DapKeyDate_Roo_Jpa_ActiveRecord {
    
    public static long DapKeyDate.countDapKeyDates() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DapKeyDate o", Long.class).getSingleResult();
    }
    
    public static List<DapKeyDate> DapKeyDate.findAllDapKeyDates() {
        return entityManager().createQuery("SELECT o FROM DapKeyDate o", DapKeyDate.class).getResultList();
    }
    
    public static DapKeyDate DapKeyDate.findDapKeyDate(Long id) {
        if (id == null) return null;
        return entityManager().find(DapKeyDate.class, id);
    }
    
    public static List<DapKeyDate> DapKeyDate.findDapKeyDateEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DapKeyDate o", DapKeyDate.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public DapKeyDate DapKeyDate.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        DapKeyDate merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
