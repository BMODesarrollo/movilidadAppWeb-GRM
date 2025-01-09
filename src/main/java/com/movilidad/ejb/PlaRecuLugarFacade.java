package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuLugar;
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
public class PlaRecuLugarFacade extends AbstractFacade<PlaRecuLugar> implements PlaRecuLugarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PlaRecuLugarFacade() {
        super(PlaRecuLugar.class);
    }

    @Override
    public PlaRecuLugar findById(String id) {
        try {
            String sql = "SELECT * FROM pla_recu_lugar WHERE estado_reg = 0 AND id_lugar = ?1";
            Query query = em.createNativeQuery(sql, PlaRecuLugar.class);
            query.setParameter(1, id);
            return (PlaRecuLugar) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public PlaRecuLugar findByLugar(String lugar) {
        try {
            String sql = "SELECT * FROM pla_recu_lugar WHERE estado_reg = 0 AND lugar = ?1";
            Query query = em.createNativeQuery(sql, PlaRecuLugar.class);
            query.setParameter(1, lugar);
            return query.getResultList().isEmpty() ? null : (PlaRecuLugar) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuLugar> findAll() {
        try {
            String sql = "SELECT * FROM pla_recu_lugar WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuLugar.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuLugar> findAllByArea(int idInfraccionesParamArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_lugar\n"
                    + "WHERE\n"
                    + "   id_param_area = ?1"
                    + "   AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuLugar.class);
            query.setParameter(1, idInfraccionesParamArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}

