package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuConduccion;
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
public class PlaRecuConduccionFacade extends AbstractFacade<PlaRecuConduccion> implements PlaRecuConduccionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuConduccionFacade() {
        super(PlaRecuConduccion.class);
    }
    
    @Override
    public List<PlaRecuConduccion> estadoReg(int estado) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_conduccion WHERE estado_reg = ?1", PlaRecuConduccion.class);
            q.setParameter(1, estado);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos 'Conduccion'");
            return null;
        }
    }
    
    @Override
    public List<PlaRecuConduccion> findAll() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_conduccion WHERE estado_reg = 0", PlaRecuConduccion.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Conduccion");
            return null;
        }
    }
    
    @Override
    public PlaRecuConduccion findByDescripcion(String categoriaDescripcion) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM pla_recu_conduccion WHERE descripcion = ?1 AND estado_reg = 0", PlaRecuConduccion.class);
            q.setParameter(1, categoriaDescripcion);
            return q.getResultList().isEmpty() ? null : (PlaRecuConduccion) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en Facade Planeación Recursos Conduccion");
            return null;
        }
    }

}

