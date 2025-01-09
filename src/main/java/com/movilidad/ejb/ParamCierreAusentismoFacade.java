/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Novedad;
import com.movilidad.model.ParamCierreAusentismo;
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
public class ParamCierreAusentismoFacade extends AbstractFacade<ParamCierreAusentismo> implements ParamCierreAusentismoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamCierreAusentismoFacade() {
        super(ParamCierreAusentismo.class);
    }

    @Override
    public ParamCierreAusentismo buscarPorRangoFechasYUnidadFuncional(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    param_cierre_ausentismo\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + sql_unida_func
                    + "	AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, ParamCierreAusentismo.class);
            q.setParameter(1, Util.dateFormat(fechaDesde));
            q.setParameter(2, Util.dateFormat(fechaHasta));
            q.setParameter(3, idGopUnidadFuncional);
            return (ParamCierreAusentismo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParamCierreAusentismo verificarRegistro(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional, int idRegistro) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    param_cierre_ausentismo\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + "	AND id_param_cierre_ausentismo <> ?4\n"
                    + sql_unida_func
                    + "	AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, ParamCierreAusentismo.class);
            q.setParameter(1, Util.dateFormat(fechaDesde));
            q.setParameter(2, Util.dateFormat(fechaHasta));
            q.setParameter(3, idGopUnidadFuncional);
            q.setParameter(4, idRegistro);
            return (ParamCierreAusentismo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ParamCierreAusentismo> obtenerListaCierresPorRangoFechasYUnidadFuncional(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    param_cierre_ausentismo\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2))\n"
                    + sql_unida_func
                    + "	AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, ParamCierreAusentismo.class);
            q.setParameter(1, Util.dateFormat(fechaDesde));
            q.setParameter(2, Util.dateFormat(fechaHasta));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
