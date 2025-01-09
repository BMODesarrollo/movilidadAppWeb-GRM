/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoParamArea;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class AseoParamAreaFacade extends AbstractFacade<AseoParamArea> implements AseoParamAreaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoParamAreaFacade() {
        super(AseoParamArea.class);
    }

    @Override
    public AseoParamArea findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM aseo_param_area where id_aseo_param_area <> ?1 and codigo = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AseoParamArea.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, codigo);

            return (AseoParamArea) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AseoParamArea findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM aseo_param_area where id_aseo_param_area <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AseoParamArea.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (AseoParamArea) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AseoParamArea> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AseoParamArea.findByEstadoReg", AseoParamArea.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public AseoParamArea findByHashString(String hashString) {
        try {
            String sql = "SELECT \n"
                    + "    apa.*\n"
                    + "FROM\n"
                    + "    aseo_param_area apa\n"
                    + "WHERE\n"
                    + "    apa.hash_string = ?1\n"
                    + "        AND apa.estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, AseoParamArea.class);
            query.setParameter(1, hashString);

            return (AseoParamArea) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AseoParamArea findByCodigo(String codigo) {
        try {
            String sql = "SELECT * FROM aseo_param_area where codigo = ?1 and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, AseoParamArea.class);
            query.setParameter(1, codigo);

            return (AseoParamArea) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
