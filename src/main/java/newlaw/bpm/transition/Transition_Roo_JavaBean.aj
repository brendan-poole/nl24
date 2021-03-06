// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.transition;

import java.util.List;
import newlaw.bpm.handler.DueDateHandler;
import newlaw.bpm.phase.Phase;
import newlaw.bpm.transition.Transition;
import org.joda.time.Period;

privileged aspect Transition_Roo_JavaBean {
    
    public String Transition.getCode() {
        return this.code;
    }
    
    public void Transition.setCode(String code) {
        this.code = code;
    }
    
    public String Transition.getName() {
        return this.name;
    }
    
    public void Transition.setName(String name) {
        this.name = name;
    }
    
    public Boolean Transition.getEmail() {
        return this.email;
    }
    
    public void Transition.setEmail(Boolean email) {
        this.email = email;
    }
    
    public void Transition.setPhase(Phase phase) {
        this.phase = phase;
    }
    
    public List<Phase> Transition.getToPhase() {
        return this.toPhase;
    }
    
    public void Transition.setToPhase(List<Phase> toPhase) {
        this.toPhase = toPhase;
    }
    
    public String Transition.getToPhaseCode() {
        return this.toPhaseCode;
    }
    
    public void Transition.setToPhaseCode(String toPhaseCode) {
        this.toPhaseCode = toPhaseCode;
    }
    
    public String Transition.getPeriod() {
        return this.period;
    }
    
    public void Transition.setPeriod(String period) {
        this.period = period;
    }
    
    public String Transition.getInvalidatePhases() {
        return this.invalidatePhases;
    }
    
    public void Transition.setInvalidatePhases(String invalidatePhases) {
        this.invalidatePhases = invalidatePhases;
    }
    
    public String Transition.getKillPhases() {
        return this.killPhases;
    }
    
    public void Transition.setKillPhases(String killPhases) {
        this.killPhases = killPhases;
    }
    
    public void Transition.setPeriods(List<Period> periods) {
        this.periods = periods;
    }
    
    public List<DueDateHandler> Transition.getDueDateHandlers() {
        return this.dueDateHandlers;
    }
    
    public void Transition.setDueDateHandlers(List<DueDateHandler> dueDateHandlers) {
        this.dueDateHandlers = dueDateHandlers;
    }
    
}
