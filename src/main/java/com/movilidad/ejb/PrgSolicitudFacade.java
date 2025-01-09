/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitud;
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
public class PrgSolicitudFacade extends AbstractFacade<PrgSolicitud> implements PrgSolicitudFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudFacade() {
        super(PrgSolicitud.class);
    }

    @Override
    public List<PrgSolicitud> findByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PrgSolicitud.findByEstadoReg", PrgSolicitud.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgSolicitud> findByDateRangeAndUnidadFuncional(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	prg_solicitud\n"
                    + "where\n"
                    + "	fecha_solicitud BETWEEN ?1 AND ?2\n"
                    + sql_unida_func
                    + "	and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, PrgSolicitud.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIni)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter(3, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
