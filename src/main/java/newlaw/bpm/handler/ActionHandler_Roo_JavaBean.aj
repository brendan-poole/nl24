// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.bpm.handler;

import newlaw.bpm.handler.ActionHandler;
import newlaw.bpm.phase.Phase;
import org.apache.commons.logging.Log;

privileged aspect ActionHandler_Roo_JavaBean {
    
    public Log ActionHandler.getLOG() {
        return this.LOG;
    }
    
    public Phase ActionHandler.getPhase() {
        return this.phase;
    }
    
    public void ActionHandler.setPhase(Phase phase) {
        this.phase = phase;
    }
    
}