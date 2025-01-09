package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuMotivo;
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
public class PlaRecuMotivoFacade extends AbstractFacade<PlaRecuMotivo> implements PlaRecuMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuMotivoFacade() {
        super(PlaRecuMotivo.class);
    }
    
    @Override
    public List<PlaRecuMotivo> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_motivo WHERE estado_reg = ?1", PlaRecuMotivo.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Motivo");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuMotivo> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_motivo WHERE estado_reg = 0", PlaRecuMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Motivo");
            return null;
        }
    }
    
    @Override
    public PlaRecuMotivo findByName(String name) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_motivo WHERE motivo = ?1 AND estado_reg = 0", PlaRecuMotivo.class);
            q.setParameter(1, name);
            return q.getResultList().isEmpty() ? null : (PlaRecuMotivo) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Motivo'");
            return null;
        }
    }

}
