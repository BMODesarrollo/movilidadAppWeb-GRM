/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConfigControlJornada;
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
public class ConfigControlJornadaFacade extends AbstractFacade<ConfigControlJornada> implements ConfigControlJornadaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConfigControlJornadaFacade() {
        super(ConfigControlJornada.class);
    }

    @Override
    public List<ConfigControlJornada> findAllByEstadoRegActivo() {
        String sql = "SELECT * FROM config_control_jornada WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, ConfigControlJornada.class);
        return q.getResultList();
    }

}