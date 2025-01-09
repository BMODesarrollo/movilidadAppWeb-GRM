package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuRuta;
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
public class PlaRecuRutaFacade extends AbstractFacade<PlaRecuRuta> implements PlaRecuRutaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuRutaFacade() {
        super(PlaRecuRuta.class);
    }

    @Override
    public PlaRecuRuta findById(String id) {
        try {
            String sql = "SELECT * FROM pla_recu_ruta WHERE estado_reg = 0 AND id_pla_recu_ruta = ?1";
            Query query = em.createNativeQuery(sql, PlaRecuRuta.class);
            query.setParameter(1, id);
            return (PlaRecuRuta) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public PlaRecuRuta findByRuta(String ruta) {
        try {
            String sql = "SELECT * FROM pla_recu_ruta WHERE estado_reg = 0 AND ruta = ?1";
            Query query = em.createNativeQuery(sql, PlaRecuRuta.class);
            query.setParameter(1, ruta);
            return query.getResultList().isEmpty() ? null : (PlaRecuRuta) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuRuta> findAll() {
        try {
            String sql = "SELECT * FROM pla_recu_ruta WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuRuta.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}

