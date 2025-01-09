package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuModalidad;
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
public class PlaRecuModalidadFacade extends AbstractFacade<PlaRecuModalidad> implements PlaRecuModalidadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuModalidadFacade() {
        super(PlaRecuModalidad.class);
    }
    
    @Override
    public List<PlaRecuModalidad> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_modalidad WHERE estado_reg = ?1", PlaRecuModalidad.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Modalidad");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuModalidad> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_modalidad WHERE estado_reg = 0", PlaRecuModalidad.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Modalidad");
            return null;
        }
    }
    
    @Override
    public PlaRecuModalidad findByName(String modalidadName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_modalidad WHERE modalidad = ?1 AND estado_reg = 0", PlaRecuModalidad.class);
            q.setParameter(1, modalidadName);
            return q.getResultList().isEmpty() ? null : (PlaRecuModalidad) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Modalidad");
            return null;
        }
    }

}