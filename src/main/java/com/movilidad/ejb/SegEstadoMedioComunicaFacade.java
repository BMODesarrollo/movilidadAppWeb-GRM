/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegEstadoMedioComunica;
import com.movilidad.utils.Util;
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
public class SegEstadoMedioComunicaFacade extends AbstractFacade<SegEstadoMedioComunica> implements SegEstadoMedioComunicaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegEstadoMedioComunicaFacade() {
        super(SegEstadoMedioComunica.class);
    }

    @Override
    public List<SegEstadoMedioComunica> findRangoFechaEstadoReg(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    seg_estado_medio_comunica e\n"
                    + "WHERE\n"
                    + "    DATE(e.fecha_hora) BETWEEN ?1 AND ?2;", SegEstadoMedioComunica.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
