/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMtto;
import java.util.ArrayList;
import java.util.Date;
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
public class NovedadMttoFacade extends AbstractFacade<NovedadMtto> implements NovedadMttoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoFacade() {
        super(NovedadMtto.class);
    }

    @Override
    public List<NovedadMtto> findRanfoFechaEstadoReg(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    novedad_mtto n\n"
                    + "WHERE\n"
                    + "   DATE(n.fecha_hora_nov) BETWEEN ?1 AND ?2\n"
                    + "        AND estado_reg = 0;", NovedadMtto.class);
            q.setParameter(1, desde);
            q.setParameter(2, hasta);
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
