/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamReporteHoras;
import com.movilidad.util.beans.ReporteHoras;
import com.movilidad.util.beans.ReporteHorasKactus;
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
public class ParamReporteHorasFacade extends AbstractFacade<ParamReporteHoras> implements ParamReporteHorasFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamReporteHorasFacade() {
        super(ParamReporteHoras.class);
    }

    @Override
    public List<ReporteHoras> obtenerDatosReporte(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?3\n";
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "from\n"
                    + "	prg_sercon ps\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = ps.id_empleado\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "group by\n"
                    + "	ps.id_empleado";

            Query query = em.createNativeQuery(sql, "ReporteHorasMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ParamReporteHoras> findAllActivos(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT * from param_reporte_horas where estado_reg = 0"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, ParamReporteHoras.class);
            query.setParameter(1, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHoras> obtenerDatosReporteGenericas(Date fechaInicio, Date fechaFin, Integer idArea, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?4\n";
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + " from\n"
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and id_param_area = ?3\n"
                    + sql_unida_func
                    + "group by\n"
                    + "	gj.id_empleado;";
            Query query = em.createNativeQuery(sql, "ReporteHorasMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idArea);
            query.setParameter(4, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasKactus> obtenerDatosReporteKactus(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?3\n";
            String sql = "select\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "	from\n"
                    + "	prg_sercon\n"
                    + "	where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "    and liquidado = 1\n"
                    + sql_unida_func
                    + "    and id_empleado NOT IN (SELECT \n"
                    + "    id_empleado\n"
                    + "FROM\n"
                    + "    nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    enviado_nomina = 1 AND\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2)))"
                    + "    and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, "ReporteHorasKactusMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHoras> obtenerDatosReporteByFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND ps.id_gop_unidad_funcional = ?3\n";

        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "from\n"
                    + "	prg_sercon ps\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = ps.id_empleado\n"
                    + "where\n"
                    + "	ps.fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "group by\n"
                    + "	ps.id_empleado";

            Query query = em.createNativeQuery(sql, "ReporteHorasMapping");
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasKactus> obtenerDatosReporteKactusGenericas(Date fechaInicio, Date fechaFin, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "	from\n"
                    + "	generica_jornada\n"
                    + "	where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "    and liquidado = 1\n"
                    + "and id_param_area = ?3\n"
                    + "    and id_empleado NOT IN (SELECT \n"
                    + "    id_empleado\n"
                    + "FROM\n"
                    + "    generica_nomina_autorizacion_individual\n"
                    + "WHERE\n"
                    + "    enviado_nomina = 1 AND\n"
                    + "    ((?1 BETWEEN desde AND hasta\n"
                    + "        OR ?2 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?1 AND ?2\n"
                    + "        OR hasta BETWEEN ?1 AND ?2)))"
                    + "    and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, "ReporteHorasKactusMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasKactus> obtenerDatosReporteKactusIndividual(Date fechaInicio, Date fechaFin, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?3\n";
            String sql_empleado = idEmpleado == 0 ? "" : "        AND id_empleado = ?4\n";

            String sql = "select\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "	from\n"
                    + "	prg_sercon\n"
                    + "	where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "    and liquidado   = 1\n"
                    + sql_empleado
                    + sql_unida_func
                    + "    and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, "ReporteHorasKactusMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasKactus> obtenerDatosReporteKactusGenericasIndividual(Date fechaInicio, Date fechaFin, Integer idArea, int idEmpleado) {
        try {
            String sql = "select\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "	from\n"
                    + "	generica_jornada\n"
                    + "	where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "    and liquidado = 1\n"
                    + "and id_param_area = ?3\n"
                    + "and id_empleado = ?4\n"
                    + "    and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, "ReporteHorasKactusMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idArea);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHoras> obtenerDatosReporteByFechasAndUfAndIdEmpleado(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND ps.id_gop_unidad_funcional = ?3\n";
        String sql_empleado = idEmpleado == 0 ? "" : "        AND ps.id_empleado = ?4\n";

        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "from\n"
                    + "	prg_sercon ps\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = ps.id_empleado\n"
                    + "where\n"
                    + "	ps.fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + sql_empleado
                    + "group by\n"
                    + "	ps.id_empleado";

            Query query = em.createNativeQuery(sql, "ReporteHorasMapping");
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHoras> obtenerDatosReporteGenericaByAreaAndEmpleado(Date fechaInicio, Date fechaFin, Integer idArea, int idEmpleado) {
        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END)/ 3600,0) as diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END )/ 3600,0) as nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END)/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END)/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END)/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END)/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END)/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END)/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END)/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END)/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(sum(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END)/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + " from\n"
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "and id_param_area = ?3\n"
                    + "and gj.id_empleado = ?4\n"
                    + "group by\n"
                    + "	gj.id_empleado;";
            Query query = em.createNativeQuery(sql, "ReporteHorasMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idArea);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
