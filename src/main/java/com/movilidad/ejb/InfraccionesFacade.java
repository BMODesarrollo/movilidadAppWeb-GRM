package com.movilidad.ejb;

import com.movilidad.model.Infracciones;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class InfraccionesFacade extends AbstractFacade<Infracciones> implements InfraccionesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public InfraccionesFacade() {
        super(Infracciones.class);
    }

    @Override
    public List<Infracciones> findAll() {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos_infracciones\n"
                    + "WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, Infracciones.class);
            
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<Infracciones> findAllByDateRangeAndEstadoReg(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos_infracciones\n"
                    + "WHERE\n"
                    + "    DATE(fecha_identificacion) BETWEEN ?1 AND ?2\n"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, Infracciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Infracciones> findAllByDateRangeAndArea(Date desde, Date hasta, int idInfraccionesParamArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    hallazgos_infracciones\n"
                    + "WHERE\n"
                    + "    DATE(fecha_identificacion) BETWEEN ?1 AND ?2\n"
                    + "        AND id_infracciones_param_area = ?3"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, Infracciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idInfraccionesParamArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Infracciones findByIdNovedad(int idNovedad) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "n.*\n"
                    + "FROM\n"
                    + "hallazgos_infracciones n\n"
                    + "WHERE\n"
                    + "n.id_novedad = ?1\n"
                    + "AND n.estado_reg = 0\n",Infracciones.class);
            q.setParameter(1, idNovedad);
            return (Infracciones) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Infracciones findByIdICO(int idICO) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "n.*\n"
                    + "FROM\n"
                    + "hallazgos_infracciones n\n"
                    + "WHERE\n"
                    + "n.id_ICO = ?1\n"
                    + "AND n.estado_reg = 0\n",Infracciones.class);
            q.setParameter(1, idICO);
            return (Infracciones) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}

