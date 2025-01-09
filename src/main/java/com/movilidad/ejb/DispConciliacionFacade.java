/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacion;
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
public class DispConciliacionFacade extends AbstractFacade<DispConciliacion> implements DispConciliacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispConciliacionFacade() {
        super(DispConciliacion.class);
    }

    @Override
    public DispConciliacion obtenerUltimaConciliacion() {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    disp_conciliacion\n"
                    + "WHERE\n"
                    + "    estado_reg = 0\n"
                    + "ORDER BY fecha_hora DESC\n"
                    + "LIMIT 1;";

            Query query = em.createNativeQuery(sql, DispConciliacion.class);
            return (DispConciliacion) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DispConciliacion> findByFecha(Date fecha, int idGopUnidadFuncional, Integer flagConciliado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND dr.id_gop_unidad_funcional = ?3\n";
            String sql_flag_conciliado = flagConciliado == null ? "" : "       AND dr.aprobado = ?2\n";
            String sql = "SELECT DISTINCT\n"
                    + "    dc.*\n"
                    + "FROM\n"
                    + "    disp_conciliacion dc\n"
                    + "        INNER JOIN\n"
                    + "    disp_conciliacion_resumen dr ON dc.id_disp_conciliacion = dr.id_disp_conciliacion\n"
                    + "WHERE\n"
                    + "    date(dc.fecha_hora) = ?1\n"
                    + "        AND dc.estado_reg = 0\n"
                    + "        AND dr.estado_reg = 0\n"
                    + sql_unida_func
                    + sql_flag_conciliado
                    + ";";

            Query query = em.createNativeQuery(sql, DispConciliacion.class);
            query.setParameter(1, Util.dateTimeFormat(fecha));
            query.setParameter(2, flagConciliado);
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
