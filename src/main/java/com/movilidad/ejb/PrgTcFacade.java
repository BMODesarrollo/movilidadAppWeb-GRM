/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.InsumoProgramacionDTO;
import com.movilidad.dto.KmPrgEjecDTO;
import com.movilidad.dto.PorcentajeDisponibilidadFlotaDTO;
import com.movilidad.dto.PrimerServicioDTO;
import com.movilidad.dto.ReporteSemanaActualDTO;
import com.movilidad.dto.ServbusPlanificadoDTO;
import com.movilidad.dto.ServbusPlanificadoDetalleDTO;
import com.movilidad.dto.ServbusTipoTablaDTO;
import com.movilidad.dto.SumEliminadoResponsableDTO;
import com.movilidad.dto.TP28UltimoServicioDTO;
import com.movilidad.dto.UltimoServicioDTO;
import com.movilidad.model.PrgClasificacionMotivo;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgStopPoint;
import com.movilidad.model.PrgTc;
import com.movilidad.util.beans.ConsolidadServicios;
import com.movilidad.util.beans.DiasSinOperar;
import com.movilidad.util.beans.InformeOperacion;
import com.movilidad.util.beans.KmsAdicionalesCtrl;
import com.movilidad.util.beans.KmsOperador;
import com.movilidad.util.beans.KmsPerdidosArt;
import com.movilidad.util.beans.KmsPerdidosBi;
import com.movilidad.util.beans.KmsVehiculo;
import com.movilidad.util.beans.NovedadesTQ04;
import com.movilidad.util.beans.SerconList;
import com.movilidad.util.beans.ServbusIdTipoVehiculo;
import com.movilidad.util.beans.ServbusList;
import com.movilidad.util.beans.ServiciosPorSalir;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
public class PrgTcFacade extends AbstractFacade<PrgTc> implements PrgTcFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTcFacade() {
        super(PrgTc.class);
    }

    @Override
    public List<PrgTc> findByCodigoOperador(int value, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    EXISTS( SELECT \n"
                    + "            e.*\n"
                    + "        FROM\n"
                    + "            empleado e\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = prg.id_empleado\n"
                    + "                AND e.codigo_tm = ?1)\n"
                    + "        AND EXISTS( SELECT \n"
                    + "            prgt.*\n"
                    + "        FROM\n"
                    + "            prg_tarea prgt\n"
                    + "        WHERE\n"
                    + "            prg.id_task_type = prgt.id_prg_tarea)\n"
                    + "        AND prg.fecha = ?2\n"
                    + "ORDER BY prg.time_origin;", PrgTc.class);
            q.setParameter(1, value);
            q.setParameter(2, Util.dateFormat(fecha));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findServiceConServbus(String sercon, Date fecha, int idGopUnidadFunc) {
        try {
            String sql_unida_func = idGopUnidadFunc == 0 ? "" : "  AND tc.id_gop_unidad_funcional = ?3\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.sercon = ?1\n"
                    + "        AND tc.servbus IS NOT NULL\n"
                    + "        AND tc.fecha = ?2\n"
                    + sql_unida_func
                    + "ORDER BY tc.time_origin ASC\n"
                    + "LIMIT 1", PrgTc.class);
            q.setParameter(1, sercon);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGopUnidadFunc);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findOperadorByCod(String codigoTm, Date fechaConsulta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON prg.id_empleado = e.id_empleado\n"
                    + "WHERE\n"
                    + "    e.codigo_tm = ?1 AND prg.fecha = ?2\n"
                    + "        AND e.id_empleado_estado = 1\n"
                    + "        AND (prg.sercon LIKE '%DP%'\n"
                    + "        OR prg.sercon LIKE '%master%'\n"
                    + "        OR prg.sercon LIKE 'Desc%');", PrgTc.class);
            q.setParameter(1, codigoTm);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findOperadorProgramado(String codigoTm, Date fechaConsulta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON prg.id_empleado = e.id_empleado\n"
                    + "WHERE\n"
                    + "    e.codigo_tm = ?1 AND prg.fecha = ?2\n"
                    + "ORDER BY prg.time_origin ASC\n"
                    + "LIMIT 1;", PrgTc.class);
            q.setParameter(1, codigoTm);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * Método encargado de ejecutar la operación de un cambio uno a uno entre
     * vehiculos. los servicio de un vehiculo pasaran a uno y viceversa. esto de
     * acuerdo a un servbus, un tiempo de origen de servicio, una fecha y los
     * propios vehículos.
     *
     * @param prgtc1
     * @param prgtc2
     */
    @Override
    public void saveChangeOneToOne(PrgTc prgtc1, PrgTc prgtc2) {
        int idGopUnidadFuncional = prgtc1.getIdGopUnidadFuncional() == null
                ? 0 : prgtc1.getIdGopUnidadFuncional().getIdGopUnidadFuncional();

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND tc.id_gop_unidad_funcional = ?13\n";

        Query q = em.createNativeQuery("UPDATE prg_tc tc \n"
                + "SET \n"
                + "    tc.id_vehiculo = ?1,\n"
                + "    tc.username = ?6,\n"
                + "    tc.id_prg_tc_responsable = ?7,\n"
                + "    tc.old_vehiculo = ?8,\n"
                + "    tc.modificado = ?11,\n"
                + "    tc.id_prg_clasificacion_motivo = ?12,\n"
                + "    tc.observaciones = ?9,\n"
                + "    tc.control = ?10 \n"
                + "WHERE\n"
                + "    tc.id_vehiculo = ?2 \n"
                + "        AND tc.time_origin >= ?3 \n"
                + "        AND tc.fecha = ?4 \n"
                + sql_unida_func
                //                + "AND tc.estado_operacion NOT IN (2,5,8,99) "
                + "        AND tc.servbus = ?5;");
        q.setParameter(1, prgtc1.getIdVehiculo().getIdVehiculo());
        q.setParameter(2, prgtc2.getIdVehiculo().getIdVehiculo());
        q.setParameter(3, prgtc1.getTimeOrigin());
        q.setParameter(4, Util.dateFormat(prgtc2.getFecha()));
        q.setParameter(5, prgtc2.getServbus());
        q.setParameter(6, prgtc2.getUsername());
        q.setParameter(7, prgtc1.getIdPrgTcResponsable().getIdPrgTcResponsable());
        q.setParameter(8, prgtc2.getIdVehiculo().getIdVehiculo());
        q.setParameter(9, prgtc2.getObservaciones());
        q.setParameter(10, prgtc1.getControl());
        q.setParameter(11, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(12, prgtc1.getIdPrgClasificacionMotivo() == null
                ? null : prgtc1.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo()
        );
        q.setParameter(13, idGopUnidadFuncional);
//        System.out.println("prgtc2.getIdVehiculo().getIdVehiculo()->" + prgtc2.getIdVehiculo().getIdVehiculo());
//        System.out.println("prgtc1.getTimeOrigin()->" + prgtc1.getTimeOrigin());
//        System.out.println("prgtc2.getServbus()->" + prgtc2.getServbus());
//        System.out.println("Observación " + prgtc2.getObservaciones());
        q.executeUpdate();
//        System.out.println("-----------------------------------------------------------------------");
        Query qq = em.createNativeQuery("UPDATE prg_tc tc \n"
                + "SET \n"
                + "    tc.id_vehiculo = ?1,\n"
                + "    tc.username = ?6,\n"
                + "    tc.id_prg_tc_responsable = ?7,\n"
                + "    tc.old_vehiculo = ?8,\n"
                + "    tc.modificado = ?11,\n"
                + "    tc.id_prg_clasificacion_motivo = ?12,\n"
                + "    tc.observaciones = ?9,\n"
                + "    tc.control = ?10 \n"
                + "WHERE\n"
                + "    tc.id_vehiculo = ?2 \n"
                + "        AND tc.time_origin >= ?3 \n"
                + "        AND tc.fecha = ?4 \n"
                + sql_unida_func
                //                + "AND tc.estado_operacion NOT IN (2,5,8,99) "
                + "        AND tc.servbus = ?5;");
        qq.setParameter(1, prgtc2.getIdVehiculo().getIdVehiculo());
        qq.setParameter(2, prgtc1.getIdVehiculo().getIdVehiculo());
        qq.setParameter(3, prgtc1.getTimeOrigin());
        qq.setParameter(4, Util.dateFormat(prgtc1.getFecha()));
        qq.setParameter(5, prgtc1.getServbus());

        qq.setParameter(6, prgtc2.getUsername());
        qq.setParameter(7, prgtc1.getIdPrgTcResponsable().getIdPrgTcResponsable());
        qq.setParameter(8, prgtc1.getIdVehiculo().getIdVehiculo());
        qq.setParameter(9, prgtc2.getObservaciones());
        qq.setParameter(10, prgtc1.getControl());
        qq.setParameter(11, MovilidadUtil.fechaCompletaHoy());
        qq.setParameter(12, prgtc1.getIdPrgClasificacionMotivo() == null
                ? null : prgtc1.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo());
        qq.setParameter(13, idGopUnidadFuncional);
//        System.out.println("prgtc1.getIdVehiculo().getIdVehiculo()->" + prgtc1.getIdVehiculo().getIdVehiculo());
//        System.out.println("prgtc1.getTimeOrigin()->" + prgtc1.getTimeOrigin());
//        System.out.println("prgtc1.getServbus()->" + prgtc1.getServbus());
        qq.executeUpdate();
    }

    @Override
    public List<PrgTc> findByFechaResumen(Date fecha, int tipoVehiculo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    prg.fecha = ?1\n"
                    + "        AND EXISTS( SELECT \n"
                    + "            v.*\n"
                    + "        FROM\n"
                    + "            vehiculo v\n"
                    + "        WHERE\n"
                    + "            v.id_vehiculo = prg.id_vehiculo\n"
                    + "                AND id_vehiculo_tipo = ?2)\n"
                    + "        AND EXISTS( SELECT \n"
                    + "            e.*\n"
                    + "        FROM\n"
                    + "            empleado e\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = prg.id_empleado)\n"
                    + "ORDER BY prg.time_origin;", PrgTc.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, tipoVehiculo);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findByCodigoAndHora(String codigoV, Date fechaConsulta, String timeOrigin, String timeDestiny, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND tc.id_gop_unidad_funcional = ?5\n";

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    EXISTS( SELECT \n"
                    + "            v.*\n"
                    + "        FROM\n"
                    + "            vehiculo v\n"
                    + "        WHERE\n"
                    + "            v.id_vehiculo = tc.id_vehiculo\n"
                    + "                AND v.codigo LIKE '%" + codigoV + "'\n"
                    + "                AND v.id_vehiculo_tipo_estado = 1)\n"
                    + "        AND tc.fecha = ?2\n"
                    + sql_unida_func
                    + "ORDER BY tc.time_origin ASC\n"
                    + "LIMIT 1;", PrgTc.class);
//            q.setParameter(1, codigoV);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            q.setParameter(3, timeOrigin);
            q.setParameter(4, timeDestiny);
            q.setParameter(5, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método responsable de devolver un servicio (PrgTc) con la fecha que se
     * indique y donde el operador con codigo que se indique sean iguales,
     * ademas que, el operador este activo.
     *
     * @param codigoTm
     * @param fecha
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public PrgTc findSerconByCodigoTmAndIdUnidadFunc(int codigoTm, Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND tc.id_gop_unidad_funcional = ?3\n";

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    EXISTS( SELECT \n"
                    + "            e.*\n"
                    + "        FROM\n"
                    + "            empleado e\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = tc.id_empleado\n"
                    + "                AND e.id_empleado_estado = 1\n"
                    + "                AND e.codigo_tm = ?1)\n"
                    + "        AND tc.fecha = ?2\n"
                    + sql_unida_func
                    + "ORDER BY tc.fecha DESC\n"
                    + "LIMIT 1;", PrgTc.class);
            q.setParameter(1, codigoTm);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findOperadorByCodigoAndHora(String codigoTm, Date fechaConsulta, String timeOrigin) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "WHERE\n"
                + "    EXISTS( SELECT \n"
                + "            e.*\n"
                + "        FROM\n"
                + "            empleado e\n"
                + "        WHERE\n"
                + "            e.id_empleado = prg.id_empleado\n"
                + "                AND e.codigo_tm = ?1)\n"
                + "        AND prg.fecha = ?2\n"
                + "        AND prg.time_origin >= ?3\n"
                + "ORDER BY prg.time_origin ASC\n"
                + "LIMIT 1;", PrgTc.class);
        q.setParameter(1, codigoTm);
        q.setParameter(2, Util.dateFormat(fechaConsulta));
        q.setParameter(3, timeOrigin);
        return (PrgTc) q.getSingleResult();
    }

    @Override
    public List<PrgTc> findByFecha(Date fecha, Integer idGopUnidadFuncional) {//Revisada LV 22102019
        try {
            String sql_unida_func = (idGopUnidadFuncional == null || idGopUnidadFuncional == 0)
                    ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + sql_unida_func
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC", PrgTc.class);
            q.setParameter("fecha", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findEliminadosByFecha(Date fecha, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 AND p.estadoOperacion = 5 "
                    + "AND p.distance > 0  AND p.idTaskType.comercial = 1 "
                    + "AND p.idTaskType.idPrgTarea NOT IN (2,3,4) "
                    + sql_unida_func
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC");
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findAdicionalesByFecha(Date fecha, int idGopUnidadFuncional) {// Revisada LV 22102019
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + "AND p.idTaskType.comercial = 1 "
                    + "AND p.estadoOperacion in (3,4,6) "
                    + sql_unida_func
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC");
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idVehiculoTipo.idVehiculoTipo = :tipo "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + sql_unida_func
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC");
            q.setParameter("tipo", tipoVehiculo);
            q.setParameter("fecha", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findAdicionalesByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {// Revisar
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idVehiculoTipo.idVehiculoTipo = :tipo "
                    + "AND p.idTaskType.sumDistancia = 1 AND "
                    + "p.idTaskType.comercial = 1 "
                    + sql_unida_func
                    + "AND p.estadoOperacion in (3,4,6) "
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC");
            q.setParameter("tipo", tipoVehiculo);
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findEliminadosByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) { //Revisar
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idVehiculoTipo.idVehiculoTipo = :tipo "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + "AND p.estadoOperacion = 5 "
                    + sql_unida_func
                    + "AND p.distance > 0  AND p.idTaskType.comercial = 1 "
                    + "AND p.idTaskType.idPrgTarea NOT IN (2,3,4) "
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC");
            q.setParameter("tipo", tipoVehiculo);
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método responsable de devolver una lista de sercones que su tarea sume
     * distancia que el tiempo de origen sea mayor o igual al indicado por
     * parametro, que el servbus sea igual al que se indique por parámetro, que
     * la fecha sea a la que se indique por parámetro, el el vehículo del
     * servicio no sea nulo y ser ordenara todo por tiempo de origen ascendente.
     *
     * @param servbus
     * @param fecha
     * @param timeOrigen
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public List<SerconList> findSerconByServbus(String servbus, Date fecha, String timeOrigen, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        try {
            Query q = em.createNativeQuery("SELECT DISTINCT\n"
                    + "    tc.sercon\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                    + "WHERE\n"
                    + "    pt.sum_distancia = 1 AND tc.fecha = ?1\n"
                    + "        AND tc.time_origin >= ?2\n"
                    + "        AND tc.servbus = ?3\n"
                    + "        AND tc.id_vehiculo IS NOT NULL\n"
                    + sql_unida_func
                    + "GROUP BY tc.sercon\n"
                    + "ORDER BY MIN(tc.time_origin) ASC");
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, timeOrigen);
            q.setParameter(3, servbus);
            q.setParameter(4, idGopUnidadFuncional);
            List<SerconList> lista = new ArrayList<>();
            SerconList serc;
            for (Object s : q.getResultList()) {
                serc = new SerconList();
                serc.setSercon(s.toString());
//                serc.setTimeOrigin(s.toString());
                lista.add(serc);
            }
            return lista;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ServbusList> findServbusBySercon(String sercon, Date fecha, String timeOrigen, int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        Query q = em.createNativeQuery("SELECT DISTINCT\n"
                + "    tc.servbus\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                + "    pt.sum_distancia = 1 AND tc.fecha = ?1\n"
                + "        AND tc.time_origin >= ?2\n"
                + "        AND tc.sercon = ?3\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + sql_unida_func
                + "ORDER BY tc.time_origin ASC;");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, timeOrigen);
        q.setParameter(3, sercon);
        q.setParameter(4, idGopUnidadFunc);
        List<ServbusList> serb = new ArrayList<>();
        List<ServbusList> lista = new ArrayList<>();
        ServbusList serv;

        for (Object s : q.getResultList()) {
            serv = new ServbusList();
            serv.setServbus(s.toString());
//            serv.setTimeOrigin(s.toString());
            lista.add(serv);
        }

        return lista;
    }

    @Override
    public List<ServbusList> findServbusBySerconSinOperador(String sercon, Date fecha, String timeOrigen, int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        Query q = em.createNativeQuery("SELECT DISTINCT\n"
                + "    tc.servbus\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                + "    pt.sum_distancia = 1 AND tc.fecha = ?1\n"
                + "        AND tc.time_origin >= ?2\n"
                + "        AND tc.sercon = ?3\n"
                + "        AND tc.id_empleado IS NULL\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + sql_unida_func
                + "ORDER BY tc.time_origin ASC;");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, timeOrigen);
        q.setParameter(3, sercon);
        q.setParameter(4, idGopUnidadFunc);
        List<ServbusList> serb = new ArrayList<>();
        List<ServbusList> lista = new ArrayList<>();
        ServbusList serv;

        for (Object s : q.getResultList()) {
            serv = new ServbusList();
            serv.setServbus(s.toString());
//            serv.setTimeOrigin(s.toString());
            lista.add(serv);
        }

        return lista;
    }

    /**
     * El método devuelve todos los servicios(PrgTc) que pueden ser eliminados o
     * que puedan sufrir un cambio de o desasignación de vehículos, que cumplan
     * con los siguientes criterios: -Fecha igual a la que se indique. -tiempo
     * de origen mayor o igual al que se indique. -Servbus igual al que se
     * indique. -Tarea del servicio no nula. -ordenada acsendentemente por
     * tiempo de origen.
     *
     * @param sercon
     * @param fecha
     * @param timeOrigen
     * @param servbus
     * @param idGopUnidadFunc
     * @return
     */
    @Override
    public List<PrgTc> findTcBySercon(String sercon, Date fecha, String timeOrigen, String servbus, int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?5\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "WHERE\n"
                + "    tc.sercon = ?1 AND tc.fecha = ?2\n"
                + "        AND tc.time_origin >= ?3\n"
                + "        AND tc.servbus = ?4 \n"
                + "        AND tc.id_task_type IS NOT NULL\n"
                + sql_unida_func
                + "ORDER BY tc.time_origin ASC;", PrgTc.class);
        q.setParameter(1, sercon);
        q.setParameter(2, Util.dateFormat(fecha));
        q.setParameter(3, timeOrigen);
        q.setParameter(4, servbus);
        q.setParameter(5, idGopUnidadFunc);
        return q.getResultList();
    }
//se modifica el metodo para corregir los servicios visualizados para al armar el arbol de operador
// en la gestion de servicios

    @Override
    public List<PrgTc> findAllTcBySercon(String sercon, Date fecha, String timeOrigen, int idEmpleado, int idGopUnidadFuncional, boolean dispo) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?5\n";
        String sql_empleado = dispo ? "       AND tc.id_empleado = ?4\n" : "";
        String sql = "";
        sql = "SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "WHERE\n"
                + "    tc.sercon = ?1 AND tc.fecha = ?2\n"
                + "        AND tc.time_origin >= ?3\n"
                + "        AND tc.id_task_type IS NOT NULL\n"
                + sql_empleado
                + sql_unida_func
                + "        AND tc.estado_operacion NOT IN(9)\n"
                + "ORDER BY time_origin ASC;";

        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, sercon);
        q.setParameter(2, Util.dateFormat(fecha));
        q.setParameter(3, timeOrigen);
        q.setParameter(4, idEmpleado);
        q.setParameter(5, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> findAllTcBySerconSinOperador(Date fecha, String timeOrigen, String sercon, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "WHERE\n"
                + "    tc.fecha = ?1 AND tc.time_origin >= ?2\n"
                + "        AND tc.sercon = ?3\n"
                + sql_unida_func
                + "        AND tc.id_empleado IS NULL;", PrgTc.class
        );
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, timeOrigen);
        q.setParameter(3, sercon);
        q.setParameter(4, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public void eliminarPrgTc(PrgTc prgtc) {
        Query q = em.createNativeQuery("UPDATE prg_tc p \n"
                + "SET \n"
                + "    p.estado_operacion = ?5,\n"
                + "    p.username = ?2,\n"
                + "    p.modificado = ?3,\n"
                + "    p.id_prg_tc_responsable = ?4,\n"
                + "    p.observaciones = ?6,\n"
                + "    p.control = ?7,\n"
                + "    p.id_prg_clasificacion_motivo = ?8 \n"
                + "WHERE\n"
                + "    p.id_prg_tc = ?1;");
        q.setParameter(1, prgtc.getIdPrgTc());
        q.setParameter(2, prgtc.getUsername());
        q.setParameter(3, new Date());
        q.setParameter(4, prgtc.getIdPrgTcResponsable().getIdPrgTcResponsable());
        q.setParameter(5, prgtc.getEstadoOperacion());
        q.setParameter(6, prgtc.getObservaciones());
        q.setParameter(7, prgtc.getControl());
        q.setParameter(8, prgtc.getIdPrgClasificacionMotivo() == null ? null : prgtc.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo());
        q.executeUpdate();
        Query qq = em.createNativeQuery("UPDATE prg_tc_det pd \n"
                + "SET \n"
                + "    pd.ejecutado = ?4,\n"
                + "    pd.username = ?2,\n"
                + "    pd.modificado = ?3\n"
                + "WHERE\n"
                + "    pd.id_prg_tc = ?1;");
        qq.setParameter(1, prgtc.getIdPrgTc());
        qq.setParameter(2, prgtc.getUsername());
        qq.setParameter(3, new Date());
        qq.setParameter(4, prgtc.getEstadoOperacion());
        qq.executeUpdate();
    }

    @Override
    public BigDecimal getDistance(PrgStopPoint prgFromStop, PrgStopPoint prgToStop) {
        Query query = em.createQuery("SELECT d.distance FROM PrgDistance d WHERE "
                + "d.idPrgFromStop = :from "
                + "AND d.idPrgToStop = :stop AND d.vigente = 1");
        query.setParameter("from", prgFromStop);
        query.setParameter("stop", prgToStop);

        return (BigDecimal) query.getSingleResult();
    }

    //By Yisus Antonio
    @Override
    public BigDecimal findDistandeByFromStopAndToStop(int prgFromStop, int prgToStop) {
        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "    d.distance\n"
                    + "FROM\n"
                    + "    prg_distance d\n"
                    + "WHERE\n"
                    + "    d.id_prg_from_stop = ?1\n"
                    + "        AND d.id_prg_to_stop = ?2 \n"
                    + "        AND d.vigente = ?3;");
            query.setParameter(1, prgFromStop);
            query.setParameter(2, prgToStop);
            query.setParameter(3, 1);

            return (BigDecimal) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public BigDecimal getAdicionales(Date fecha) {
        try {
            Query q = em.createQuery("SELECT SUM(p.distance) FROM PrgTc p "
                    + "WHERE p.fecha = :fecha AND p.idTaskType.sumDistancia = 1 "
                    + "AND p.idTaskType.comercial = 1 AND "
                    + "p.estadoOperacion IN (3,4,6) ", BigDecimal.class);
            q.setParameter("fecha", fecha);
            return (BigDecimal) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigDecimal getAdicionalesByTipoVehiculo(Date fecha, int tipoVehiculo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?3\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    SUM(TRUNCATE(tc.distance, 0))\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_tarea t ON tc.id_task_type = t.id_prg_tarea\n"
                    + "WHERE\n"
                    + "    tc.fecha = ?1 AND t.sum_distancia = 1\n"
                    + "        AND t.comercial = 1\n"
                    + "        AND tc.id_vehiculo_tipo = ?2\n"
                    + sql_unida_func
                    + "        AND tc.estado_operacion IN (3 , 4, 6);");
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, tipoVehiculo);
            q.setParameter(3, idGopUnidadFuncional);
            return (BigDecimal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigDecimal getEliminadosByTipoVehiculo(Date fecha, int tipoVehiculo) {
        try {
            Query q = em.createQuery("SELECT SUM(p.distance) FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 AND p.estadoOperacion = 5 "
                    + "AND p.distance > 0  AND p.idTaskType.comercial = 1 "
                    + "AND p.idTaskType.idPrgTarea NOT IN (2,3,4) "
                    + "AND p.idVehiculoTipo.idVehiculoTipo = :tipo");
            q.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
            q.setParameter("tipo", tipoVehiculo);
            return (BigDecimal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigDecimal getVacComerciales(Date fecha) {
        try {
            Query q = em.createQuery("SELECT SUM(p.distance) FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + "AND p.estadoOperacion = 6 ", BigDecimal.class);
            q.setParameter("fecha", fecha);
            return (BigDecimal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void updateTmDistance(Date fecha) {
        Query query = em.createQuery("UPDATE PrgTc p SET "
                + "p.tmDistance = p.distance WHERE p.fecha = :fecha");
        query.setParameter("fecha", fecha);
        query.executeUpdate();
    }

    @Override
    public BigDecimal getVacEjecutadosByTipoVehiculo(Date fecha, int tipoVehiculo) {
        try {
            Query q = em.createQuery("SELECT SUM(p.distance) FROM PrgTc p "
                    + "WHERE p.fecha = :fecha AND "
                    + "p.idTaskType.sumDistancia= 1 AND "
                    + "p.idTaskType.comercial= 0 AND "
                    + "p.idVehiculoTipo.idVehiculoTipo= :tipo AND "
                    + "p.estadoOperacion= 7 ", BigDecimal.class);
            q.setParameter("fecha", fecha);
            q.setParameter("tipo", tipoVehiculo);
            return (BigDecimal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public BigDecimal getComEjecutadosConciliadosByTipoVehiculo(Date fecha, int tipoVehiculo) {
        try {
            Query q = em.createQuery("SELECT SUM(p.tmDistance) FROM PrgTc p "
                    + "WHERE p.fecha = :fecha AND "
                    + "p.idTaskType.sumDistancia= 1 AND "
                    + "p.idTaskType.comercial= 1 AND "
                    + "p.idVehiculoTipo.idVehiculoTipo= :tipo AND "
                    + "p.estadoOperacion= 7 ", BigDecimal.class);
            q.setParameter("fecha", fecha);
            q.setParameter("tipo", tipoVehiculo);
            return (BigDecimal) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findByCodigoAndHoraUnoAUno(String codigoV, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    EXISTS( SELECT \n"
                    + "            v.*\n"
                    + "        FROM\n"
                    + "            vehiculo v\n"
                    + "        WHERE\n"
                    + "            v.id_vehiculo = tc.id_vehiculo\n"
                    + "                AND v.codigo LIKE '%" + codigoV + "'\n"
                    + "                AND v.id_vehiculo_tipo_estado = 1)\n"
                    + "        AND tc.fecha = ?2\n"
                    + "        AND tc.time_origin >= ?3\n"
                    + "        AND tc.estado_operacion <> 5\n"
                    + sql_unida_func
                    + "ORDER BY tc.time_origin ASC\n"
                    + "LIMIT 1;", PrgTc.class);
//            q.setParameter(1, codigoV);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            q.setParameter(3, timeOrigin);
            q.setParameter(4, idGopUnidadFuncional);

            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> serviceSinBus(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        pt.*\n"
                + "    FROM\n"
                + "        prg_tarea pt\n"
                + "    WHERE\n"
                + "        pt.sum_distancia = 1) res ON tc.id_task_type = res.id_prg_tarea\n"
                + "INNER JOIN (\n"
                + "	SELECT servbus, min(time_origin) as time_origin\n"
                + "	FROM \n"
                + "	 prg_tc tc\n"
                + "	WHERE\n"
                + "    tc.fecha = ?1\n"
                + "		AND tc.id_vehiculo IS NULL\n"
                + "		AND servbus IS NOT NULL\n"
                + "		AND tc.id_ruta IS NOT NULL\n"
                + "		AND tc.estado_operacion <>5\n"
                + "		AND tc.estado_operacion <>8\n"
                + "		AND tc.time_origin > TIME_FORMAT(now(),'%H:%i:%s') \n"
                + "	group by servbus) AS servbuses\n"
                + "	on servbuses.servbus= tc.servbus "
                + "WHERE\n "
                + "    tc.fecha = ?1\n"/*
                 + "        AND tc.id_vehiculo IS NULL\n"
                 + "        AND servbus IS NOT NULL\n"
                 + "        AND tc.id_ruta IS NOT NULL\n"
                 + "        AND tc.estado_operacion <> ?3\n"
                 + "        AND tc.estado_operacion <> ?4\n"*/
                + "        AND tc.time_origin = servbuses.time_origin "
                + "        AND tc.time_origin > TIME_FORMAT(now(),'%H:%i:%s') \n"
                + sql_unida_func
                + "ORDER BY time_origin ASC", PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(4, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> serviceSinOpe(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "INNER JOIN (\n"
                + " SELECT servbus, min(time_origin) as time_origin\n"
                + " FROM \n"
                + "  prg_tc tc\n"
                + " WHERE\n"
                + "      tc.fecha = ?1\n"
                + "	 AND tc.id_empleado IS NULL\n"
                + "	 AND tc.id_task_type IS NOT NULL\n"
                + "	 AND tc.tabla IS NOT NULL\n"
                + "	 AND tc.id_ruta IS NOT NULL\n"
                + "      AND tc.estado_operacion <>?3\n"
                + "      AND tc.estado_operacion <>?4\n"
                + "	 AND tc.time_origin > TIME_FORMAT(now(),'%H:%i:%s') \n"
                + "GROUP BY servbus) AS servbuses\n"
                + "ON servbuses.servbus = tc.servbus "
                + "WHERE\n"
                + "    tc.fecha = ?1\n"/*
                 + "        AND tc.id_empleado IS NULL\n"
                 + "        AND tc.id_task_type IS NOT NULL\n"
                 + "        AND tc.tabla IS NOT NULL\n"
                 + "        AND tc.id_ruta IS NOT NULL\n"
                 + "        AND tc.estado_operacion <>?3\n"
                 + "        AND tc.estado_operacion <>?4\n"*/
                + "        AND tc.time_origin = servbuses.time_origin"
                + "        AND tc.time_origin > TIME_FORMAT(now(),'%H:%i:%s') \n"
                + sql_unida_func
                + "ORDER BY tc.time_origin ASC", PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(4, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> operadoresDisponibles(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        LEFT JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                + "        tc.fecha = ?1\n"
                + "        AND pt.op_disponible = 1\n"
                + "        AND tc.estado_reg = 0\n"
                + "        AND TIME(time_destiny) >= TIME(NOW())\n"
                + "        AND tc.id_empleado IS NOT NULL\n"
                + "        AND estado_operacion <> ?3\n"
                + sql_unida_func
                + "ORDER BY time_origin ASC;", PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_DISP_9);
        return q.getResultList();
    }

    /**
     * Metodo responsable de aplicar en BD la asignación de servbus-vehículo
     *
     * @param idVehiculo
     * @param servbus
     * @param username
     * @param fecha
     * @param idGopUnidadFunc
     * @return
     */
    @Override
    public int asignarBusToServbus(Integer idVehiculo, String servbus, String fecha, String username, int idGopUnidadFunc) {
        try {
            String sql = "UPDATE prg_tc p \n"
                    + "SET \n"
                    + "    p.id_vehiculo = ?1,\n"
                    + "    p.username = ?2\n"
                    + "WHERE\n"
                    + "    p.servbus = ?3 AND p.fecha = DATE(?4)\n"
                    + "        AND p.id_gop_unidad_funcional = ?5";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, username);
            q.setParameter(3, servbus);
            q.setParameter(4, fecha);
            q.setParameter(5, idGopUnidadFunc);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * Metodo responsable de aplicar en BD la desasignación de servbus-vehículo
     *
     * @param idVehiculo
     * @param username
     * @param fecha
     * @param idGopUnidadFunc
     * @return
     */
    @Override
    public int updateDesasignarVehiculoServbus(Integer idVehiculo, String servbus, String fecha, String username, int idGopUnidadFunc) {
        try {
            String sql = "UPDATE prg_tc p \n"
                    + "SET \n"
                    + "    p.id_vehiculo = NULL,\n"
                    + "    p.username = ?2,\n"
                    + "    p.old_vehiculo = ?1\n"
                    + "WHERE\n"
                    + "    p.servbus = ?6 AND p.fecha = ?4\n"
                    + "        AND p.id_gop_unidad_funcional = ?5";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, username);
            q.setParameter(4, fecha);
            q.setParameter(5, idGopUnidadFunc);
            q.setParameter(6, servbus);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<PrgTc> listarSerbus(Date date, int idGopUnidadFuncional) {//Entrada a Patios
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point psp ON tc.to_stop = psp.id_prg_stoppoint\n"
                + "WHERE\n"
                + "    tc.fecha = ?1 AND psp.is_depot = 1\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + "        AND tc.estado_operacion NOT IN (5 , 8, 99)\n"
                + sql_unida_func
                + "ORDER BY tc.time_destiny ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, Util.toDate(Util.dateFormat(date)));
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> listarSalidas(Date date, int idGopUnidadFuncional) {//Salida a Patios
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point psp ON tc.from_stop = psp.id_prg_stoppoint\n"
                + "WHERE\n"
                + "    tc.fecha = ?1 AND psp.is_depot = 1\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + "        AND tc.estado_operacion NOT IN (5 , 8, 99)\n"
                + sql_unida_func
                + "ORDER BY time(tc.time_origin) ASC";
        Query query = em.createNativeQuery(sql, PrgTc.class);
        query.setParameter(1, Util.toDate(Util.dateFormat(date)));
        query.setParameter(2, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<KmsOperador> getKmByOperador(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional) {

        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND prg_tc.id_gop_unidad_funcional = ?3\n";

            Query query = em.createNativeQuery("select "
                    + "empleado.codigo_tm as codigo_tm, "
                    + "empleado.nombres as nombres, "
                    + "empleado.apellidos as apellidos, "
                    + "Sum(prg_tc.distance) AS total, "
                    + "Sum(if((estado_operacion = 0 or estado_operacion=1 or estado_operacion=2) and prg_tarea.id_prg_tarea<>4 ,distance,0)) AS comercial, "
                    + "Sum(if(estado_operacion = 0 and prg_tarea.id_prg_tarea=4,distance,0)) AS hlp_prg, "
                    + "Sum(if(estado_operacion = 3 or estado_operacion=4,distance,0)) AS adicionales, "
                    + "Sum(if(estado_operacion = 6,distance,0)) AS vaccom, "
                    + "Sum(if(estado_operacion = 5 and prg_tarea.id_prg_tarea<>4,distance,0)) AS comercial_Eliminados, "
                    + "Sum(if(estado_operacion = 5 and prg_tarea.id_prg_tarea=4,distance,0)) AS hlp_Eliminados, "
                    + "Sum(if(estado_operacion = 7,distance,0)) AS vac "
                    + "FROM "
                    + "prg_tc "
                    + "INNER JOIN empleado ON prg_tc.id_empleado = empleado.id_empleado "
                    + "INNER JOIN prg_tarea ON prg_tc.id_task_type = prg_tarea.id_prg_tarea "
                    + "WHERE "
                    + "prg_tc.fecha BETWEEN ?1 AND ?2 "
                    + sql_unida_func
                    + "group by  "
                    + "empleado.codigo_tm, "
                    + "empleado.nombres, "
                    + "empleado.apellidos ORDER BY empleado.nombres ASC", "KmByOperadorMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<KmsVehiculo> getKmByVehiculo(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional) {
        try {

            String sql_unida_func_vehiculo = (idGopUnidadFuncional == null || idGopUnidadFuncional == 0) ? "" : "        AND vehiculo.id_gop_unidad_funcional = ?3\n";

            Query query = em.createNativeQuery("(SELECT\n"
                    //                    + "	fecha,\n"
                    + "	DATE_FORMAT(?1, \"%Y-%m-%d\") as fecha,"
                    + "	vehiculo.codigo as codigo_vehiculo,\n"
                    + "	ifnull(round(((comercial + hlp_prg + adicionales + vaccom + vac)/ 1000), 0),0) as total_km,\n"
                    + "	ifnull((comercial + hlp_prg + adicionales + vaccom + vac),0) as total_mts,\n"
                    + "	comercial,\n"
                    + "	hlp_prg,\n"
                    + "	adicionales,\n"
                    + "	vaccom,\n"
                    + "	comercial_Eliminados,\n"
                    + "	hlp_Eliminados,\n"
                    + "	vac\n"
                    + "from\n"
                    + "	vehiculo\n"
                    + "LEFT join (\n"
                    + "	select\n"
                    + "		vehiculo.id_vehiculo,\n"
                    + "		fecha,\n"
                    + "		Sum(if((estado_operacion = 0 or estado_operacion = 1 or estado_operacion = 2) and prg_tarea.id_prg_tarea <> 4 , distance, 0)) AS comercial,\n"
                    + "		Sum(if(estado_operacion = 0 and prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_prg,\n"
                    + "		Sum(if(estado_operacion = 3 or estado_operacion = 4, distance, 0)) AS adicionales,\n"
                    + "		Sum(if(estado_operacion = 6, distance, 0)) AS vaccom,\n"
                    + "		Sum(if(estado_operacion = 5 and prg_tarea.id_prg_tarea <> 4, distance, 0)) AS comercial_Eliminados,\n"
                    + "		Sum(if(estado_operacion = 5 and prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_Eliminados,\n"
                    + "		Sum(if(estado_operacion = 7, distance, 0)) AS vac\n"
                    + "	from\n"
                    + "		prg_tc\n"
                    + "		RIGHT JOIN vehiculo ON\n"
                    + "			prg_tc.id_vehiculo=vehiculo.id_vehiculo\n"
                    + "	INNER JOIN prg_tarea ON\n"
                    + "		prg_tc.id_task_type = prg_tarea.id_prg_tarea\n"
                    + "	WHERE\n"
                    + "		prg_tc.fecha BETWEEN ?1 AND ?2\n"
                    + "		and prg_tc.servbus is not null\n"
                    + "	GROUP BY\n"
                    + "		id_vehiculo, fecha ) prg ON\n"
                    + "	prg.id_vehiculo = vehiculo.id_vehiculo\n"
                    + "	where vehiculo.id_vehiculo_tipo_estado = 1\n"
                    + sql_unida_func_vehiculo
                    + "order by\n"
                    //                    + "	fecha,\n"
                    + "	vehiculo.codigo asc)", "KmByVehiculoMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updatePrgTcUnoAUno(Integer id_ve, String serv, Date fecha,
            String timeOrigen, int oldVehiculo, String control,
            String observacion, String username, int idPrgTcResponsable,
            PrgClasificacionMotivo clasificacionMotivo, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND p.id_gop_unidad_funcional = ?12\n";

        try {
            Query q = em.createNativeQuery("UPDATE prg_tc p \n"
                    + "SET \n"
                    + "    p.id_vehiculo = ?1,\n"
                    + "    p.old_vehiculo = ?5,\n"
                    + "    p.modificado = ?7,\n"
                    + "    p.control = ?6,\n"
                    + "    p.observaciones = ?8,\n"
                    + "    p.username = ?9,\n"
                    + "    p.id_prg_tc_responsable = ?10,\n"
                    + "    p.id_prg_clasificacion_motivo = ?11\n"
                    + "WHERE\n"
                    + "    p.servbus = ?2 AND p.fecha = ?3\n"
                    + sql_unida_func
                    + "        AND p.time_origin >= ?4;");
            q.setParameter(1, id_ve);
            q.setParameter(2, serv);
            q.setParameter(3, Util.dateFormat(fecha));
            q.setParameter(4, timeOrigen);
            q.setParameter(5, oldVehiculo);
            q.setParameter(6, control);
            q.setParameter(7, MovilidadUtil.fechaCompletaHoy());
            q.setParameter(8, observacion);
            q.setParameter(9, username);
            q.setParameter(10, idPrgTcResponsable);
            q.setParameter(11, clasificacionMotivo == null ? null : clasificacionMotivo.getIdPrgClasificacionMotivo());
            q.setParameter(12, idGopUnidadFuncional);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public PrgTc findVehiculoAsignar(int idVehiculo, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    prg.fecha = ?2\n"
                    + "        AND prg.servbus IS NOT NULL\n"
                    + "        AND prg.id_ruta IS NOT NULL\n"
                    //                    + "        AND prg.old_vehiculo IS NULL\n"
                    + "        AND prg.id_vehiculo = ?1\n"
                    + "ORDER BY prg.time_origin ASC\n"
                    + "LIMIT 1", PrgTc.class);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, Util.dateFormat(fecha));
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método responable de consultar objto prgtc que no tenga asignación de
     * vehículo previa
     *
     * @param servbus
     * @param fecha
     * @return PrgTc
     */
    @Override
    public PrgTc buscarPrgTcSinVehiculo(String servbus, Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?3\n";

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.servbus = ?1 AND tc.fecha = ?2\n"
                    + "        AND tc.id_vehiculo IS NULL\n"
                    + sql_unida_func
                    + "LIMIT 1;", PrgTc.class);
            q.setParameter(1, servbus);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> salidasPatio(String fecha, String fecha_hora, int idPrgStopPoint, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point sp ON sp.id_prg_stoppoint = tc.to_stop\n"
                + "        AND sp.is_depot = 1\n"
                + "WHERE\n"
                + "    tc.fecha = ?1\n"
                + "        AND tc.time_origin >= ?2\n"
                + "        AND tc.to_stop = ?3\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + sql_unida_func
                + "ORDER BY tc.time_origin ASC", PrgTc.class);
        q.setParameter(1, fecha);
        q.setParameter(2, fecha_hora);
        q.setParameter(3, idPrgStopPoint);
        q.setParameter(4, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> entradasPatio(String fecha, String fecha_hora, int idPrgStopPoint, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?4\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point sp ON sp.id_prg_stoppoint = tc.to_stop\n"
                + "        AND sp.is_depot = 1\n"
                + "WHERE\n"
                + "    tc.fecha = ?1\n"
                + "        AND tc.time_destiny >= ?2\n"
                + "        AND tc.to_stop = ?3\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + sql_unida_func
                + "ORDER BY tc.time_destiny ASC", PrgTc.class);
        q.setParameter(1, fecha);
        q.setParameter(2, fecha_hora);
        q.setParameter(3, idPrgStopPoint);
        q.setParameter(4, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public PrgTc servicioAnterior(PrgTc prgTc) {
        try {
            int idGopUnidadFuncional = prgTc.getIdGopUnidadFuncional() == null
                    ? 0 : prgTc.getIdGopUnidadFuncional().getIdGopUnidadFuncional();

            String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                    : "       AND prg.id_gop_unidad_funcional = ?4\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    prg.servbus = ?1 AND fecha = ?2\n"
                    + "        AND prg.time_origin < ?3 \n"
                    + "        AND prg.id_vehiculo IS NOT NULL\n"
                    + sql_unida_func
                    + "ORDER BY time_origin DESC\n"
                    + "LIMIT 1", PrgTc.class);
            q.setParameter(1, prgTc.getServbus());
            q.setParameter(2, Util.dateFormat(prgTc.getFecha()));
            q.setParameter(3, prgTc.getTimeOrigin());
            q.setParameter(4, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long countByFechas(Date fromDate, Date toDate, int idGopUnidadFunc) {
        String uf = idGopUnidadFunc == 0 ? " " : " AND id_gop_unidad_funcional = ?3";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    COUNT(*)\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN DATE(?1) AND DATE(?2)\n"
                    + uf);
            q.setParameter(1, Util.dateFormat(fromDate));
            q.setParameter(2, Util.dateFormat(toDate));
            q.setParameter(3, idGopUnidadFunc);
            return (long) q.getSingleResult();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<ServbusIdTipoVehiculo> getServbusAndIdTipoVehiculo(String fecha, int idGopUnidadFunc) {
        try {
            String sql = "SELECT DISTINCT\n"
                    + "    servbus, id_vehiculo_tipo\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    fecha = ?1\n"
                    + "        AND id_gop_unidad_funcional = ?2\n"
                    + "        AND servbus IS NOT NULL";
            Query q = em.createNativeQuery(sql, "servbusIdVehiculoTipoDTO");
            q.setParameter(1, fecha);
            q.setParameter(2, idGopUnidadFunc);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgTc> serviciosPrgTcPendientesPorOperador(int idEmpleado, Date fecha) {
        String sql = "SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "        INNER JOIN\n"
                + "    empleado e ON prg.id_empleado = e.id_empleado\n"
                + "        INNER JOIN\n"
                + "    prg_tarea t ON t.id_prg_tarea = prg.id_task_type\n"
                + "WHERE\n"
                + "    e.id_empleado = ?1\n"
                + "        AND prg.fecha = ?2\n"
                + "        AND (t.op_disponible <> 1\n"
                + "        OR t.op_disponible IS NULL)\n"
                + "        AND prg.estado_operacion <> ?3\n"
                + "        AND prg.estado_operacion <> ?4\n"
                + "ORDER BY prg.time_origin ASC;";

        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, idEmpleado);
        q.setParameter(2, Util.dateFormat(fecha));
        q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(4, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
        return q.getResultList();
    }

//    @Override
//    public PrgTc validarServicioEnEjecucionByHoraForOperador(String hora, int idEmpleado, Date fecha) {
//        try {
//            Query q = em.createNativeQuery("SELECT \n"
//                    + "    prg.*\n"
//                    + "FROM\n"
//                    + "    prg_tc prg\n"
//                    + "        INNER JOIN\n"
//                    + "    empleado e ON prg.id_empleado = e.id_empleado\n"
//                    + "        INNER JOIN\n"
//                    + "    prg_tarea t ON t.id_prg_tarea = prg.id_task_type\n"
//                    + "WHERE\n"
//                    + "((TIME_TO_SEC(?3) > TIME_TO_SEC(prg.time_origin))\n"
//                    + "        AND (TIME_TO_SEC(?3) < TIME_TO_SEC(prg.time_destiny)))\n"
//                    + "        AND prg.sercon NOT LIKE '%DP%'\n"
//                    + "        AND prg.sercon NOT LIKE 'master%'\n"
//                    + "        AND prg.sercon NOT LIKE 'Desc%'\n"
//                    + "        AND (t.op_disponible <> 1\n"
//                    + "        OR t.op_disponible IS NULL)\n"
//                    + "        AND e.id_empleado = ?1\n"
//                    + "        AND prg.fecha = ?2\n"
//                    + "        AND prg.estado_operacion <> 5\n"
//                    + "        AND prg.estado_operacion <> 8\n"
//                    + "ORDER BY prg.time_origin ASC\n"
//                    + "LIMIT 1;", PrgTc.class);
//            q.setParameter(1, idEmpleado);
//            q.setParameter(2, Util.dateFormat(fecha));
//            q.setParameter(3, hora);
//            return (PrgTc) q.getSingleResult();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    @Override
    public List<PrgTc> serviciosPendientesPorVehiculo(String cod, Date fecha, int idGopUnidadFuncional) {
        String sql = "SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "        INNER JOIN\n"
                + "    vehiculo v ON prg.id_vehiculo = v.id_vehiculo\n"
                + "WHERE\n"
                + "    v.codigo LIKE '%" + cod + "'\n"
                + "        AND prg.fecha = ?2\n"
                + "        AND prg.estado_operacion <> ?5\n"
                + "        AND prg.estado_operacion <> ?6\n"
                + "ORDER BY prg.time_origin ASC;";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(2, Util.dateFormat(fecha));
        q.setParameter(4, idGopUnidadFuncional);
        q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(6, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
        return q.getResultList();
    }

//    @Override
//    public PrgTc validarServicioEnEjecucionByHoraAndVehiculo(String hora, String cod, Date fecha, int idGopUnidadFuncional) {
//        try {
//            String sql_unida_func = idGopUnidadFuncional == 0 ? " "
//                    : "      AND prg.id_gop_unidad_funcional = ?4\n";
//            Query q = em.createNativeQuery("SELECT \n"
//                    + "    prg.*\n"
//                    + "FROM\n"
//                    + "    prg_tc prg\n"
//                    + "        INNER JOIN\n"
//                    + "    vehiculo v ON prg.id_vehiculo = v.id_vehiculo\n"
//                    + "WHERE\n"
//                    + "((TIME_TO_SEC(?3) > TIME_TO_SEC(prg.time_origin))\n"
//                    + "        AND (TIME_TO_SEC(?3) < TIME_TO_SEC(prg.time_destiny)))"
//                    + "        AND v.codigo LIKE '%" + cod + "'\n"
//                    + "        AND prg.fecha = ?2\n"
//                    + "        AND prg.estado_operacion <> 5\n"
//                    + "        AND prg.estado_operacion <> 8\n"
//                    + sql_unida_func
//                    + "ORDER BY prg.time_origin ASC\n"
//                    + "LIMIT 1;", PrgTc.class);
//            q.setParameter(1, cod);
//            q.setParameter(2, Util.dateFormat(fecha));
//            q.setParameter(3, hora);
//            q.setParameter(4, idGopUnidadFuncional);
//            return (PrgTc) q.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
    /**
     * Método responsable de consultar un servicio (PrgTc) de acuerto a un
     * sercon, fecha, a un tiempo de origen. Que el servbus no se nulo, el
     * vehículo no sea nulo que empleado no sea nulo y ordenado por el tiempo de
     * origen de forma acsendente para así traer el primer servicio que se
     * ejecutará.
     *
     * @param sercon
     * @param fechaConsulta
     * @param timeOrigin
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public PrgTc findPrgTcForDeleteAndChangeVehiculo(String sercon, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND prg.id_gop_unidad_funcional = ?4\n";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    prg.sercon = ?1 AND prg.fecha = ?2\n"
                    + "        AND prg.time_origin >= ?3\n"
                    + "        AND prg.servbus IS NOT NULL\n"
                    + "        AND prg.id_vehiculo IS NOT NULL\n"
                    + "        AND prg.id_empleado IS NOT NULL\n"
                    + sql_unida_func
                    + "ORDER BY prg.time_origin ASC\n"
                    + "LIMIT 1;", PrgTc.class);
            q.setParameter(1, sercon);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            q.setParameter(3, timeOrigin);
            q.setParameter(4, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findPrgTcAsignacionOperador(String sercon, Date fechaConsulta, String timeOrigin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND prg.id_gop_unidad_funcional = ?4\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    prg.*\n"
                    + "FROM\n"
                    + "    prg_tc prg\n"
                    + "WHERE\n"
                    + "    prg.sercon = ?1 AND prg.fecha = ?2\n"
                    + "        AND prg.time_origin >= ?3\n"
                    + "        AND prg.servbus IS NOT NULL\n"
                    + "        AND prg.id_empleado IS NOT NULL\n"
                    + sql_unida_func
                    + "ORDER BY prg.time_origin ASC\n"
                    + "LIMIT 1;", PrgTc.class);
            q.setParameter(1, sercon);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            q.setParameter(3, timeOrigin);
            q.setParameter(4, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int desasignarOp(PrgTc p) {
        try {
            Query q = em.createNativeQuery("UPDATE prg_tc tc \n"
                    + "SET \n"
                    + "    tc.id_empleado = ?9,\n"
                    + "    tc.observaciones = ?1,\n"
                    + "    tc.username = ?2,\n"
                    + "    tc.old_empleado = ?3,\n"
                    + "    tc.control = ?4,\n"
                    + "    tc.modificado = ?5,\n"
                    + "    tc.id_prg_tc_responsable = ?6,\n"
                    + "    tc.id_prg_clasificacion_motivo = ?10\n"
                    + "WHERE\n"
                    + "    tc.id_prg_tc = ?7 AND tc.fecha = ?8;");
            q.setParameter(1, p.getObservaciones());
            q.setParameter(2, p.getUsername());
            q.setParameter(3, p.getOldEmpleado());
            q.setParameter(4, p.getControl());
            q.setParameter(5, new Date());
            q.setParameter(6, p.getIdPrgTcResponsable().getIdPrgTcResponsable());
            q.setParameter(7, p.getIdPrgTc());
            q.setParameter(8, Util.dateFormat(p.getFecha()));
            if (p.getIdEmpleado() == null) {
                q.setParameter(9, null);
            } else {
                q.setParameter(9, p.getIdEmpleado().getIdEmpleado());
            }
            q.setParameter(10, p.getIdPrgClasificacionMotivo() == null ? null : p.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int reasignarVe(PrgTc p) {
        try {
            Query q = em.createNativeQuery("UPDATE prg_tc tc \n"
                    + "SET \n"
                    + "    tc.id_vehiculo = ?1,\n"
                    + "    tc.observaciones = ?2,\n"
                    + "    tc.username = ?3,\n"
                    + "    tc.old_vehiculo = ?4,\n"
                    + "    tc.control = ?5,\n"
                    + "    tc.modificado = ?6,\n"
                    + "    tc.id_prg_tc_responsable = ?7,\n"
                    + "    tc.id_prg_clasificacion_motivo = ?10 \n"
                    + "WHERE\n"
                    + "    tc.id_prg_tc = ?8  AND tc.fecha = ?9;");
            q.setParameter(1, p.getIdVehiculo() != null ? p.getIdVehiculo().getIdVehiculo() : null);
            q.setParameter(2, p.getObservaciones());
            q.setParameter(3, p.getUsername());
            q.setParameter(4, p.getOldVehiculo());
            q.setParameter(5, p.getControl());
            q.setParameter(6, new Date());
            q.setParameter(7, p.getIdPrgTcResponsable().getIdPrgTcResponsable());
            q.setParameter(8, p.getIdPrgTc());
            q.setParameter(9, Util.toDate(Util.dateFormat(p.getFecha())));
            q.setParameter(10, p.getIdPrgClasificacionMotivo() == null ? null : p.getIdPrgClasificacionMotivo().getIdPrgClasificacionMotivo());
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//    @Override
//    public PrgTcResumen validarConciliado(Date fecha) {
//        try {
//            Query q = em.createNativeQuery("SELECT p.* FROM prg_tc_resumen p "
//                    + "WHERE p.fecha = ?1 AND (p.conciliado = 0 or p.conciliado is null) LIMIT 1;", PrgTcResumen.class);
//            q.setParameter(1, Util.dateFormat(fecha));
//            return (PrgTcResumen) q.getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
//    }
    @Override
    public KmsPerdidosArt getEliminadosArtCtrl(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND p.id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT\n"
                    + "	count(if(p.id_prg_tc_responsable IN (1, 3), 1, null)) as count_operaciones,\n"
                    + "	SUM(if(p.id_prg_tc_responsable IN (1, 3), p.distance, 0)) as operaciones,\n"
                    + "	count(if(p.id_prg_tc_responsable IN (2), 1, null)) as count_mtto,\n"
                    + "	SUM(if(p.id_prg_tc_responsable IN (2), p.distance, 0)) as mtto,\n"
                    + "	count(if(p.id_prg_tc_responsable NOT IN (1, 2, 3), 1, null)) as count_otros,\n"
                    + "	SUM(if(p.id_prg_tc_responsable NOT IN (1, 2, 3), p.distance, 0)) as otros\n"
                    + "FROM\n"
                    + "	prg_tc p\n"
                    + "inner join prg_tarea t on\n"
                    + "	p.id_task_type = t.id_prg_tarea\n"
                    + "WHERE\n"
                    + "	p.fecha = ?1\n"
                    + "	AND t.sum_distancia = 1\n"
                    + "	AND p.estado_operacion = 5\n"
                    + "	AND p.distance > 0\n"
                    + "	AND t.comercial = 1\n"
                    + "	AND t.id_prg_tarea NOT IN (2,3,4)\n"
                    + sql_unida_func
                    + "	AND p.id_vehiculo_tipo = 1;";
            Query q = em.createNativeQuery(sql, "KmsPerdidosArtMapping");
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idGopUnidadFuncional);
            return (KmsPerdidosArt) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KmsPerdidosBi getEliminadosBiCtrl(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND p.id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT\n"
                    + "	count(if(p.id_prg_tc_responsable IN (1, 3), 1, null)) as count_operaciones,\n"
                    + "	SUM(if(p.id_prg_tc_responsable IN (1, 3), p.distance, 0)) as operaciones,\n"
                    + "	count(if(p.id_prg_tc_responsable IN (2), 1, null)) as count_mtto,\n"
                    + "	SUM(if(p.id_prg_tc_responsable IN (2), p.distance, 0)) as mtto,\n"
                    + "	count(if(p.id_prg_tc_responsable NOT IN (1, 2, 3), 1, null)) as count_otros,\n"
                    + "	SUM(if(p.id_prg_tc_responsable NOT IN (1, 2, 3), p.distance, 0)) as otros\n"
                    + "FROM\n"
                    + "	prg_tc p\n"
                    + "inner join prg_tarea t on\n"
                    + "	p.id_task_type = t.id_prg_tarea\n"
                    + "WHERE\n"
                    + "	p.fecha = ?1\n"
                    + "	AND t.sum_distancia = 1\n"
                    + "	AND p.estado_operacion = 5\n"
                    + "	AND p.distance > 0\n"
                    + "	AND t.comercial = 1\n"
                    + "	AND t.id_prg_tarea NOT IN (2,3,4)\n"
                    + sql_unida_func
                    + "	AND p.id_vehiculo_tipo = 2;";
            Query q = em.createNativeQuery(sql, "KmsPerdidosBiMapping");
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idGopUnidadFuncional);
            return (KmsPerdidosBi) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public KmsAdicionalesCtrl getAdicionalesCtrl(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND p.id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT\n"
                    + "	count(if(p.id_vehiculo_tipo = 1,1,null)) as count_adicionales_art,\n"
                    + "	count(if(p.id_vehiculo_tipo = 2,1,null)) as count_adicionales_bi\n"
                    + "FROM\n"
                    + "	prg_tc p\n"
                    + "INNER JOIN prg_tarea t on\n"
                    + "	p.id_task_type = t.id_prg_tarea\n"
                    + "WHERE\n"
                    + "	p.fecha = ?1\n"
                    + "	AND t.sum_distancia = 1\n"
                    + "	AND t.comercial = 1\n"
                    + sql_unida_func
                    + "	AND p.estado_operacion in (3,4,6)";
            Query q = em.createNativeQuery(sql, "KmsAdicionalesCtrlMapping");
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idGopUnidadFuncional);
            return (KmsAdicionalesCtrl) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgTc> obtenerInoperativos(Date fecha_inicio, Date fecha_fin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?3\n";
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "WHERE\n"
                + "    tc.id_empleado IS NOT NULL\n"
                + "        AND tc.fecha BETWEEN ?1 AND ?2\n"
                + sql_unida_func
                + "        AND tc.sercon LIKE 'Inoperativo_TM%'\n"
                + "        OR 'Sancion%';";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha_inicio));
        q.setParameter(2, Util.dateFormat(fecha_fin));
        q.setParameter(3, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> findEntradasMtto(Date fecha, int idpatio, int idTipoVehiculo) {
        try {
            String tipoVehiculo = " ";
            if (idTipoVehiculo != 0) {
                tipoVehiculo = "        AND tc.id_vehiculo_tipo = ?3\n";
            }
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.fecha = ?1\n"
                    + "        AND tc.to_stop = ?2\n"
                    + tipoVehiculo + "\n"
                    + "        AND TIME_TO_SEC(time_destiny) BETWEEN TIME_TO_SEC(DATE_ADD(NOW(), INTERVAL - 1 HOUR)) AND TIME_TO_SEC(DATE_ADD(NOW(), INTERVAL + 1 HOUR))\n"
                    + "        AND tc.servbus IS NOT NULL\n"
                    + "        AND tc.estado_operacion NOT IN (5 , 8, 99)\n"
                    + "ORDER BY tc.time_destiny ASC", PrgTc.class);
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idpatio);
            q.setParameter(3, idTipoVehiculo);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error" + e.getStackTrace());
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgTc> findSalidaMtto(Date fecha, int idpatio, int idTipoVehiculo) {
        try {
            String tipoVehiculo = " ";
            if (idTipoVehiculo != 0) {
                tipoVehiculo = "        AND tc.id_vehiculo_tipo = ?3\n";
            }
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.fecha = ?1\n"
                    + "        AND tc.from_stop = ?2\n"
                    + tipoVehiculo + "\n"
                    + "        AND TIME_TO_SEC(time_origin) BETWEEN TIME_TO_SEC(DATE_ADD(NOW(), INTERVAL - 5 HOUR)) AND TIME_TO_SEC(DATE_ADD(NOW(), INTERVAL + 1 HOUR))\n"
                    + "        AND tc.servbus IS NOT NULL\n "
                    + "and tc.id_vehiculo is not null "
                    + "        AND tc.estado_operacion NOT IN (5 , 8, 99)\n"
                    + "ORDER BY tc.time_origin ASC", PrgTc.class);
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idpatio);
            q.setParameter(3, idTipoVehiculo);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error" + e.getStackTrace());
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgTc> changeOneToOneReturnPrgTcAfectados(PrgTc prgtc1, PrgTc prgtc2) {
        int idGopFuncional = prgtc1.getIdGopUnidadFuncional() == null ? 0 : prgtc1.getIdGopUnidadFuncional().getIdGopUnidadFuncional();
        String sql_uf = idGopFuncional == 0 ? "" : "            AND prg.id_gop_unidad_funcional = ?6\n";
        List<PrgTc> lista1 = new ArrayList<>();
        List<PrgTc> lista2 = new ArrayList<>();
        List<PrgTc> listaReturn = new ArrayList<>();
        Query q = em.createNativeQuery("SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "WHERE\n"
                + "    prg.id_vehiculo = ?2\n"
                + "        AND prg.time_origin >= ?3\n"
                + "        AND prg.fecha = ?4\n"
                + sql_uf
                + "        AND prg.servbus = ?5;", PrgTc.class);
        q.setParameter(2, prgtc2.getIdVehiculo().getIdVehiculo());
        q.setParameter(3, prgtc1.getTimeOrigin());
        q.setParameter(4, Util.dateFormat(prgtc2.getFecha()));
        q.setParameter(5, prgtc2.getServbus());
        q.setParameter(6, idGopFuncional);
        lista1 = q.getResultList();

        Query qq = em.createNativeQuery("SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "WHERE\n"
                + "    prg.id_vehiculo = ?2 \n"
                + "        AND prg.time_origin >= ?3 \n"
                + "        AND prg.fecha = ?4 \n"
                + sql_uf
                + "        AND prg.servbus = ?5;", PrgTc.class);
        qq.setParameter(2, prgtc1.getIdVehiculo().getIdVehiculo());
        qq.setParameter(3, prgtc1.getTimeOrigin());
        qq.setParameter(4, Util.dateFormat(prgtc1.getFecha()));
        qq.setParameter(5, prgtc1.getServbus());
        qq.setParameter(6, idGopFuncional);
        lista2 = qq.getResultList();

        if (lista1 != null) {
            listaReturn.addAll(lista1);
        }
        if (lista2 != null) {
            listaReturn.addAll(lista2);
        }
        return listaReturn;

    }

    @Override
    public List<PrgTc> prgTcUnoAUnoReturnPrgTcAfectados(String serv, Date fecha, String timeOrigen, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND p.id_gop_unidad_funcional = ?5\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    p.*\n"
                + "FROM\n"
                + "    prg_tc p\n"
                + "WHERE\n"
                + "    p.servbus = ?2 AND p.fecha = ?3\n"
                + sql_uf
                + "        AND p.time_origin >= ?4;", PrgTc.class);
        q.setParameter(2, serv);
        q.setParameter(3, Util.dateFormat(fecha));
        q.setParameter(4, timeOrigen);
        q.setParameter(5, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> rutasEjecutadas(Date fechaDesde, Date fechaHasta, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND prg.id_gop_unidad_funcional = ?3\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    prg.*\n"
                + "FROM\n"
                + "    prg_tc prg\n"
                + "WHERE\n"
                + "    prg.fecha BETWEEN ?1 AND ?2\n"
                + "        AND prg.id_ruta IS NOT NULL\n"
                + "        AND prg.id_vehiculo IS NOT NULL\n"
                + sql_uf
                + "ORDER BY prg.id_vehiculo , prg.id_ruta asc", PrgTc.class);
        q.setParameter(1, Util.toDate(Util.dateFormat(fechaDesde)));
        q.setParameter(2, Util.toDate(Util.dateFormat(fechaHasta)));
        q.setParameter(3, idGopUnidadFunc);

        return q.getResultList();
    }

    @Override
    public List<PrgTc> reporteVehiculoOperador(Date desde, Date hasta, String codVehiculo, String codEmpleado) {
        try {
            String empleado_inner = "";
            String empleado_where = "";
            String vehiculo_inner = "";
            String vehiculo_where = "";
            if (!codEmpleado.isEmpty()) {
                empleado_inner = "        INNER JOIN\n"
                        + "    empleado e ON e.id_empleado = tc.id_empleado\n";
                empleado_where = "        AND e.codigo_tm = ?4";
            }
            if (!codVehiculo.isEmpty()) {
                vehiculo_inner = "        INNER JOIN\n"
                        + "    vehiculo v ON v.id_vehiculo = tc.id_vehiculo\n";

                vehiculo_where = "        AND v.codigo = ?3\n";
            }
            String sql = "SELECT \n"
                    + "    tc.id_prg_tc as id_prg_tc,\n"
                    + "    tc.fecha as fecha,\n"
                    + "    tc.id_empleado as id_empleado,\n"
                    + "    tc.id_vehiculo as id_vehiculo\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + empleado_inner + vehiculo_inner
                    + "WHERE\n"
                    + "    tc.fecha BETWEEN ?1 AND ?2 AND \n"
                    + "    tc.id_empleado IS NOT NULL AND \n"
                    + "    tc.id_vehiculo IS NOT NULL\n"
                    + vehiculo_where + empleado_where
                    + "\nGROUP BY  2,3,4;";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, codVehiculo);
            q.setParameter(4, codEmpleado);
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ConsolidadServicios> getConsolidadoPorHora(Date fecha) {
        try {
            String sql = "SELECT \n"
                    + "    HOUR(time_origin) AS id,\n"
                    + "    (SUM(CASE\n"
                    + "            WHEN estado_operacion IN (0 , 1, 2, 5) THEN distance\n"
                    + "        END) / 1000) AS programado,\n"
                    + "    (IFNULL(SUM(CASE\n"
                    + "                    WHEN estado_operacion IN (5) THEN distance\n"
                    + "                END),\n"
                    + "                0) / 1000) AS eliminado,\n"
                    + "    (IFNULL(SUM(CASE\n"
                    + "                    WHEN estado_operacion IN (3) THEN distance\n"
                    + "                END),\n"
                    + "                0) / 1000) AS adicional\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    fecha = ?1\n"
                    + "        AND servbus IS NOT NULL\n"
                    + "GROUP BY 1\n"
                    + "ORDER BY 1 ASC;";
            Query query = em.createNativeQuery(sql, "consolidadServiciosMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<ConsolidadServicios> getConsolidadoPorDia(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    DAYOFMONTH(fecha) AS id,\n"
                    + "    (SUM(CASE\n"
                    + "        WHEN estado_operacion IN (0 , 1, 2, 5) THEN distance\n"
                    + "    END) / 1000) AS programado,\n"
                    + "    (IFNULL(SUM(CASE\n"
                    + "                WHEN estado_operacion IN (5) THEN distance\n"
                    + "            END),\n"
                    + "            0) / 1000) AS eliminado,\n"
                    + "    (IFNULL(SUM(CASE\n"
                    + "                WHEN estado_operacion IN (3 , 4, 6) THEN distance\n"
                    + "            END),\n"
                    + "            0) / 1000) AS adicional\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN ?1 AND ?2\n"
                    + "        AND servbus IS NOT NULL\n"
                    + "GROUP BY 1\n"
                    + "ORDER BY 1 ASC";
            Query query = em.createNativeQuery(sql, "consolidadServiciosMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int removeByDate(Date d, int idGopUnidadFunc) {
        try {
            Query q = em.createNativeQuery("{call borrarPrg(?1,?2)}");
            q.setParameter(1, Util.dateFormat(d));
            q.setParameter(2, idGopUnidadFunc);
            return q.executeUpdate();
        } catch (Exception e) {
            return -2;
        }
    }

    @Override
    public int updateByDate(Date d, int idGopUnidadFunc) {
        try {
            String sql = "UPDATE prg_tc \n"
                    + "SET \n"
                    + "    id_vehiculo = NULL\n"
                    + "WHERE\n"
                    + "    fecha = DATE(?1) AND servbus IS NOT NULL\n"
                    + "        AND id_vehiculo IS NOT NULL\n"
                    + "        AND servbus NOT LIKE '%AD'\n"
                    + "        AND id_gop_unidad_funcional = ?2";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            q.setParameter(2, idGopUnidadFunc);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<PrgTc> findByServBus(String cServbus, Date d, Integer idEmpleado) {
        try {
            String sql = "SELECT * "
                    + "FROM prg_tc "
                    + "WHERE servbus = ?1 "
                    + "AND id_empleado = ?2 "
                    + "AND fecha = ?3";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, cServbus);
            q.setParameter(2, idEmpleado);
            q.setParameter(3, Util.dateFormat(d));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Realiza el cambio de operadores en la tabla prg_tc al aprobar un cambio
     * de turnos.
     *
     * @param idEmpleado
     * @param oldEmpleado
     * @param sercon
     * @param fecha
     */
    @Override
    public void realizarCambioOperacion(Integer idEmpleado, Integer oldEmpleado, String sercon, Date fecha) {
        try {
            String sql = "update prg_tc set id_empleado=?1,old_empleado=?2 where sercon=?3 and fecha=?4;";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idEmpleado);
            query.setParameter(2, oldEmpleado);
            query.setParameter(3, sercon);
            query.setParameter(4, Util.dateFormat(fecha));

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<InformeOperacion> InformeOperacionParaMtto() {
        String sql = "select * from vista_reporte_información_operación;";
        Query q = em.createNativeQuery(sql, "InformeOperacionMapping");
        return q.getResultList();
    }

    @Override
    public List<NovedadesTQ04> getNovedadesTq04(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT * FROM novedades_tq04"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, "NovedadesTQ04Mapping");
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgTc> findServicioGenerico(String tabla, String servicio,
            String servBus, String sercon, String codigoOperador, String vehiculoC,
            Date fechaConsulta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND tc.id_gop_unidad_funcional = ?7\n";

            String sql_tabla = MovilidadUtil.getWithoutSpaces(tabla).isEmpty() ? "" : "        AND tc.tabla = ?2\n";
            String sql_sercon = MovilidadUtil.getWithoutSpaces(sercon).isEmpty() ? "" : "        AND tc.sercon = ?4\n";
            String sql_servbus = MovilidadUtil.getWithoutSpaces(servBus).isEmpty() ? "" : "        AND tc.servbus = ?3\n";
            String sql_servicio = MovilidadUtil.getWithoutSpaces(servicio).isEmpty() ? "" : "        AND pt.tarea LIKE '%" + servicio + "%'\n";
            String sql_operador = MovilidadUtil.getWithoutSpaces(codigoOperador).isEmpty() ? "" : "                AND e.codigo_tm = ?6\n";
            String sql_operador_inner = MovilidadUtil.getWithoutSpaces(codigoOperador).isEmpty() ? "" : "        INNER JOIN\n"
                    + "    empleado e ON e.id_empleado = tc.id_empleado\n";
            String sql_vehiculo = MovilidadUtil.getWithoutSpaces(vehiculoC).isEmpty() ? "" : "                AND v.codigo LIKE '%" + vehiculoC + "'\n";
            String sql_vehiculo_inner = MovilidadUtil.getWithoutSpaces(vehiculoC).isEmpty() ? "" : "        INNER JOIN\n"
                    + "    vehiculo v ON v.id_vehiculo = tc.id_vehiculo\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_tarea pt ON pt.id_prg_tarea = tc.id_task_type\n"
                    + sql_vehiculo_inner
                    + sql_operador_inner
                    + "WHERE\n"
                    + "    tc.fecha = ?1\n"
                    + sql_tabla
                    + sql_servbus
                    + sql_sercon
                    + sql_vehiculo
                    + sql_servicio
                    + sql_operador
                    + sql_unida_func
                    + "        AND tc.id_task_type IS NOT NULL\n"
                    + "ORDER BY tc.time_origin ASC;", PrgTc.class);
            q.setParameter(1, Util.dateFormat(fechaConsulta));
            q.setParameter(2, tabla);
            q.setParameter(3, servBus);
            q.setParameter(4, sercon);
            q.setParameter(5, vehiculoC);
            q.setParameter(6, codigoOperador);
            q.setParameter(7, idGopUnidadFuncional);
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Object obtenerKmRecorridosByOperador(Integer idEmpleado, String fecha, String hora) {
        try {
            String sql = "SELECT "
                    + "	SUM(distance) "
                    + "FROM "
                    + "	`prg_tc` "
                    + "WHERE "
                    + "	id_empleado = ?1 "
                    + "	AND DATE( fecha ) = ?2 "
                    + "	AND TIME(time_destiny < ?3) "
                    + "	AND estado_operacion NOT IN ( 5, 8, 99 ) "
                    + " AND estado_reg = 0";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, fecha);
            q.setParameter(3, hora);
            return q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgTc> obtenerConsultaProgramacion(Date fechaDesde, Date fechaHasta, String codOperador) {
        try {
            String sql = "SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_tc p\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON p.id_empleado = e.id_empleado\n"
                    + "WHERE\n"
                    + "    p.fecha BETWEEN ?1 AND ?2 AND e.codigo_tm = ?3\n"
                    + "        AND p.estado_reg = 0\n"
                    + "ORDER BY p.fecha,p.time_origin\n"
                    + ";";
            Query query = em.createNativeQuery(sql, PrgTc.class);
            query.setParameter(1, Util.dateFormat(fechaDesde));
            query.setParameter(2, Util.dateFormat(fechaHasta));
            query.setParameter(3, codOperador);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public PrgTc findUltimoServicioProgramado(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND tc.id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.fecha = ?1\n"
                    + "        AND tc.estado_operacion IN (0 , 1, 2, 5)\n"
                    + "        AND tc.id_vehiculo > 0\n"
                    + sql_unida_func
                    + "ORDER BY tc.time_destiny DESC limit 1";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFuncional);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc findFirtOrEndServiceByIdEmpleado(int idEmpleado, Date fecha, String order) {
        try {
            String sql = "SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.fecha = ?1\n"
                    + "        AND tc.time_origin <> '00:00:00'\n"
                    + "        AND tc.id_empleado = ?2\n"
                    + "ORDER BY tc.time_destiny " + order + "\n"
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idEmpleado);
            q.setParameter(3, order);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findVehiculosSinPresentacion(Date fecha, Integer idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";
        String sql = "SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point psp ON tc.to_stop = psp.id_prg_stoppoint\n"
                + "WHERE\n"
                + "    tc.fecha = ?1\n"
                + "        AND psp.is_depot = 1\n"
                + "        AND tc.id_prg_tc NOT IN (SELECT \n"
                + "            m.id_prg_tc\n"
                + "        FROM\n"
                + "            my_app_confirm_depot_entry m\n"
                + "        WHERE\n"
                + "            DATE(m.fecha_hora) = DATE(?1)\n"
                + "                AND m.estado_reg = 0)\n"
                + "        AND (TIME_TO_SEC(tc.time_destiny) <= (TIME_TO_SEC(NOW()) + ((SELECT \n"
                + "            a.minutos\n"
                + "        FROM\n"
                + "            gop_alerta_presentacion_vehiculo a\n"
                + "        WHERE\n"
                + "            a.estado_reg = 0\n"
                + "        LIMIT 1) * 60)))\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + "        AND tc.id_vehiculo IS NOT NULL\n"
                + "        AND tc.estado_operacion NOT IN (5 , 8, 99)\n"
                + sql_unida_func
                + "ORDER BY tc.time_destiny ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> entradasPatio(Date fechaHora, int idGopUnidadFunc) {
        String fecha = Util.dateFormat(fechaHora);
        String iniTime = Util.startTimeByDate(fechaHora);
        String finTime = Util.endTimeByDate(fechaHora);
        String sql = "SELECT "
                + "    tc.* "
                + "FROM "
                + "    prg_tc tc "
                + "        INNER JOIN "
                + "    (SELECT psp.* FROM prg_stop_point psp WHERE psp.is_depot = 1) sp ON sp.id_prg_stoppoint = tc.to_stop "
                + "WHERE "
                + "    tc.fecha = DATE(?1) "
                + "        AND TIME(tc.time_destiny) BETWEEN TIME(?2) AND TIME(?3) "
                + "        AND tc.servbus IS NOT NULL ";
        if (idGopUnidadFunc != 0) {
            sql = sql + "AND tc.id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
        }
        sql = sql + "ORDER BY TIME(tc.time_destiny) ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, fecha);
        q.setParameter(2, iniTime);
        q.setParameter(3, finTime);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> salidasPatio(Date fechaHora, int idGopUnidadFunc) {
        String fecha = Util.dateFormat(fechaHora);
        String iniTime = Util.startTimeByDate(fechaHora);
        String finTime = Util.endTimeByDate(fechaHora);
        String sql = "SELECT "
                + "    tc.* "
                + "FROM "
                + "    prg_tc tc "
                + "        INNER JOIN "
                + "    (SELECT psp.* FROM prg_stop_point psp WHERE psp.is_depot = 1) sp ON sp.id_prg_stoppoint = tc.to_stop "
                + "WHERE "
                + "    tc.fecha = DATE(?1) "
                + "        AND TIME(tc.time_origin) BETWEEN TIME(?2) AND TIME(?3) "
                + "        AND tc.servbus IS NOT NULL ";
        if (idGopUnidadFunc != 0) {
            sql = sql + "AND tc.id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
        }
        sql = sql + "ORDER BY TIME(tc.time_origin) ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, fecha);
        q.setParameter(2, iniTime);
        q.setParameter(3, finTime);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> getListPrgTcByIdVehiculoAndFechaAndHoraMayorIgual(Integer idVehiculo, Date fechaHora, int idGopUnidadFunc) {
        String sql = "SELECT  "
                + "   * "
                + "FROM "
                + "    prg_tc "
                + "WHERE "
                + "    id_vehiculo = ?1 "
                + "        AND DATE(fecha) = DATE(?2) "
                + "        AND TIME(time_origin) >= TIME(?3) ";
        if (idGopUnidadFunc != 0) {
            sql = sql + "AND id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
        }
        sql = sql + "ORDER BY time_origin ASC";
        String fecha = Util.dateFormat(fechaHora);
        String hora = Util.dateToTimeHHMMSS(fechaHora);
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, idVehiculo);
        q.setParameter(2, fecha);
        q.setParameter(3, hora);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> findServicesByVehiculo(Integer idVehiculo, Date fecha, int idGopUnidadFunc) {
        String sql = "SELECT  "
                + "    * "
                + "FROM "
                + "    prg_tc "
                + "WHERE "
                + "    id_vehiculo = ?1 "
                + "        AND DATE(fecha) = DATE(?2) ";
        if (idGopUnidadFunc != 0) {
            sql = sql + "AND id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
        }
        sql = sql + "ORDER BY time_origin ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, idVehiculo);
        q.setParameter(2, Util.dateFormat(fecha));
        return q.getResultList();
    }

    @Override
    public PrgTc getPrgTcByFechaAndTimeOriginAndStopPointIsDepotEntry(Integer idVehiculo,
            Date fecha,
            String hora,
            int idGopUnidadFunc) {
        try {
            String sql = "SELECT "
                    + "    tc.* "
                    + "FROM "
                    + "    prg_tc tc "
                    + "        INNER JOIN "
                    + "    (SELECT  "
                    + "        * "
                    + "    FROM "
                    + "        prg_stop_point "
                    + "    WHERE "
                    + "        is_depot = 1 AND estado_reg = 0) s ON tc.to_stop = s.id_prg_stoppoint "
                    + "WHERE "
                    + "    DATE(fecha) = DATE(?1) "
                    + "        AND TIME(tc.time_destiny) <= TIME(?2) "
                    + "        AND tc.id_vehiculo = ?3 "
                    + "        AND tc.estado_reg = 0 ";
            if (idGopUnidadFunc != 0) {
                sql = sql + "AND id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
            }
            sql = sql + "ORDER BY time_destiny DESC "
                    + "LIMIT 1";
            String fechaS = Util.dateFormat(fecha);
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, fechaS);
            q.setParameter(2, hora);
            q.setParameter(3, idVehiculo);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc getPrgTcByFechaAndTimeOriginAndStopPointIsDepotExit(Integer idVehiculo, Date fecha, String hora, int idGopUnidadFunc) {
        try {
            String sql = "SELECT  "
                    + "    tc.* "
                    + "FROM "
                    + "    prg_tc tc "
                    + "        INNER JOIN "
                    + "    (SELECT  "
                    + "        * "
                    + "    FROM "
                    + "        prg_stop_point "
                    + "    WHERE "
                    + "        is_depot = 1 AND estado_reg = 0) s ON s.id_prg_stoppoint = tc.from_stop "
                    + "WHERE "
                    + "    fecha = ?1 "
                    + "        AND TIME(tc.time_origin) >= TIME(?2) "
                    + "        AND tc.id_vehiculo = ?3 "
                    + "        AND tc.estado_reg = 0 ";
            if (idGopUnidadFunc != 0) {
                sql = sql + "AND id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
            }
            sql = sql + "ORDER BY time_origin ASC "
                    + "LIMIT 1";
            String fechaS = Util.dateFormat(fecha);
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, fechaS);
            q.setParameter(2, hora);
            q.setParameter(3, idVehiculo);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc getPrgTcByIdEmpleadoTmAndFechaByIdGopUF(Integer idEmpleado, Date fecha, String hora, int idGopUnidadFunc) {
        try {
            String sql = "SELECT "
                    + "    * "
                    + "FROM "
                    + "    prg_tc "
                    + "WHERE "
                    + "    fecha = ?1 "
                    + "        AND id_empleado = ?2 "
                    + "        AND id_task_type IS NOT NULL "
                    + "        AND TIME(time_origin) >= TIME(?3) "
                    + "        AND estado_reg = 0 ";
            if (idGopUnidadFunc != 0) {
                sql = sql + "AND id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
            }
            sql = sql + "LIMIT 1";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idEmpleado);
            q.setParameter(3, hora);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> getPrgTcByIdEmpleadoAndFechaAndIdGopUF(Integer idEmpleado, Date fecha, int idGopUnidadFunc) {
        String sql = "SELECT "
                + "    p.* "
                + "FROM "
                + "    prg_tc p "
                + "        INNER JOIN "
                + "    (SELECT  "
                + "        * "
                + "    FROM "
                + "        empleado "
                + "    WHERE "
                + "        estado_reg = 0 AND id_empleado = ?1 "
                + "            AND id_empleado_estado = 1) e ON p.id_empleado = e.id_empleado "
                + "WHERE "
                + "    DATE(p.fecha) = DATE(?2) "
                + "        AND p.estado_reg = 0 "
                + "        AND p.estado_operacion not in(9) ";
        if (idGopUnidadFunc != 0) {
            sql = sql + "AND p.id_gop_unidad_funcional = " + idGopUnidadFunc + " ";
        }
        sql = sql + "ORDER BY DATE(p.fecha) , TIME(p.time_origin)";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, idEmpleado);
        q.setParameter(2, Util.dateFormat(fecha));
        return q.getResultList();
    }

    @Override
    public List<ServiciosPorSalir> findServiciosForGestionTaller() {
        String sql = "SELECT \n"
                + "    vv.codigo as codigoVehiculo,\n"
                + "    ps.servbus,\n"
                + "    (SELECT \n"
                + "            pt.tarea\n"
                + "        FROM\n"
                + "            prg_tarea pt\n"
                + "        WHERE\n"
                + "            ps.id_task_type = pt.id_prg_tarea) AS tarea,\n"
                + "    MIN(ps.time_origin) AS timeOrigin,\n"
                + "    MIN(vv.placa) AS placaVehiculo\n"
                + "FROM\n"
                + "    (SELECT \n"
                + "        tc.*\n"
                + "    FROM\n"
                + "        prg_tc tc\n"
                + "    LEFT JOIN vehiculo v ON tc.id_vehiculo = v.id_vehiculo\n"
                + "    LEFT JOIN vehiculo_tipo_estado vte ON v.id_vehiculo_tipo_estado = vte.id_vehiculo_tipo_estado\n"
                + "    WHERE\n"
                + "        vte.restriccion_operacion = 1\n"
                + "            AND DATE(tc.fecha) = DATE(now())\n"
                + "            AND TIME(tc.time_origin) > TIME(NOW())) AS ps\n"
                + "        INNER JOIN\n"
                + "    vehiculo vv ON ps.id_vehiculo = vv.id_vehiculo\n"
                + "GROUP BY ps.id_vehiculo\n"
                + "ORDER BY 4 ASC";
        Query q = em.createNativeQuery(sql, "ServiciosPorSalirMapping");
        return q.getResultList();
    }

    @Override
    public List<SumEliminadoResponsableDTO> findReportSumEliminadoResponsable(Date fecha,
            int idGopUnidadFunc, Date fechaUltimoturno) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_hora_ultimo_cierre = fechaUltimoturno == null ? "AND TIME(tc.modificado) <= TIME(?1)"
                : "            AND TIME(tc.modificado) BETWEEN TIME(?3) AND TIME(?1)";

        String sql = "SELECT \n"
                + "    res.responsable AS responsable, COUNT(distinct tc.servbus) total\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    prg_tc_responsable res ON tc.id_prg_tc_responsable = res.id_prg_tc_responsable\n"
                + "        INNER JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                + "    tc.estado_operacion IN (2 , 5, 99)\n"
                + "        AND DATE(tc.fecha) = DATE(?1)\n"
                + "        AND tc.id_task_type IS NOT NULL\n"
                + "        AND pt.comercial = 1\n"
                + "        AND pt.sum_distancia = 1\n"
                + sql_hora_ultimo_cierre
                + sql_uf
                + "GROUP BY res.id_prg_tc_responsable\n"
                + "ORDER BY total DESC;";
        Query q = em.createNativeQuery(sql, "sumEliminadoResponsableDTO");

        q.setParameter(1, Util.dateTimeFormat(fecha));
        q.setParameter(2, idGopUnidadFunc);
        q.setParameter(3, Util.dateTimeFormat(fechaUltimoturno));
        return q.getResultList();
    }

    @Override
    public Long findReportSumServiciosSinOp(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        try {

            String sql = "SELECT \n"
                    + "    COUNT(DISTINCT tc.servbus)\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                    + "WHERE\n"
                    + "    DATE(tc.fecha) = DATE(?1)\n"
                    + "        AND tc.id_empleado IS NULL\n"
                    + "        AND TIME(tc.time_origin) >= TIME(?6)\n"
                    + "        AND pt.comercial = 1\n"
                    + "        AND tc.estado_operacion <> ?3\n"
                    + "        AND tc.estado_operacion <> ?4\n"
                    + "        AND tc.estado_operacion <> ?5\n"
                    + sql_uf
                    + "        AND pt.sum_distancia = 1";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFunc);
            q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
            q.setParameter(4, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
            q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
            q.setParameter(6, Util.dateToTimeHHMMSS(fecha));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public Long findReportSumServiciosSinVehiculo(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_uf_pt = idGopUnidadFunc == 0 ? "" : "            AND pt.id_gop_unidad_funcional = ?2\n";
        try {
            String sql = "SELECT \n"
                    + "    COUNT(DISTINCT tc.servbus) AS total\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    (SELECT \n"
                    + "        *\n"
                    + "    FROM\n"
                    + "        prg_tarea pt\n"
                    + "    WHERE\n"
                    + "        pt.sum_distancia = 1\n"
                    + sql_uf_pt
                    + "            AND pt.comercial = 1) res ON tc.id_task_type = res.id_prg_tarea\n"
                    + "WHERE\n"
                    + "    DATE(tc.fecha) = DATE(?1)\n"
                    + "        AND tc.id_vehiculo IS NULL\n"
                    + "        AND TIME(tc.time_origin) >= TIME(?6)\n"
                    + "        AND tc.estado_operacion <> ?3\n"
                    + "        AND tc.estado_operacion <> ?4\n"
                    + "        AND tc.estado_operacion <> ?5\n"
                    + sql_uf
                    + "        AND servbus IS NOT NULL";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFunc);
            q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
            q.setParameter(4, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
            q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
            q.setParameter(6, Util.dateToTimeHHMMSS(fecha));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public List<PrgTc> findServiciosEjecutadosByFechaAndUnidadFuncional(Date fecha, Integer idGopUnidadFuncional, Integer tipoServicio) {
        try {
            String sql_unida_func = (idGopUnidadFuncional == null || idGopUnidadFuncional == 0) ? "" : "  AND p.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            String sql_tipo_servicio = tipoServicio == null ? "" : tipoServicio == 1 ? "  AND p.estadoOperacion IN (0,1,NULL) \n" : "  AND p.estadoOperacion = " + tipoServicio + "\n";
            Query q = em.createQuery("SELECT p FROM PrgTc p WHERE p.fecha = :fecha "
                    + "AND p.idTaskType.sumDistancia = 1 "
                    + sql_unida_func
                    + sql_tipo_servicio
                    + "order by p.idVehiculo.codigo ASC, p.timeOrigin ASC", PrgTc.class);
            q.setParameter("fecha", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Long totalDisponibleByFechaAndUf(Date fecha, int idGopUnidadFuncional) {
        String sql_uf = idGopUnidadFuncional == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        try {
            String sql = "SELECT \n"
                    + "    COUNT(DISTINCT tc.id_empleado) AS total\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        LEFT JOIN\n"
                    + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON tc.id_empleado = e.id_empleado\n"
                    + "WHERE\n"
                    + "     DATE(tc.fecha) = DATE(?1)\n"
                    + "        AND pt.op_disponible = 1\n"
                    + "        AND estado_operacion <> ?3\n"
                    + "        AND tc.estado_reg = 0\n"
                    + "        AND e.id_empleado_estado = 1\n"
                    + "        AND TIME(tc.time_destiny) > TIME(?1)\n"
                    + sql_uf
                    + "ORDER BY time_origin ASC;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateTimeFormat(fecha));
            q.setParameter(2, idGopUnidadFuncional);
            q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_DISP_9);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public List<PrgTc> tareasDispoByIdEmpeladoAndFechaAndUnidadFunc(Integer idEmpleado, Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        LEFT JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                //                + "        tc.sercon LIKE '%DP%' AND\n"
                + "        tc.fecha = ?1\n"
                + "        AND pt.op_disponible = 1\n"
                + "        AND tc.id_empleado = ?3\n"
                + "        AND tc.estado_reg = 0\n"
                + sql_unida_func
                + "ORDER BY time_origin ASC;", PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, idEmpleado);
        return q.getResultList();
    }

    @Override
    public void cambioUnoAUnoServicosBySercon(PrgSercon sercon1, PrgSercon sercon2) {
        String sql = "UPDATE prg_tc tc \n"
                + "SET \n"
                + "    tc.id_empleado = ?1,\n"
                + "    tc.old_empleado = ?2\n"
                + "WHERE\n"
                + "    tc.fecha = ?3 AND tc.sercon = ?4\n"
                + "        AND tc.id_empleado = ?2;";

        Query q = em.createNativeQuery(sql);
        q.setParameter(1, sercon1.getIdEmpleado().getIdEmpleado());
        q.setParameter(2, sercon2.getIdEmpleado().getIdEmpleado());
        q.setParameter(3, Util.dateFormat(sercon1.getFecha()));
        q.setParameter(4, sercon1.getSercon());
        q.executeUpdate();

        Query qq = em.createNativeQuery(sql);
        qq.setParameter(1, sercon2.getIdEmpleado().getIdEmpleado());
        qq.setParameter(2, sercon1.getIdEmpleado().getIdEmpleado());
        qq.setParameter(3, Util.dateFormat(sercon2.getFecha()));
        qq.setParameter(4, sercon2.getSercon());
        qq.executeUpdate();

    }

    @Override
    public PrgTc currentServiceByCodeVehicle(String codeVehicle) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    vehiculo v ON v.id_vehiculo = tc.id_vehiculo\n"
                    + "WHERE\n"
                    + "    tc.fecha = DATE(NOW())\n"
                    + "        AND v.codigo = ?1\n"
                    + "        AND id_empleado IS NOT NULL\n"
                    + "        AND TIME(NOW()) BETWEEN TIME(time_origin) AND TIME(time_destiny)\n"
                    + "LIMIT 1", PrgTc.class);
            q.setParameter(1, codeVehicle);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrgTc firtServiceByIdEmpleado(Integer idEmpleado, Date fecha) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    id_empleado = ?1 AND fecha = DATE(?2)\n"
                    + "        AND id_task_type IS NOT NULL\n"
                    + "        AND from_stop IS NOT NULL\n"
                    + "        AND time_origin IS NOT NULL\n"
                    + "        AND estado_operacion NOT IN (5 , 8, 99)\n"
                    + "ORDER BY TIME(time_origin) ASC\n"
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fecha));
            return (PrgTc) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findAllIdGopUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                : "        p.id_gop_unidad_funcional = ?1 AND\n";
        String sql = "SELECT \n"
                + "    p.*\n"
                + "FROM\n"
                + "    prg_tc p\n"
                + "WHERE\n"
                + sql_unida_func
                + "         p.estado_reg =0\n";

        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public long buildShifts(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql = "{CALL buildShifts(?1, ?2, ?3)}";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFuncional);
        return q.executeUpdate();
    }

    @Override
    public long spDesasignarOp(Date desde, Date hasta, int idGopUnidadFuncional, String username) {
        String sql = "{CALL spDesasignarOp(?1, ?2, ?3, ?4)}";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFuncional);
        q.setParameter(4, username);
        return q.executeUpdate();
    }

    @Override
    public List<PrgTc> findServiciosByFechaAndIdEmpleado(Date d, Integer idEmpleado) {
        try {
            String sql = "SELECT * "
                    + "FROM prg_tc "
                    + "WHERE id_empleado = ?1 "
                    + "AND fecha = ?2";
            Query q = em.createNativeQuery(sql, PrgTc.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(d));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTc> findServiciosSinAsignarByFechaAndGopUf(Date d, int idGopUnidadFunc) {
        if (idGopUnidadFunc == 0) {
            return null;
        }
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_tc\n"
                + "WHERE\n"
                + "    id_vehiculo IS NULL\n"
                + "        AND fecha = DATE(?1)\n"
                + "        AND servbus IS NOT NULL\n"
                + "        AND estado_operacion NOT IN (5 , 8, 99)\n"
                + "        AND id_gop_unidad_funcional = ?2\n"
                + "ORDER BY time_origin ASC";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, Util.dateFormat(d));
        q.setParameter(2, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public Long totalServbusVehiculo(Date fecha, int idVehiculo, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?3\n";
        try {
            String sql = "SELECT \n"
                    + "    COUNT(DISTINCT tc.servbus) total\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "WHERE\n"
                    + "    tc.id_vehiculo = ?2\n"
                    + sql_uf
                    + "        AND fecha = ?1";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idVehiculo);
            q.setParameter(3, idGopUnidadFunc);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public List<ServbusPlanificadoDTO> findBusesPlanificadosByRangeDate(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND bp.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    bp.fecha,\n"
                + "    guf.id_gop_unidad_funcional AS id_uf,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    bp.nombre_tipo_vehiculo,\n"
                + "    bp.id_vehiculo_tipo,\n"
                + "    bp.total_programado,\n"
                + "    bp.total_asignado,\n"
                + "    bp.tipo_dia,\n"
                + "    bp.estacionalidad\n"
                + "FROM\n"
                + "    buses_planificados_view bp\n"
                + "    INNER JOIN\n"
                + "    gop_unidad_funcional guf ON bp.id_gop_unidad_funcional=guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    bp.fecha BETWEEN ?1 AND ?2"
                + sql_uf;

        Query q = em.createNativeQuery(sql, "ServbusPlanificadoDTOMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<ServbusPlanificadoDetalleDTO> findBusesPlanificadosDetalleByRangeDate(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "AND t1.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "t1.fecha, t1.servbus, t2.descripcion_tipo_vehiculo as tipologia,\n"
                + " CONCAT(t3.nombres,' ',t3.apellidos) as nombre, t3.codigo_tm,\n"
                + "t1.time_origin, t1.time_destiny, t4.codigo as codigo_bus, t5.nombre as uf,\n"
                + " t6.tarea as tipo_tarea, t1.distance as km, t7.descripcion as estado_operacion,\n"
                + "(case when t1.estado_operacion = 5 then (select st3.nombre_tipo_novedad as tipo_novedad from novedad_prg_tc st1\n"
                + "left join novedad st2 on st1.id_novedad = st2.id_novedad\n"
                + "left join novedad_tipo st3 on st2.id_novedad_tipo = st3.id_novedad_tipo\n"
                + "where st1.id_prg_tc=t1.id_prg_tc limit 1) else '' end ) as tipo_novedad,\n"
                + "(case when t1.estado_operacion = 5 then (select st4.titulo_tipo_novedad as tipo_novedad_detalle from novedad_prg_tc st1\n"
                + "left join novedad st2 on st1.id_novedad = st2.id_novedad\n"
                + "left join novedad_tipo_detalles st4 on st2.id_novedad_tipo_detalle = st4.id_novedad_tipo_detalle where st1.id_prg_tc=t1.id_prg_tc limit 1) else '' end ) as tipo_novedad_detalle,\n"
                + "(case when t1.estado_operacion = 5 then (select st1.observaciones from novedad_prg_tc st1\n"
                + "where st1.id_prg_tc=t1.id_prg_tc limit 1) else '' end ) as motivo\n"
                + "FROM prg_tc t1\n"
                + "LEFT JOIN vehiculo_tipo t2 on t1.id_vehiculo_tipo= t2.id_vehiculo_tipo\n"
                + "LEFT JOIN empleado t3 on t1.id_empleado = t3.id_empleado\n"
                + "LEFT JOIN vehiculo t4 on t1.id_vehiculo = t4.id_vehiculo\n"
                + "LEFT JOIN gop_unidad_funcional t5 on t1.id_gop_unidad_funcional = t5.id_gop_unidad_funcional\n"
                + "LEFT JOIN prg_tarea t6 on t1.id_task_type = t6.id_prg_tarea\n"
                + "LEFT JOIN prg_estado_op t7 on t1.estado_operacion = t7.id_prg_estado_op\n"
                + "WHERE t1.fecha between ?1 and ?2 AND t1.servbus is not null "
                + sql_uf
                + "ORDER BY t1.fecha, t1.time_origin, t1.servbus";

        Query q = em.createNativeQuery(sql, "ServbusPlanificadoDetalleDTOMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFunc);
        return q.getResultList();
    }
    
        @Override
    public List<ReporteSemanaActualDTO> findReporteSemanaActual(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "AND t1.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "t1.fecha, t1.servbus, t2.descripcion_tipo_vehiculo as tipologia,\n"
                + " CONCAT(t3.nombres,' ',t3.apellidos) as nombre, t3.codigo_tm,\n"
                + "t1.time_origin, t1.time_destiny, t4.codigo as codigo_bus, t5.nombre as uf,\n"
                + " t6.tarea as tipo_tarea, t1.distance as km, t7.descripcion as estado_operacion, t1.tabla, t1.id_empleado, t1.id_prg_tc, t8.id_motivo\n"
                + "FROM prg_tc t1\n"
                + "LEFT JOIN vehiculo_tipo t2 on t1.id_vehiculo_tipo= t2.id_vehiculo_tipo\n"
                + "LEFT JOIN empleado t3 on t1.id_empleado = t3.id_empleado\n"
                + "LEFT JOIN vehiculo t4 on t1.id_vehiculo = t4.id_vehiculo\n"
                + "LEFT JOIN gop_unidad_funcional t5 on t1.id_gop_unidad_funcional = t5.id_gop_unidad_funcional\n"
                + "LEFT JOIN prg_tarea t6 on t1.id_task_type = t6.id_prg_tarea\n"
                + "LEFT JOIN prg_estado_op t7 on t1.estado_operacion = t7.id_prg_estado_op\n"
                + "LEFT JOIN reporte_semana_motivo_prg t8 on t1.id_prg_tc = t8.id_prg_tc\n"
                + "WHERE t1.fecha between ?1 and ?2 AND t1.servbus is not null and t6.id_prg_tarea not in (2,3,4,1055,1056,1057)\n"
                + sql_uf
                + "ORDER BY t1.fecha, t1.time_origin, t1.servbus";

        Query q = em.createNativeQuery(sql, "ReporteSemanaActualDTOMapping"); //ServbusPlanificadoDetalleDTOMapping
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<KmPrgEjecDTO> findKmPrgEjecDTOByRangeDate(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND bp.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    bp.fecha,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    bp.nombre_tipo_vehiculo,\n"
                + "    bp.id_vehiculo_tipo,\n"
                + "    bp.km_programado,\n"
                + "    bp.km_ejecutado,\n"
                + "    bp.tipo_dia,\n"
                + "    bp.estacionalidad\n"
                + "FROM\n"
                + "    km_programados_ejecutados bp\n"
                + "    INNER JOIN\n"
                + "    gop_unidad_funcional guf ON bp.id_gop_unidad_funcional=guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    bp.fecha BETWEEN ?1 AND ?2"
                + sql_uf;

        Query q = em.createNativeQuery(sql, "KmPrgEjecDTOMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<UltimoServicioDTO> findUltimosServiciosPorServbus(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    tc.fecha AS fecha,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    tc.servbus AS servbus,\n"
                + "    pspp.name AS lugar_entrada,\n"
                + "    tc.id_prg_tc AS id_prg_tc,\n"
                + "    MAX(tc.time_destiny) AS hora_entrada,\n"
                + "    (SELECT \n"
                + "            CONCAT_WS('#',\n"
                + "                        tcc.sercon,\n"
                + "                        IFNULL(e.codigo_tm, 'N/A'),\n"
                + "                        IFNULL(e.nombres, 'N/A'),\n"
                + "                        IFNULL(e.apellidos, 'N/A'),\n"
                + "                        pt.tarea,\n"
                + "                        tcc.tabla,\n"
                + "                        IFNULL(v.codigo, 'N/A'))\n"
                + "        FROM\n"
                + "            prg_tc tcc\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                x.*\n"
                + "            FROM\n"
                + "                prg_tarea x\n"
                + "            WHERE\n"
                + "                x.sum_distancia = 1 AND x.comercial = 1) pt ON tcc.id_task_type = pt.id_prg_tarea\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                psp.*\n"
                + "            FROM\n"
                + "                prg_stop_point psp\n"
                + "            WHERE\n"
                + "                psp.is_depot IS NULL OR psp.is_depot = 0) pspx ON tcc.to_stop = pspx.id_prg_stoppoint\n"
                + "                LEFT JOIN\n"
                + "            empleado e ON tcc.id_empleado = e.id_empleado\n"
                + "                LEFT JOIN\n"
                + "            vehiculo v ON tcc.id_vehiculo = v.id_vehiculo\n"
                + "        WHERE\n"
                + "            tc.fecha = tcc.fecha\n"
                + "                AND tc.servbus = tcc.servbus\n"
                + "                AND tcc.id_task_type IS NOT NULL\n"
                + "        ORDER BY tcc.time_destiny DESC\n"
                + "        LIMIT 1) AS detalle_servicio,\n"
                + "    CAST((SELECT \n"
                + "            TIME(mce.fecha_hora)\n"
                + "        FROM\n"
                + "            my_app_confirm_depot_entry mce\n"
                + "        WHERE\n"
                + "            DATE(mce.fecha_hora) = DATE(tc.fecha)\n"
                + "                AND mce.id_prg_tc = tc.id_prg_tc) AS NCHAR) AS hora_real_ingreso\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        xpsp.*\n"
                + "    FROM\n"
                + "        prg_stop_point xpsp\n"
                + "    WHERE\n"
                + "        xpsp.is_depot IS NULL\n"
                + "            OR xpsp.is_depot = 1) pspp ON pspp.id_prg_stoppoint = tc.to_stop\n"
                + "                INNER JOIN\n"
                + "            gop_unidad_funcional guf ON tc.id_gop_unidad_funcional = guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    tc.fecha = ?1\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + "        AND estado_operacion NOT IN (?3 , ?4, ?6, ?7)\n"
                + sql_uf
                + "GROUP BY servbus\n"
                + "ORDER BY detalle_servicio ASC";

        Query q = em.createNativeQuery(sql, "UltimoServicioDTOMapping");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFunc);
        q.setParameter(3, ConstantsUtil.CODE_ADICIONAL_3);
        q.setParameter(4, ConstantsUtil.CODE_ADICIONAL_PARCIAL_4);
//        q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(6, ConstantsUtil.CODE_VACCOM_6);
        q.setParameter(7, ConstantsUtil.CODE_VAC_7);
//        q.setParameter(8, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
//        q.setParameter(9, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
        return q.getResultList();
    }

    @Override
    public List<PrimerServicioDTO> findPrimerosServiciosPorServbus(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_uf_servbus = idGopUnidadFunc == 0 ? "" : "            AND tcc.id_gop_unidad_funcional = ?2\n";
        String sql_uf_tarea = idGopUnidadFunc == 0 ? "" : "            AND x.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    tc.fecha AS fecha,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    tc.servbus AS servbus,\n"
                + "    pspp.name AS lugar_salida,\n"
                + "    tc.id_prg_tc AS id_prg_tc,\n"
                + "    MIN(tc.time_origin) AS hora_salida,\n"
                + "    (SELECT \n"
                + "            CONCAT_WS('#',\n"
                + "                        tcc.sercon,\n"
                + "                        IFNULL(e.codigo_tm, 'N/A'),\n"
                + "                        IFNULL(e.nombres, 'N/A'),\n"
                + "                        IFNULL(e.apellidos, 'N/A'),\n"
                + "                        pspx.name,\n"
                + "                        pt.tarea,\n"
                + "                        tcc.tabla,\n"
                + "                        IFNULL(v.codigo, 'N/A'))\n"
                + "        FROM\n"
                + "            prg_tc tcc\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                x.*\n"
                + "            FROM\n"
                + "                prg_tarea x\n"
                + "            WHERE\n"
                + "                x.sum_distancia = 1 "
                + "                 AND x.comercial = 1"
                + sql_uf_tarea + ") pt ON tcc.id_task_type = pt.id_prg_tarea\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                psp.*\n"
                + "            FROM\n"
                + "                prg_stop_point psp\n"
                + "            WHERE\n"
                + "                psp.is_depot IS NULL OR psp.is_depot = 0) pspx ON tcc.from_stop = pspx.id_prg_stoppoint\n"
                + "                LEFT JOIN\n"
                + "            empleado e ON tcc.id_empleado = e.id_empleado\n"
                + "                LEFT JOIN\n"
                + "            vehiculo v ON tcc.id_vehiculo = v.id_vehiculo\n"
                + "        WHERE\n"
                + "            tc.fecha = tcc.fecha\n"
                + "                AND tc.servbus = tcc.servbus\n"
                + "                AND tcc.id_task_type IS NOT NULL\n"
                + sql_uf_servbus
                + "        ORDER BY tcc.time_destiny ASC\n"
                + "        LIMIT 1) AS detalle_servicio,\n"
                + "    CAST((SELECT \n"
                + "                TIME(mce.fecha_hora)\n"
                + "            FROM\n"
                + "                my_app_confirm_depot_exit mce\n"
                + "            WHERE\n"
                + "                DATE(mce.fecha_hora) = DATE(tc.fecha)\n"
                + "                    AND mce.id_prg_tc = tc.id_prg_tc)\n"
                + "        AS NCHAR) AS hora_real_salida\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        xpsp.*\n"
                + "    FROM\n"
                + "        prg_stop_point xpsp\n"
                + "    WHERE\n"
                + "        xpsp.is_depot IS NULL\n"
                + "            OR xpsp.is_depot = 1) pspp ON pspp.id_prg_stoppoint = tc.from_stop\n"
                + "                INNER JOIN\n"
                + "            gop_unidad_funcional guf ON tc.id_gop_unidad_funcional = guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    tc.fecha = ?1\n"
                + "        AND tc.servbus IS NOT NULL\n"
                + "        AND estado_operacion NOT IN (?3 , ?4, ?6, ?7)\n"
                + sql_uf
                + "GROUP BY servbus\n"
                + "ORDER BY detalle_servicio ASC";

        Query q = em.createNativeQuery(sql, "primerServicioDTOMapping");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFunc);
        q.setParameter(3, ConstantsUtil.CODE_ADICIONAL_3);
        q.setParameter(4, ConstantsUtil.CODE_ADICIONAL_PARCIAL_4);
//        q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(6, ConstantsUtil.CODE_VACCOM_6);
        q.setParameter(7, ConstantsUtil.CODE_VAC_7);
//        q.setParameter(8, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
//        q.setParameter(9, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
        return q.getResultList();
    }

    @Override
    public List<ServbusTipoTablaDTO> findServbusTipoTabla(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND p.id_gop_unidad_funcional = ?2\n";
        String sql = "SELECT \n"
                + "    p.servbus AS servbus, COUNT(p.id_prg_tc) AS num_entry_depot\n"
                + "FROM\n"
                + "    prg_tc p\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        psp.*\n"
                + "    FROM\n"
                + "        prg_stop_point psp\n"
                + "    WHERE\n"
                + "        psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON psppx.id_prg_stoppoint = p.to_stop\n"
                + "WHERE\n"
                + "    p.fecha = ?1\n"
                + "        AND p.servbus IS NOT NULL\n"
                + "        AND p.estado_operacion NOT IN (?3 , ?4, ?6, ?7)\n"
                + sql_uf
                + "GROUP BY servbus;";

        Query q = em.createNativeQuery(sql, "ServbusTipoTablaDTOMapping");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFunc);
        q.setParameter(3, ConstantsUtil.CODE_ADICIONAL_3);
        q.setParameter(4, ConstantsUtil.CODE_ADICIONAL_PARCIAL_4);
        q.setParameter(6, ConstantsUtil.CODE_VACCOM_6);
        q.setParameter(7, ConstantsUtil.CODE_VAC_7);
        return q.getResultList();
    }

    @Override
    public List<InsumoProgramacionDTO> findInsumoProgramacion(Date desde, Date hasta, int idGopUnidadFunc) {
        String sql_uf_tc = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?11\n";
        String sql = "SELECT DISTINCT\n"
                + "    etc.id_empleado_tipo_cargo AS id_empleado_tipo_cargo,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    tc.fecha,\n"
                + "    etc.nombre_cargo AS tipologia,\n"
                + "    (IFNULL(COUNT(DISTINCT (CASE\n"
                + "                    WHEN\n"
                + "                        ptx.comercial = 1\n"
                + "                            AND ptx.sum_distancia = 1\n"
                + "                            AND tc.distance > 0\n"
                + "                    THEN\n"
                + "                        tc.id_empleado\n"
                + "                    ELSE NULL\n"
                + "                END)),\n"
                + "            0)) total_tareas_prg,\n"
                + "    (IFNULL(COUNT(DISTINCT (CASE\n"
                + "                    WHEN ptx.op_disponible = 1 THEN tc.id_empleado\n"
                + "                    ELSE NULL\n"
                + "                END)),\n"
                + "            0)) total_reservas_prg,\n"
                + "    IFNULL((SELECT \n"
                + "            tnsp.valor\n"
                + "        FROM\n"
                + "            total_novedad_sp tnsp\n"
                + "        WHERE\n"
                + "            tnsp.fecha = tc.fecha\n"
                + "                AND tnsp.id_empleado_cargo = etc.id_empleado_tipo_cargo),0) AS total_ausentismo\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        INNER JOIN\n"
                + "    empleado e ON tc.id_empleado = e.id_empleado\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_cargo etc ON e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                + "        LEFT JOIN\n"
                + "    prg_tarea ptx ON tc.id_task_type = ptx.id_prg_tarea\n"
                + "                INNER JOIN\n"
                + "            gop_unidad_funcional guf ON tc.id_gop_unidad_funcional = guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    tc.fecha BETWEEN ?1 AND ?2\n"
                + "        AND estado_operacion NOT IN (?3 , ?4, ?6, ?7, ?8, ?9)\n"
                + sql_uf_tc
                + "GROUP BY tc.id_gop_unidad_funcional, tc.fecha , id_empleado_tipo_cargo\n"
                + "ORDER BY fecha , tipologia";

        Query sp = em.createNativeQuery("{call spTotalTipoNovedadByRangeFecha(?1,?2,?3,?4)}");
        sp.setParameter(1, Util.dateFormat(desde));
        sp.setParameter(2, Util.dateFormat(hasta));
        sp.setParameter(3, idGopUnidadFunc);
        sp.setParameter(4, SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO));
        sp.executeUpdate();
        Query q = em.createNativeQuery(sql, "InsumoProgramacionDTOMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, ConstantsUtil.CODE_ADICIONAL_3);
        q.setParameter(4, ConstantsUtil.CODE_ADICIONAL_PARCIAL_4);
        q.setParameter(6, ConstantsUtil.CODE_VACCOM_6);
        q.setParameter(7, ConstantsUtil.CODE_VAC_7);
        q.setParameter(8, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
        q.setParameter(9, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
        q.setParameter(11, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<PorcentajeDisponibilidadFlotaDTO> porcentajeDispoFlota(Date desde, Date hasta, String horaCorte, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND d.id_uf = ?3\n";

        String sql = "SELECT \n"
                + "    x.id_uf,\n"
                + "    guf.nombre as nombre_uf,\n"
                + "    x.fecha,\n"
                + "    x.total_flota,\n"
                + "    x.total_disponibles,\n"
                + "    x.total_inoperativos,\n"
                + "    IFNULL(((x.total_disponibles / x.x.total_flota) * 100),\n"
                + "            0) AS porcentaje_dispo,\n"
                + "    vt.nombre_tipo_vehiculo\n"
                + "FROM\n"
                + "    (SELECT \n"
                + "        d.id_uf,\n"
                + "            d.fecha,\n"
                + "            d.id_vehiculo_tipo,\n"
                + "            COUNT(d.id_vehiculo) AS total_flota,\n"
                + "            COUNT(DISTINCT d.id_vehiculo, IF((d.id_vehiculo_tipo_estado = 1), 1, NULL)) AS total_disponibles,\n"
                + "            COUNT(DISTINCT d.id_vehiculo, IF((d.id_vehiculo_tipo_estado = 2), 1, NULL)) AS total_inoperativos\n"
                + "    FROM\n"
                + "        disp_vehicle_status d\n"
                + "    WHERE\n"
                + "        d.fecha BETWEEN ?1 AND ?2\n"
                + sql_uf
                + "    GROUP BY d.id_uf , d.fecha , d.id_vehiculo_tipo) x\n"
                + "        INNER JOIN\n"
                + "    vehiculo_tipo vt ON x.id_vehiculo_tipo = vt.id_vehiculo_tipo\n"
                + "        INNER JOIN\n"
                + "    gop_unidad_funcional guf ON x.id_uf = guf.id_gop_unidad_funcional\n"
                + "ORDER BY x.id_uf , x.fecha , x.id_vehiculo_tipo";

        Query sp = em.createNativeQuery("{call spVehiculeStatusByRangeDate(?1,?2,?3)}");
        sp.setParameter(1, Util.dateFormat(desde));
        sp.setParameter(2, Util.dateFormat(hasta));
        sp.setParameter(3, horaCorte);
        sp.executeUpdate();
        Query q = em.createNativeQuery(sql, "PorcentajeDisponibilidadFlotaDTOMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFunc);
        return q.getResultList();
    }

    @Override
    public List<TP28UltimoServicioDTO> findTP28UltimosServicios(Date fecha, int idGopUnidadFunc) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "            AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_uf_tx = idGopUnidadFunc == 0 ? "" : "            AND tx.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    tx.fecha AS fecha,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    tx.time_origin AS time_origin,\n"
                + "    u.tarea AS tarea,\n"
                + "    tx.tabla AS tabla,\n"
                + "    e.codigo_tm AS codigo_tm,\n"
                + "    e.nombres AS nombres,\n"
                + "    e.apellidos AS apellidos,\n"
                + "    e.telefono_movil AS telefono_movil,\n"
                + "    psp.name AS from_stop,\n"
                + "    v.codigo AS codigo_vehiculo\n"
                + "FROM\n"
                + "    prg_tc tx\n"
                + "        LEFT JOIN\n"
                + "    empleado e ON tx.id_empleado = e.id_empleado\n"
                + "        INNER JOIN\n"
                + "    prg_stop_point psp ON tx.from_stop = psp.id_prg_stoppoint\n"
                + "        LEFT JOIN\n"
                + "    vehiculo v ON tx.id_vehiculo = v.id_vehiculo\n"
                + "        INNER JOIN\n"
                + "    gop_unidad_funcional guf ON tx.id_gop_unidad_funcional = guf.id_gop_unidad_funcional,\n"
                + "    (SELECT \n"
                + "        tc.id_task_type, MAX(time_destiny) AS tdestiny, t.tarea\n"
                + "    FROM\n"
                + "        prg_tc tc\n"
                + "    INNER JOIN prg_tarea t ON t.id_prg_tarea = tc.id_task_type\n"
                + "    WHERE\n"
                + "        tc.fecha = ?1\n"
                + sql_uf
                + "            AND tc.estado_operacion NOT IN (?3 , ?4, ?6, ?7)\n"
                + "            AND t.comercial = 1\n"
                + "            AND t.sum_distancia = 1\n"
                + "            AND t.id_prg_tarea not in(" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_VAC_PRG) + ")\n"
                + "            AND t.tarea NOT LIKE '%Vac%'\n"
                + "    GROUP BY tc.id_task_type\n"
                + "    ORDER BY tc.id_task_type ASC) u\n"
                + "WHERE\n"
                + "    tx.id_task_type = u.id_task_type\n"
                + "        AND tx.time_destiny = u.tdestiny\n"
                + "        AND tx.fecha = ?1\n"
                + sql_uf_tx
                + "        AND tx.estado_operacion NOT IN (?3 , ?4, ?6, ?7)\n"
                + "GROUP BY tx.servbus\n"
                + "ORDER BY tx.time_origin";

        Query q = em.createNativeQuery(sql, "TP28UltimoServicioDTOMapping");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFunc);
        q.setParameter(3, ConstantsUtil.CODE_ADICIONAL_3);
        q.setParameter(4, ConstantsUtil.CODE_ADICIONAL_PARCIAL_4);
//        q.setParameter(5, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
        q.setParameter(6, ConstantsUtil.CODE_VACCOM_6);
        q.setParameter(7, ConstantsUtil.CODE_VAC_7);
//        q.setParameter(8, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
//        q.setParameter(9, ConstantsUtil.CODE_ELIMINADO_SERVICIO_99);
        return q.getResultList();
    }

    @Override
    public List<DiasSinOperar> obtenerDiasSinOperar(Date fechaInicio, Date fechaFin, Integer idGopUnidadFuncional) {
        try {

            String sql_unida_func_vehiculo = (idGopUnidadFuncional == null || idGopUnidadFuncional == 0) ? "" : "        WHERE vehiculo.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT \n"
                    + "	   ?1 as desde,\n"
                    + "    ?2 as hasta,\n"
                    + "    vehiculo.codigo AS codigo_vehiculo,\n"
                    + "    IFNULL(ROUND(((comercial + hlp_prg + adicionales + vaccom + vac) / 1000),\n"
                    + "                    0),\n"
                    + "            0) AS total_km,\n"
                    + "    IFNULL(estado, 'Inoperativo') AS estado\n"
                    + "FROM\n"
                    + "    vehiculo\n"
                    + "        LEFT JOIN\n"
                    + "    (SELECT \n"
                    + "        vehiculo.id_vehiculo,\n"
                    + "            vehiculo_tipo_estado.nombre_tipo_estado AS estado,\n"
                    + "            SUM(IF((estado_operacion = 0\n"
                    + "                OR estado_operacion = 1\n"
                    + "                OR estado_operacion = 2)\n"
                    + "                AND prg_tarea.id_prg_tarea <> 4, distance, 0)) AS comercial,\n"
                    + "            SUM(IF(estado_operacion = 0\n"
                    + "                AND prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_prg,\n"
                    + "            SUM(IF(estado_operacion = 3\n"
                    + "                OR estado_operacion = 4, distance, 0)) AS adicionales,\n"
                    + "            SUM(IF(estado_operacion = 6, distance, 0)) AS vaccom,\n"
                    + "            SUM(IF(estado_operacion = 5\n"
                    + "                AND prg_tarea.id_prg_tarea <> 4, distance, 0)) AS comercial_Eliminados,\n"
                    + "            SUM(IF(estado_operacion = 5\n"
                    + "                AND prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_Eliminados,\n"
                    + "            SUM(IF(estado_operacion = 7, distance, 0)) AS vac\n"
                    + "    FROM\n"
                    + "        prg_tc\n"
                    + "    RIGHT JOIN vehiculo ON prg_tc.id_vehiculo = vehiculo.id_vehiculo\n"
                    + "    INNER JOIN prg_tarea ON prg_tc.id_task_type = prg_tarea.id_prg_tarea\n"
                    + "    INNER JOIN vehiculo_tipo_estado ON vehiculo.id_vehiculo_tipo_estado = vehiculo_tipo_estado.id_vehiculo_tipo_estado\n"
                    + "    WHERE\n"
                    + "        prg_tc.fecha BETWEEN ?1 AND ?2\n"
                    + "            AND prg_tc.servbus IS NOT NULL\n"
                    + "    GROUP BY id_vehiculo) prg ON prg.id_vehiculo = vehiculo.id_vehiculo\n"
                    + sql_unida_func_vehiculo
                    + "ORDER BY vehiculo.codigo ASC , desde, hasta";

            Query query = em.createNativeQuery(sql, "DiasSinOperarMapping");
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Método que se encarga de obtener el listado de operadores disponibles
     * para le fecha posterior (Consulta Ausentismo)
     *
     * @param fecha
     * @param timeDestiny
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public List<PrgTc> operadoresDisponiblesDiaPosterior(Date fecha,
            String timeDestiny, int idGopUnidadFuncional) {

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    tc.*\n"
                + "FROM\n"
                + "    prg_tc tc\n"
                + "        LEFT JOIN\n"
                + "    prg_tarea pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "WHERE\n"
                + "        tc.fecha = ?1\n"
                + "        AND pt.op_disponible = 1\n"
                + "        AND tc.estado_reg = 0\n"
                + "        AND TIME(time_destiny) >= TIME(?4)\n"
                + "        AND tc.id_empleado IS NOT NULL\n"
                + "        AND estado_operacion <> ?3\n"
                + sql_unida_func
                + "ORDER BY time_origin ASC;", PrgTc.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, ConstantsUtil.CODE_ELIMINADO_DISP_9);
        q.setParameter(4, timeDestiny);
        return q.getResultList();
    }

    @Override
    public PrgTc obtenerFechaYRutaDiaPosterior(String sercon, Date fechaConsulta, int idGopUnidadFunc) {
        try {
            String sql_unida_func = idGopUnidadFunc == 0 ? "" : "  AND id_gop_unidad_funcional = ?3\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "WHERE\n"
                    + "    sercon = ?1\n"
                    + "        AND fecha = ?2\n"
                    + "        AND servbus is not null\n"
                    + "        AND id_ruta is not null\n"
                    + sql_unida_func
                    + "order by time_origin\n"
                    + "limit 1;", PrgTc.class);
            q.setParameter(1, sercon);
            q.setParameter(2, Util.dateFormat(fechaConsulta));
            q.setParameter(3, idGopUnidadFunc);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int updateServbusDesasignado(Date fecha, int idGopUnidadFunc, String servbus, int idVehiculo, String timeOrigin, String username) {
        try {
            String sql = "UPDATE prg_tc p \n"
                    + "SET \n"
                    + " p.id_vehiculo = ?1,\n"
                    + " p.username = ?2\n"
                    + "WHERE \n"
                    + " p.servbus = ?3 \n"
                    + " AND p.fecha = ?4 \n"
                    + " AND p.id_gop_unidad_funcional = ?5 \n"
                    + " AND p.time_origin >= ?6 \n"
                    + " AND p.id_vehiculo is null";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, username);
            q.setParameter(3, servbus);
            q.setParameter(4, Util.dateFormat(fecha));
            q.setParameter(5, idGopUnidadFunc);
            q.setParameter(6, timeOrigin);
            return q.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public Long findVehiculoLibreAsignar(int idGopUnidadFunc, String servbus, int idVehiculo, Date fecha, String timeOrigin) {
        try {
            String sql = "";
            sql = "select count(*) from prg_tc,  \n"
                    + "	(select min(time_origin) hini, max(time_destiny) hmax from prg_tc \n"
                    + "    where fecha=?1 \n"
                    + "    and time_origin>= ?2 \n"
                    + "	and servbus = ?3 \n"
                    + "	and id_vehiculo is null \n"
                    + " and id_gop_unidad_funcional = ?4) as horas\n"
                    + "where fecha = ?5 \n"
                    + " and id_vehiculo = ?6 \n"
                    + " and (time_origin between horas.hini and horas.hmax \n"
                    + " or time_Destiny between horas.hini and horas.hmax) \n"
                    + "order by time_origin asc;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, timeOrigin);
            q.setParameter(3, servbus);
            q.setParameter(4, idGopUnidadFunc);
            q.setParameter(5, Util.dateFormat(fecha));
            q.setParameter(6, idVehiculo);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Long findVehiculoLibreSinVACAsignar(int idGopUnidadFunc, String servbus, int idVehiculo, Date fecha, String timeOrigin) {
        try {
            String sql = "";
            sql = "select count(*) from prg_tc,  \n"
                    + "	(select min(time_origin) hini, max(time_destiny) hmax from prg_tc \n"
                    + "    where fecha=?1 \n"
                    + "     and time_origin > ?2 \n"
                    + "     and servbus = ?3 \n"
                    + "     and id_vehiculo is null \n"
                    + "     and id_gop_unidad_funcional = ?4"
                    + ") as horas\n"
                    + "where fecha = ?5 \n"
                    + " and id_vehiculo = ?6 \n"
                    + " and ( (time_origin between horas.hini and horas.hmax) \n"
                    + " or (time_Destiny between horas.hini and horas.hmax) ) \n"
                    + " AND estado_operacion NOT IN (?7, ?8) "
                    + "order by time_origin asc;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, timeOrigin);
            q.setParameter(3, servbus);
            q.setParameter(4, idGopUnidadFunc);
            q.setParameter(5, Util.dateFormat(fecha));
            q.setParameter(6, idVehiculo);
            q.setParameter(7, ConstantsUtil.CODE_ELIMINADO_SERVICIO_5);
            q.setParameter(8, ConstantsUtil.CODE_ELIMINADO_SERVICIO_8);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgTc> obtenerTareasPorFechasYOperador(Date fechaDesde, Date fechaHasta, int idGopUnidadFuncional, int idEmpleado) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?3\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    tc.* \n"
                    + "FROM \n"
                    + "    prg_tc tc \n"
                    + "WHERE \n"
                    + "        tc.fecha BETWEEN ?1 AND ?2 \n"
                    + "        AND tc.id_empleado = ?4 \n"
                    + "        AND tc.estado_reg = 0 \n"
                    + "        AND tc.estado_operacion not in(9) \n"
                    + sql_unida_func
                    + "ORDER BY tc.fecha ASC;", PrgTc.class);
            q.setParameter(1, Util.dateFormat(fechaDesde));
            q.setParameter(2, Util.dateFormat(fechaHasta));
            q.setParameter(3, idGopUnidadFuncional);
            q.setParameter(4, idEmpleado);

            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrgTc findPrgTcEntrada(int idVehiculo, String date, String hour) {

        try {
            Query q = em.createNativeQuery("SELECT  \n"
                    + "tc.* FROM \n"
                    + "prg_tc tc \n"
                    + "INNER JOIN \n"
                    + "prg_stop_point s ON s.id_prg_stoppoint = tc.to_stop WHERE \n"
                    + "fecha = DATE(?1) \n"
                    + "AND tc.id_prg_tc NOT IN (SELECT  \n"
                    + "made.id_prg_tc \n"
                    + "FROM \n"
                    + "my_app_confirm_depot_entry made) \n"
                    + "AND ((TIME_TO_SEC(?2) BETWEEN TIME_TO_SEC(tc.time_origin)-3600 AND TIME_TO_SEC(tc.time_destiny)) \n"
                    + "OR (TIME_TO_SEC(tc.time_destiny) <= TIME_TO_SEC(?2))) \n"
                    + "AND s.is_depot = 1 \n"
                    + "AND tc.id_vehiculo = ?3 \n"
                    + "AND tc.estado_reg = 0 \n"
                    + "AND s.estado_reg = 0 \n"
                    + "AND estado_operacion NOT IN (5 , 8, 99) ORDER BY time_destiny DESC LIMIT 1", PrgTc.class);
            q.setParameter(1, date);
            q.setParameter(2, hour);
            q.setParameter(3, idVehiculo);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return new PrgTc();
        }
    }

    @Override
    public PrgTc findPrgTcSalida(int idVehiculo, String date, String hour) {

        try {
            Query q = em.createNativeQuery("SELECT  \n"
                    + "tc.* \n"
                    + "FROM \n"
                    + "prg_tc tc \n"
                    + "INNER JOIN \n"
                    + "prg_stop_point s ON s.id_prg_stoppoint = tc.from_stop \n"
                    + "WHERE \n"
                    + "fecha = DATE(?1) \n"
                    + "AND tc.id_prg_tc NOT IN (SELECT  \n"
                    + "madex.id_prg_tc \n"
                    + "FROM \n"
                    + "my_app_confirm_depot_exit madex) \n"
                    //                    + "AND ((TIME_TO_SEC(?2) BETWEEN TIME_TO_SEC(tc.time_origin) AND TIME_TO_SEC(tc.time_destiny)) \n"
                    //                    + "OR (TIME_TO_SEC(tc.time_origin) <= TIME_TO_SEC(?2))) \n"
                    + "AND s.is_depot = 1 \n"
                    + "AND tc.id_vehiculo = ?3 \n"
                    + "AND tc.estado_reg = 0 \n"
                    + "AND s.estado_reg = 0 \n"
                    + "AND estado_operacion NOT IN (5 , 8, 99) \n"
                    + "ORDER BY time_origin ASC \n"
                    + "LIMIT 1", PrgTc.class);
            q.setParameter(1, date);
            q.setParameter(2, hour);
            q.setParameter(3, idVehiculo);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return new PrgTc();
        }
    }

    @Override
    public PrgTc findPrgTcEntradaId(int idPrgTc, String time) {
        try {
            Query q = em.createNativeQuery("select * from prg_tc tc where ((TIME_TO_SEC(?2) BETWEEN TIME_TO_SEC(tc.time_origin)-3600 \n"
                    + "AND TIME_TO_SEC(tc.time_destiny)) OR (TIME_TO_SEC(tc.time_destiny) <= TIME_TO_SEC(?2))) \n"
                    + "AND tc.id_prg_tc=?1", PrgTc.class);
            q.setParameter(1, idPrgTc);
            q.setParameter(2, time);
            return (PrgTc) q.getSingleResult();
        } catch (Exception e) {
            return new PrgTc();
        }
    }

    @Override
    public List<PrgTc> findPrimerasTareasServbus(Date fechaDesde, Date fechaHasta) {
        try {
            Query q = em.createNativeQuery(" SELECT *\n"
                    + "FROM prg_tc\n"
                    + "WHERE (servbus, time_origin) IN (\n"
                    + "    SELECT servbus, MIN(time_origin)\n"
                    + "    FROM prg_tc WHERE fecha between ?1 and ?2 AND servbus is not null AND estado_operacion not in (3,4,6,7)\n"
                    + "    GROUP BY servbus\n"
                    + ") and fecha between ?1 and ?2 AND servbus is not null AND estado_operacion not in (3,4,6,7)", PrgTc.class);
            q.setParameter(1, Util.dateFormat(fechaDesde));
            q.setParameter(2, Util.dateFormat(fechaHasta));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<PrgTc> findByEmpleadoByTarea(Date fecha, String horaInicial, String horaFin, String descripcion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_tc pt\n"
                    + "WHERE\n"
                    + "    pt.fecha = ?1\n"
                    + "        AND pt.time_origin = ?2\n"
                    + "        AND pt.time_destiny = ?3\n"
                    + "AND pt.sercon = ?4", PrgTc.class);
            q.setParameter(1, fecha);
            q.setParameter(2, horaInicial);
            q.setParameter(3, horaFin);
            q.setParameter(4, descripcion);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
