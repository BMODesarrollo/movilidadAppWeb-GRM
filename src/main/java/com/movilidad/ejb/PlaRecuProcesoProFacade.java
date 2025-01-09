package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuProcesoPro;
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
public class PlaRecuProcesoProFacade extends AbstractFacade<PlaRecuProcesoPro> implements PlaRecuProcesoProFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuProcesoProFacade() {
        super(PlaRecuProcesoPro.class);
    }
    
    @Override
    public List<PlaRecuProcesoPro> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro WHERE estado_reg = ?1", PlaRecuProcesoPro.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuProcesoPro> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro WHERE estado_reg = 0", PlaRecuProcesoPro.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación");
            return null;
        }
    }
    
    @Override
    public PlaRecuProcesoPro findByDescripcion(String categoriaName) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_proceso_pro WHERE descripcion = ?1 AND estado_reg = 0", PlaRecuProcesoPro.class);
            q.setParameter(1, categoriaName);
            return (PlaRecuProcesoPro) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Proceso Programación");
            return null;
        }
    }

}

