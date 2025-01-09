/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigEmpresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class ConfigEmpresaFacade extends AbstractFacade<ConfigEmpresa> implements ConfigEmpresaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigEmpresaFacade() {
        super(ConfigEmpresa.class);
    }

    @Override
    public List<ConfigEmpresa> findEstadoReg() {
        try {
            String sql = "SELECT * FROM config_empresa WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, ConfigEmpresa.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConfigEmpresa findByLlave(String key) {
        try {
            String sql = "SELECT * FROM config_empresa WHERE llave = ?1 AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ConfigEmpresa.class);
            q.setParameter(1, key);
            return (ConfigEmpresa) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
