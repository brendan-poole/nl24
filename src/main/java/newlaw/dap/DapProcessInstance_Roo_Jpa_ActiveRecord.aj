// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.dap;

import java.util.List;
import newlaw.dap.DapProcessInstance;

privileged aspect DapProcessInstance_Roo_Jpa_ActiveRecord {
    
    public static long DapProcessInstance.countDapProcessInstances() {
        return entityManager().createQuery("SELECT COUNT(o) FROM DapProcessInstance o", Long.class).getSingleResult();
    }
    
    public static List<DapProcessInstance> DapProcessInstance.findAllDapProcessInstances() {
        return entityManager().createQuery("SELECT o FROM DapProcessInstance o", DapProcessInstance.class).getResultList();
    }
    
    public static DapProcessInstance DapProcessInstance.findDapProcessInstance(Long id) {
        if (id == null) return null;
        return entityManager().find(DapProcessInstance.class, id);
    }
    
    public static List<DapProcessInstance> DapProcessInstance.findDapProcessInstanceEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM DapProcessInstance o", DapProcessInstance.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
