package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuTurno;
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
public class PlaRecuTurnoFacade extends AbstractFacade<PlaRecuTurno> implements PlaRecuTurnoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuTurnoFacade() {
        super(PlaRecuTurno.class);
    }
    
    @Override
    public List<PlaRecuTurno> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_turno WHERE estado_reg = ?1", PlaRecuTurno.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Turno'");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuTurno> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_turno WHERE estado_reg = 0", PlaRecuTurno.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Turno'");
            return null;
        }
    }
    
    @Override
    public PlaRecuTurno findByName(String turnoName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_turno WHERE turno = ?1 AND estado_reg = 0", PlaRecuTurno.class);
            q.setParameter(1, turnoName);
            return q.getResultList().isEmpty() ? null : (PlaRecuTurno) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Turno'");
            return null;
        }
    }

}

