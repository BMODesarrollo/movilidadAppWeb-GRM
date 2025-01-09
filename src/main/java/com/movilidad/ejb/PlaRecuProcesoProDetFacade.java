package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProcesoProDet;
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
public class PlaRecuProcesoProDetFacade extends AbstractFacade<PlaRecuProcesoProDet> implements PlaRecuProcesoProDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuProcesoProDetFacade() {
        super(PlaRecuProcesoProDet.class);
    }
    
    @Override
    public List<PlaRecuProcesoProDet> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro_det WHERE estado_reg = ?1", PlaRecuProcesoProDet.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuProcesoProDet> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro_det WHERE estado_reg = 0 order by id_pla_recu_proceso_pro ", PlaRecuProcesoProDet.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación Det");
            return null;
        }
    }
    
    @Override
    public PlaRecuProcesoProDet findByDescripcion(String descripcion) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro_det WHERE descripcion = ?1 AND estado_reg = 0", PlaRecuProcesoProDet.class);
            q.setParameter(1, descripcion);
            return (PlaRecuProcesoProDet) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación");
            return null;
        }
    }

}

