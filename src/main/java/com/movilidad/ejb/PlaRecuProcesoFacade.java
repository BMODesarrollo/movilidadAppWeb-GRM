package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProceso;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar Beltrán
 */
@Stateless
public class PlaRecuProcesoFacade extends AbstractFacade<PlaRecuProceso> implements PlaRecuProcesoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuProcesoFacade() {
        super(PlaRecuProceso.class);
    }
    
    @Override
    public List<PlaRecuProceso> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso WHERE estado_reg = ?1", PlaRecuProceso.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Proceso'");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuProceso> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso WHERE estado_reg = 0", PlaRecuProceso.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Proceso'");
            return null;
        }
    }
    
    @Override
    public PlaRecuProceso findByName(String procesoName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso WHERE proceso = ?1 AND estado_reg = 0", PlaRecuProceso.class);
            q.setParameter(1, procesoName);
            return q.getResultList().isEmpty() ? null : (PlaRecuProceso) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Proceso'");
            return null;
        }
    }

}