// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.hqltesting;

import newlaw.hqltesting.HqlTestCommand;

privileged aspect HqlTestCommand_Roo_JavaBean {
    
    public String HqlTestCommand.getQuery() {
        return this.query;
    }
    
    public void HqlTestCommand.setQuery(String query) {
        this.query = query;
    }
    
}
