// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.bpm;

import javax.persistence.EntityManager;
import nl24.bpm.Nl24ProcessInstanceService;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

privileged aspect Nl24ProcessInstanceService_Roo_JavaBean {
    
    public EntityManager Nl24ProcessInstanceService.getEm() {
        return this.em;
    }
    
    public void Nl24ProcessInstanceService.setEm(EntityManager em) {
        this.em = em;
    }
    
    public LocalValidatorFactoryBean Nl24ProcessInstanceService.getValidator() {
        return this.validator;
    }
    
    public void Nl24ProcessInstanceService.setValidator(LocalValidatorFactoryBean validator) {
        this.validator = validator;
    }
    
    public String Nl24ProcessInstanceService.getRepairersCoreHoursStart() {
        return this.repairersCoreHoursStart;
    }
    
    public void Nl24ProcessInstanceService.setRepairersCoreHoursStart(String repairersCoreHoursStart) {
        this.repairersCoreHoursStart = repairersCoreHoursStart;
    }
    
    public String Nl24ProcessInstanceService.getRepairersCoreHoursEnd() {
        return this.repairersCoreHoursEnd;
    }
    
    public void Nl24ProcessInstanceService.setRepairersCoreHoursEnd(String repairersCoreHoursEnd) {
        this.repairersCoreHoursEnd = repairersCoreHoursEnd;
    }
    
}
