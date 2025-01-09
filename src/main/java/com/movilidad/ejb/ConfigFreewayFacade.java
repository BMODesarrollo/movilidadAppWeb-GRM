/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigFreeway;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class ConfigFreewayFacade extends AbstractFacade<ConfigFreeway> implements ConfigFreewayFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigFreewayFacade() {
        super(ConfigFreeway.class);
    }

    @Override
    public List<ConfigFreeway> findAllByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    config_freeway\n"
                + "WHERE\n"
                + "    estado_reg = 0 ORDER BY codigo_solucion ASC;", ConfigFreeway.class);
        return q.getResultList();
    }

    @Override
    public ConfigFreeway findByCodSolucion(String codSolucion, Integer idConfigFreeway) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    config_freeway\n"
                    + "WHERE\n"
                    + "    codigo_solucion = ?1 AND id_config_freeway<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", ConfigFreeway.class);
            q.setParameter(1, codSolucion);
            q.setParameter(2, idConfigFreeway);
            return (ConfigFreeway) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConfigFreeway findByIdGopUnidadFuncional(Integer idGopUnidadFuncional, Integer idConfigFreeway) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    config_freeway\n"
                    + "WHERE\n"
                    + "    id_gop_unidad_funcional = ?1 AND id_config_freeway<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", ConfigFreeway.class);
            System.out.println("idGopUnidadFuncional->" + idGopUnidadFuncional);
            q.setParameter(1, idGopUnidadFuncional);
            q.setParameter(2, idConfigFreeway);
            return (ConfigFreeway) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
