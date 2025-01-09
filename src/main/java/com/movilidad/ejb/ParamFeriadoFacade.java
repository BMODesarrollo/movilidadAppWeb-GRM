/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamFeriado;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Pc
 */
@Stateless
public class ParamFeriadoFacade extends AbstractFacade<ParamFeriado> implements ParamFeriadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamFeriadoFacade() {
        super(ParamFeriado.class);
    }

    @Override
    public List<ParamFeriado> findallEst() {

        try {
            Query q = em.createQuery("SELECT m FROM ParamFeriado m WHERE m.estadoReg = :estadoReg", ParamFeriado.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }

    }
//
//    @Override
//    public ParamFeriado findByFecha(Date fecha) {
//        try {
//            Query q = em.createNativeQuery("select pf.* from param_feriado pf "
//                    + "where pf.fecha = ?1 and pf.estado_reg= 0", ParamFeriado.class);
//            q.setParameter(1, Util.dateFormat(fecha));
//            return (ParamFeriado) q.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//
//    }

    @Override
    public ParamFeriado findByFecha(Date fecha) {
        try {
            Query q = em.createQuery("SELECT m FROM ParamFeriado m WHERE m.estadoReg = :estadoReg and m.fecha= :fecha", ParamFeriado.class);
            q.setParameter("estadoReg", 0);
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return (ParamFeriado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<ParamFeriado> findAllByFechaMes(Date dDesde, Date dHasta) {
        try {
            String sql = "SELECT * "
                    + "FROM param_feriado "
                    + "where fecha between ?1 and  ?2 "
                    + "and estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamFeriado.class);
            q.setParameter(1, Util.dateFormat(dDesde));
            q.setParameter(2, Util.dateFormat(dHasta));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParamFeriado findByFechaAndIdParamFeriado(Date fecha, int idRegistro) {
        try {
            String sql = "SELECT * "
                    + "FROM param_feriado "
                    + "WHERE fecha = ?1 AND "
                    + "id_param_feriado<>?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamFeriado.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idRegistro);
            return (ParamFeriado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
