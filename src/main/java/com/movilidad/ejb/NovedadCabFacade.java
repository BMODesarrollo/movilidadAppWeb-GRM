/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoBano;
import com.movilidad.model.NovedadCab;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class NovedadCabFacade extends AbstractFacade<NovedadCab> implements NovedadCabFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadCabFacade() {
        super(NovedadCab.class);
    }

    @Override
    public List<NovedadCab> findEstadoReg(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ab.*\n"
                    + "FROM\n"
                    + "    novedad_cab ab\n"
                    + "WHERE\n"
                    + "    DATE(ab.fecha_hora) BETWEEN DATE(?1) AND DATE(?2)\n"
                    + "        AND ab.estado_reg = 0;", NovedadCab.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
