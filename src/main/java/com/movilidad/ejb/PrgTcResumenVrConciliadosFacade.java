/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Novedad;
import com.movilidad.model.PrgTcResumenVrConciliados;
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
 * @author Carlos Ballestas
 */
@Stateless
public class PrgTcResumenVrConciliadosFacade extends AbstractFacade<PrgTcResumenVrConciliados> implements PrgTcResumenVrConciliadosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTcResumenVrConciliadosFacade() {
        super(PrgTcResumenVrConciliados.class);
    }

    @Override
    public PrgTcResumenVrConciliados verificarRegistro(Integer idRegistro, int idGopUnidadFuncional, Date fecha, int idVehiculoTipo) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?4\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc_resumen_vr_conciliados\n"
                    + "WHERE\n"
                    + "    id_prg_tc_resumen_vr_conciliado <> ?1\n"
                    + "        AND fecha = ?2\n"
                    + "        AND id_vehiculo_tipo = ?3\n"
                    + sql_unida_func
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;";
            Query q = em.createNativeQuery(sql, PrgTcResumenVrConciliados.class);
            q.setParameter(1, idRegistro);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idVehiculoTipo);
            q.setParameter(4, idGopUnidadFuncional);
            return (PrgTcResumenVrConciliados) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTcResumenVrConciliados> findAllByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            Query q = em.createNativeQuery("SELECT * FROM prg_tc_resumen_vr_conciliados where fecha between ?1 and ?2 and estado_reg = 0"
                    + sql_unida_func
                    + ";        ", PrgTcResumenVrConciliados.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     *
     * @param fecha
     * @param idGopUnidadFuncional
     * @param idRegistro
     * @return Lista de PrgTcResumenVrConciliados de acuerdo a los parámetros
     * indicados
     */
    @Override
    public List<PrgTcResumenVrConciliados> findAllByFechaAndUnidadFuncional(Date fecha, int idGopUnidadFuncional, Integer idRegistro) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?2\n";
            Query q = em.createNativeQuery("SELECT * FROM prg_tc_resumen_vr_conciliados where fecha = ?1 and estado_reg = 0"
                    + " and id_prg_tc_resumen_vr_conciliado <> ?3"
                    + sql_unida_func
                    + ";        ", PrgTcResumenVrConciliados.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFuncional);
            q.setParameter(3, idRegistro);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
