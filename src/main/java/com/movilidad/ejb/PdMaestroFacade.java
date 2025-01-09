/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.PdAgendaMasivaDTO;
import com.movilidad.dto.PdPrincipalDTO;
import com.movilidad.dto.PdReporteNovedadesDTO;
import com.movilidad.model.PdMaestro;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
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
public class PdMaestroFacade extends AbstractFacade<PdMaestro> implements PdMaestroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdMaestroFacade() {
        super(PdMaestro.class);
    }

    @Override
    public List<PdPrincipalDTO> findAllByEstadoReg(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND t2.id_gop_unidad_funcional= ?1 \n";
        try {
            String sql = "select t1.*, DATE(t1.fecha_apertura) AS fecha_apertura_date,DATE(t1.fecha_cierre) AS fecha_cierre_date, DATE(t1.fecha_citacion) as fecha_citacion_date,"
                    + " t2.codigo_tm, t3.descripcion as estado_proceso, t4.descripcion as tipo_sancion"
                    + " from pd_maestro t1 \n"
                    + "LEFT JOIN empleado t2 on t1.id_empleado = t2.id_empleado\n"
                    + "LEFT JOIN pd_estado_proceso t3 on t1.id_pd_estado_proceso = t3.id_pd_estado_proceso\n"
                    + "LEFT JOIN pd_tipo_sancion t4 on t1.id_pd_tipo_sancion = t4.id_pd_tipo_sancion\n"
                    + "WHERE t1.estado_reg=0 and t2.estado_reg = 0 and t3.estado_reg = 0 and t4.estado_reg =0"
                    + sql_unida_func + ";";
            Query query = em.createNativeQuery(sql, "PdMaestroDTOMapping");
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PdPrincipalDTO> findAllByDate(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND t2.id_gop_unidad_funcional= ?1 \n";
        try {
            String sql = "select t1.*, DATE(t1.fecha_apertura) AS fecha_apertura_date,DATE(t1.fecha_cierre) AS fecha_cierre_date, DATE(t1.fecha_citacion) as fecha_citacion_date,"
                    + " t2.codigo_tm, t3.descripcion as estado_proceso, t4.descripcion as tipo_sancion, t6.username as usuario_responsable, t2.identificacion, t7.asiste, t2.id_empleado_estado"
                    + " from pd_maestro t1 \n"
                    + "LEFT JOIN empleado t2 on t1.id_empleado = t2.id_empleado\n"
                    + "LEFT JOIN pd_estado_proceso t3 on t1.id_pd_estado_proceso = t3.id_pd_estado_proceso\n"
                    + "LEFT JOIN pd_tipo_sancion t4 on t1.id_pd_tipo_sancion = t4.id_pd_tipo_sancion\n"
                    + "LEFT JOIN pd_responsables t5 on t1.id_responsable = t5.id_responsable\n"
                    + "LEFT JOIN users t6 on t5.user_id = t6.user_id\n"
                    + "LEFT JOIN pd_maestro_asistente t7 on t1.id_pd_maestro = t7.id_pd_maestro and t7.estado_reg =0\n"
                    + "WHERE t1.estado_reg=0 and t2.estado_reg = 0 and t3.estado_reg = 0 and t4.estado_reg =0\n"
                    + "AND t1.fecha_apertura BETWEEN ?2 AND ?3"
                    + sql_unida_func + ";";
            Query query = em.createNativeQuery(sql, "PdMaestroDTOMapping");
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, Util.dateFormat(fechaInicio));
            query.setParameter(3, Util.dateFormat(fechaFin));
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PdAgendaMasivaDTO> findAgendaPrgTc(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND t2.id_gop_unidad_funcional= ?1 \n";
        try {
            String sql = "select t1.id_empleado, STR_TO_DATE(CONCAT(t1.fecha,' ',t1.time_origin),'%Y-%m-%d %H:%i:%s')  as fecha_citacion,\n"
                    + " t1.id_gop_unidad_funcional as id_uf, t3.codigo_tm, CONCAT(t3.nombres,' ',t3.apellidos) as nombre_completo\n"
                    + " from prg_tc t1 \n"
                    + "left join prg_tarea t2 on t1.id_task_type = t2.id_prg_tarea\n"
                    + "left join empleado t3 on t1.id_empleado = t3.id_empleado\n"
                    + "where  t1.id_empleado in (select distinct id_empleado\n"
                    + "from pd_maestro  where id_pd_estado_proceso=1 and fecha_citacion is null) and \n"
                    + "t1.fecha between ?2 and ?3 and t1.id_task_type in (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREAS_RELACIONES_LABORALES) + ")"
                    + sql_unida_func + " AND t1.fecha>=date(now())  group by t1.id_empleado;";
            Query query = em.createNativeQuery(sql, "PdAgendaMasivaDTOMapping");
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, Util.dateFormat(fechaInicio));
            query.setParameter(3, Util.dateFormat(fechaFin));
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override

    public List<PdPrincipalDTO> conteoAbiertosPorEmpleado(int idEmpleado) {
        try {
            String sql = "select t1.*, DATE(t1.fecha_apertura) AS fecha_apertura_date,DATE(t1.fecha_cierre) AS fecha_cierre_date, DATE(t1.fecha_citacion) as fecha_citacion_date,"
                    + " t2.codigo_tm, t3.descripcion as estado_proceso, t4.descripcion as tipo_sancion"
                    + " from pd_maestro t1 \n"
                    + "LEFT JOIN empleado t2 on t1.id_empleado = t2.id_empleado\n"
                    + "LEFT JOIN pd_estado_proceso t3 on t1.id_pd_estado_proceso = t3.id_pd_estado_proceso\n"
                    + "LEFT JOIN pd_tipo_sancion t4 on t1.id_pd_tipo_sancion = t4.id_pd_tipo_sancion\n"
                    + "WHERE t1.estado_reg=0 and t2.estado_reg = 0 and t3.estado_reg = 0 and t4.estado_reg =0 and t1.id_pd_estado_proceso=1 and t1.id_empleado = ?1";
            Query query = em.createNativeQuery(sql, "PdMaestroDTOMapping");
            query.setParameter(1, idEmpleado);
             return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    @Override
    public List<PdReporteNovedadesDTO> findNovedadPd(int idGopUnidadFuncional, Date fechaInicio, Date fechaFin) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND T2.id_gop_unidad_funcional= ?1 \n";
        try {
            String sql = "select T1.id_pd_maestro,T2.fecha, T6.descripcion_tipo_novedad as tipo, T3.titulo_tipo_novedad as detalle, T4.identificacion, \n" +
                    "T4.codigo_tm, concat(T4.nombres,' ', T4.apellidos) as nombre, T2.procede, T2.observaciones as observacion, T5.fecha_apertura as fecha_pd \n" +
                    "from pd_maestro_detalle T1\n" +
                    "left join novedad T2 on T1.id_novedad = T2.id_novedad\n" +
                    "left join novedad_tipo_detalles T3 on T2.id_novedad_tipo_detalle = T3.id_novedad_tipo_detalle\n" +
                    "left join empleado T4 on T2.id_empleado = T4.id_empleado\n" +
                    "left join pd_maestro T5 on T5.id_pd_maestro = T1.id_pd_maestro\n" +
                    "left join novedad_tipo T6 on T3.id_novedad_tipo = T6.id_novedad_tipo\n" +
                    "where DATE(T5.fecha_apertura) between ?2 AND ?3" +
                    sql_unida_func;
            Query query = em.createNativeQuery(sql, "PdReporteNovedades");
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, Util.dateFormat(fechaInicio));
            query.setParameter(3, Util.dateFormat(fechaFin));
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
