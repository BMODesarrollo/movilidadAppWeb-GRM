/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPrgJornada;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class GenericaPrgJornadaFacade extends AbstractFacade<GenericaPrgJornada> implements GenericaPrgJornadaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPrgJornadaFacade() {
        super(GenericaPrgJornada.class);
    }

    @Override
    public List<GenericaPrgJornada> findAllEstadoRegByFecha(Integer idParamArea, Date dDesde, Date dHasta) {
        try {
            String sql = "select * \n"
                    + "from generica_prg_jornada\n"
                    + "where fecha between ?1 and ?2\n"
                    + "and id_param_area = ?3 \n"
                    + "and estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaPrgJornada.class);
            q.setParameter(1, Util.dateFormat(dDesde));
            q.setParameter(2, Util.dateFormat(dHasta));
            q.setParameter(3, idParamArea);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
