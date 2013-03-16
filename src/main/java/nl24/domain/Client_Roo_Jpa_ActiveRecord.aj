// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nl24.domain;

import java.util.List;
import nl24.domain.Client;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Client_Roo_Jpa_ActiveRecord {
    
    public static long Client.countClients() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Client o", Long.class).getSingleResult();
    }
    
    public static List<Client> Client.findAllClients() {
        return entityManager().createQuery("SELECT o FROM Client o", Client.class).getResultList();
    }
    
    public static Client Client.findClient(Long id) {
        if (id == null) return null;
        return entityManager().find(Client.class, id);
    }
    
    public static List<Client> Client.findClientEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Client o", Client.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public Client Client.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Client merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
