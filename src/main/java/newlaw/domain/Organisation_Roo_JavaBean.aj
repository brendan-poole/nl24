// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package newlaw.domain;

import newlaw.domain.Organisation;

privileged aspect Organisation_Roo_JavaBean {
    
    public void Organisation.setName(String name) {
        this.name = name;
    }
    
    public String Organisation.getContactName() {
        return this.contactName;
    }
    
    public void Organisation.setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public String Organisation.getUserGroup() {
        return this.userGroup;
    }
    
    public void Organisation.setUserGroup(String userGroup) {
        this.userGroup = userGroup;
    }
    
    public String Organisation.getTelephone() {
        return this.telephone;
    }
    
    public void Organisation.setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
}