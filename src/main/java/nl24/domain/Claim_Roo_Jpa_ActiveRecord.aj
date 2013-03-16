// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.domain.Claim;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Claim_Roo_Jpa_ActiveRecord {
    
    public static long Claim.countClaims() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Claim o", Long.class).getSingleResult();
    }
    
    public static List<Claim> Claim.findAllClaims() {
        return entityManager().createQuery("SELECT o FROM Claim o", Claim.class).getResultList();
    }
    
    public static Claim Claim.findClaim(Long id) {
        if (id == null) return null;
        return entityManager().find(Claim.class, id);
    }
    
    public static List<Claim> Claim.findClaimEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Claim o", Claim.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Claim Claim.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Claim merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
