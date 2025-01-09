/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmTamplitudHoras;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class PmTamplitudHorasFacade extends AbstractFacade<PmTamplitudHoras> implements PmTamplitudHorasFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmTamplitudHorasFacade() {
        super(PmTamplitudHoras.class);
    }

    @Override
    public List<PmTamplitudHoras> cargarEstadoRegistro() {
        try {
            Query q = em.createQuery("SELECT p FROM PmTamplitudHoras p WHERE p.estadoReg = :estadoReg", PmTamplitudHoras.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PmTamplitudHoras findByFecha(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?3\n";
            String sql = "select * from pm_tamplitud_horas where desde <= ?1 and hasta >= ?2"
                    + sql_unida_func
                    + ";";

            Query query = em.createNativeQuery(sql, PmTamplitudHoras.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idGopUnidadFuncional);

            return (PmTamplitudHoras) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
