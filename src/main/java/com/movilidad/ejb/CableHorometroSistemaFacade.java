/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableHorometroSistema;
import com.movilidad.utils.Util;
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
public class CableHorometroSistemaFacade extends AbstractFacade<CableHorometroSistema> implements CableHorometroSistemaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableHorometroSistemaFacade() {
        super(CableHorometroSistema.class);
    }

    @Override
    public List<CableHorometroSistema> findAllByRangoFecha(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    chs.*\n"
                + "FROM\n"
                + "    cable_horometro_sistema chs\n"
                + "WHERE\n"
                + "    chs.fecha BETWEEN ?1 AND ?2\n"
                + "        AND chs.estado_reg = 0;", CableHorometroSistema.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    @Override
    public CableHorometroSistema findByFecha(Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    chs.*\n"
                    + "FROM\n"
                    + "    cable_horometro_sistema chs\n"
                    + "WHERE\n"
                    + "    chs.fecha = ?1 AND chs.estado_reg = 0;", CableHorometroSistema.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return (CableHorometroSistema) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
