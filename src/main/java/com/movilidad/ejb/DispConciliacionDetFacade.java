/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacionDet;
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
public class DispConciliacionDetFacade extends AbstractFacade<DispConciliacionDet> implements DispConciliacionDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispConciliacionDetFacade() {
        super(DispConciliacionDet.class);
    }

    @Override
    public List<DispConciliacionDet> obtenerDetalles(Date fecha, Integer idGopUnidadFuncional) {

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " WHERE cdf.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    cdf.*\n"
                + "FROM\n"
                + "    conciliacion_detalle_flota_rigel cdf\n"
                + sql_unida_func;
        Query query = em.createNativeQuery(sql, DispConciliacionDet.class);
//            query.setParameter(1, Util.dateTimeFormat(fecha));
        query.setParameter(2, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<DispConciliacionDet> obtenerDetallesByIdDispConciliacion(int idDispConciliacion, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND v.id_gop_unidad_funcional = ?2\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    d.*\n"
                + "FROM\n"
                + "    disp_conciliacion_det d\n"
                + "        INNER JOIN\n"
                + "    vehiculo v ON d.id_vehiculo = v.id_vehiculo\n"
                + "WHERE\n"
                + "    d.id_disp_conciliacion = ?1\n"
                + "        AND d.estado_reg = 0\n"
                + sql_unida_func
                + "ORDER BY v.codigo ASC;", DispConciliacionDet.class);
        q.setParameter(1, idDispConciliacion);
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

}
