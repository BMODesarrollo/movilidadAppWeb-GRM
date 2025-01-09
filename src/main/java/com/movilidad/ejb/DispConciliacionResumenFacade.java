/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispConciliacionResumen;
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
public class DispConciliacionResumenFacade extends AbstractFacade<DispConciliacionResumen> implements DispConciliacionResumenFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispConciliacionResumenFacade() {
        super(DispConciliacionResumen.class);
    }

    @Override
    public List<DispConciliacionResumen> obtenerResumen(Integer idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                    : "        WHERE crf.id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT \n"
                    + "    crf.*\n"
                    + "FROM\n"
                    + "    conciliacion_resumen_flota_rigel crf\n"
                    + sql_unida_func;
            Query query = em.createNativeQuery(sql, DispConciliacionResumen.class);
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DispConciliacionResumen> obtenerResumenPorUfAndIdConciliacion(Integer idConciliacion, int idGopUnidadFuncional, Integer flagConciliado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?2\n";
            String sql_conciliado = flagConciliado == null ? "" : "        AND aprobado = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    disp_conciliacion_resumen\n"
                    + "WHERE\n"
                    + "    id_disp_conciliacion = ?1\n"
                    + sql_unida_func
                    + sql_conciliado
                    + "        AND estado_reg = 0;";

            Query query = em.createNativeQuery(sql, DispConciliacionResumen.class);
            query.setParameter(1, idConciliacion);
            query.setParameter(2, idGopUnidadFuncional);
            query.setParameter(3, flagConciliado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DispConciliacionResumen> findByFechaHora(String fecha) {
        try {
            String sql = "SELECT DISTINCT\n"
                    + "    dr.*\n"
                    + "FROM\n"
                    + "    disp_conciliacion dc\n"
                    + "        INNER JOIN\n"
                    + "    disp_conciliacion_resumen dr ON dc.id_disp_conciliacion = dr.id_disp_conciliacion\n"
                    + "WHERE\n"
                    + "    dc.fecha_hora = ?1\n"
                    + "        AND dc.estado_reg = 0\n"
                    + "        AND dr.estado_reg = 0\n"
                    + ";";

            Query query = em.createNativeQuery(sql, DispConciliacionResumen.class);
            query.setParameter(1, fecha);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
