package org.wildfly.swarm.examples.ds.subsystem;


import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Ken Finnigan
 */
@ApplicationScoped
public class PersistenceHelper {

    @PersistenceContext(unitName = "MyPU")
    private EntityManager em;

    public EntityManager getEntityManager() {
        return em;
    }
}
