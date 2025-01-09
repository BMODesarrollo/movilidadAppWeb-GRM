/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Accidente;
import com.movilidad.util.beans.BitacoraAccidentalidad;
import com.movilidad.util.beans.ReporteLucroCesante;
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
public class AccidenteFacade extends AbstractFacade<Accidente> implements AccidenteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteFacade() {
        super(Accidente.class);
    }

    /**
     * Consultar objeto Accidente por identificador unico de objeto Novedad
     *
     * @param id Identificador unico Objeto Novedad
     * @return Objeto Accidente
     */
    @Override
    public Accidente findByNovedad(int id) {
        try {
            String sql = "select * from accidente where id_novedad = ?1";
            Query query = em.createNativeQuery(sql, Accidente.class);
            query.setParameter(1, id);
            return (Accidente) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Consulta objeto List de objetos Accidente de acuerdo a los parametros
     * enviados como parametros
     *
     * @param idVeh identificador unico
     * @param idEmp identificador unico
     * @param idNovDet identificador unico
     * @param fechaIni Objeto date
     * @param fechaFin Objeto date
     * @param idGopUnidadFuncional identificador unidad funcional
     * @return objeto List de objeto Accidente
     */
    @Override
    public List<Accidente> findByArguments(int idVeh, int idEmp, int idNovDet, Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql = "SELECT * FROM accidente WHERE date(fecha_acc) BETWEEN ?2 AND ?3 ";
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1 ";
            if (idEmp != 0) {
                sql = sql + "AND id_empleado = " + idEmp + " ";
            }
            if (idVeh != 0) {
                sql = sql + "AND id_vehiculo = " + idVeh + " ";
            }
            if (idNovDet != 0) {
                sql = sql + "AND id_novedad_tipo_detalle = " + idNovDet + " ";
            }
            sql = sql + sql_unida_func;
            sql = sql + "AND estado_reg = 0 ORDER BY fecha_acc DESC";
            Query q = em.createNativeQuery(sql, Accidente.class);
            q.setParameter(1, idGopUnidadFuncional);
            q.setParameter(2, Util.dateFormat(fechaIni));
            q.setParameter(3, Util.dateFormat(fechaFin));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param i_idVeh
     * @param i_idEmp
     * @param i_idNovDet
     * @param fechaIni
     * @param fechaFin
     * @param idGopUF
     * @return
     */
    @Override
    public List<Accidente> findAccidenteForInformeMaster(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni,
            Date fechaFin, int idGopUF
    ) {
        try {
            String fecIni = Util.dateFormat(fechaIni);
            String fecFin = Util.dateFormat(fechaFin);
            String sql = "select acc.* \n"
                    + "from accidente acc \n"
                    + "where acc.id_accidente not in \n"
                    + "(select acci.id_accidente from acc_informe_master acci where acci.estado_reg = 0) \n"
                    + "and date(acc.fecha_acc) between '" + fecIni + "' and '" + fecFin + "'\n";
            if (i_idEmp != 0) {
                sql = sql + "and acc.id_empleado = " + i_idEmp + "\n";
            }
            if (i_idVeh != 0) {
                sql = sql + "and acc.id_vehiculo = " + i_idVeh + "\n";
            }
            if (i_idNovDet != 0) {
                sql = sql + "and acc.id_novedad_tipo_detalle = " + i_idNovDet + " \n";
            }
            if (idGopUF != 0) {
                sql = sql + "and acc.id_gop_unidad_funcional = " + idGopUF + " ";
            }
            sql = sql + "and acc.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, Accidente.class);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param i_idVeh identificador unico
     * @param i_idEmp identificador unico
     * @param i_idNovDet identificador unico
     * @param fechaIni
     * @param fechaFin
     * @param idGopUF
     * @return
     */
    @Override
    public List<Accidente> findAccidenteForInformeOperador(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni,
            Date fechaFin, int idGopUF
    ) {
        try {
            String fecIni = Util.dateFormat(fechaIni);
            String fecFin = Util.dateFormat(fechaFin);
            String sql = "select acc.* \n"
                    + "from accidente acc \n"
                    + "where acc.id_accidente not in \n"
                    + "(select acci.id_accidente from acc_informe_ope acci where acci.estado_reg = 0) \n"
                    + "and date(acc.fecha_acc) between '" + fecIni + "' and '" + fecFin + "'\n";
            if (i_idEmp != 0) {
                sql = sql + "and acc.id_empleado = " + i_idEmp + "\n";
            }
            if (i_idVeh != 0) {
                sql = sql + "and acc.id_vehiculo = " + i_idVeh + "\n";
            }
            if (i_idNovDet != 0) {
                sql = sql + "and acc.id_novedad_tipo_detalle = " + i_idNovDet + " \n";
            }
            if (idGopUF != 0) {
                sql = sql + "and acc.id_gop_unidad_funcional = " + idGopUF + " ";
            }
            sql = sql + "and acc.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, Accidente.class);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param i_idVeh identificador unico
     * @param i_idEmp identificador unico
     * @param i_idNovDet identificador unico
     * @param fechaIni
     * @param fechaFin
     * @param idGopUF
     * @return
     */
    @Override
    public List<Accidente> findAccidenteForInformeMasterEdit(int i_idVeh, int i_idEmp, int i_idNovDet, Date fechaIni,
            Date fechaFin, int idGopUF) {
        try {
            String fecIni = Util.dateFormat(fechaIni);
            String fecFin = Util.dateFormat(fechaFin);
            String sql = "select acc.* \n"
                    + "from accidente acc \n"
                    + "where acc.id_accidente in \n"
                    + "(select acci.id_accidente from acc_informe_master acci where acci.estado_reg = 0) \n"
                    + "and date(acc.fecha_acc) between '" + fecIni + "' and '" + fecFin + "'\n";
            if (i_idEmp != 0) {
                sql = sql + "and acc.id_empleado = " + i_idEmp + "\n";
            }
            if (i_idVeh != 0) {
                sql = sql + "and acc.id_vehiculo = " + i_idVeh + "\n";
            }
            if (i_idNovDet != 0) {
                sql = sql + "and acc.id_novedad_tipo_detalle = " + i_idNovDet + " \n";
            }
            if (idGopUF != 0) {
                sql = sql + "and acc.id_gop_unidad_funcional = " + idGopUF + " ";
            }
            sql = sql + "and acc.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, Accidente.class);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Consulta que permite conocer los objetos Accidente de un objeto Empleado
     *
     * @param idAcc identificador unico objeto Accidente
     * @param idEmpleado identificador unico objeto Empleado
     * @param desde
     * @param hasta
     * @param idGopUF
     * @return Objeto List de Accidente
     */
    @Override
    public List<Accidente> findAllByIdEmpleadoAndDates(Integer idAcc, Integer idEmpleado,
            String desde, String hasta,
            int idGopUF
    ) {
        String sql = "select * "
                + "from accidente "
                + "where id_empleado = ?1 "
                + "and id_accidente <> ?2 and id_novedad_tipo_detalle <> 103 "
                + "and date(fecha_acc) BETWEEN ?3 and ?4 ";
        if (idGopUF != 0) {
            sql = sql + "and id_gop_unidad_funcional = " + idGopUF + " ";
        }
        sql = sql + "and estado_reg = 0";
        Query q = em.createNativeQuery(sql, Accidente.class);
        q.setParameter(1, idEmpleado);
        q.setParameter(2, idAcc);
        q.setParameter(3, desde);
        q.setParameter(4, hasta);
        return q.getResultList();
    }
    
    @Override
    public List<Accidente> findAllByIdVehiculoAndDate(Integer idVehiculo, Date fecha){
        String sql = "select * "
                + "from accidente "
                + "where id_vehiculo = ?1 "
                + "and date(fecha_acc) = ?2 ";
        sql = sql + "and estado_reg = 0";
        Query q = em.createNativeQuery(sql, Accidente.class);
        q.setParameter(1, idVehiculo);
        q.setParameter(2, fecha);
        return q.getResultList();
    }

    @Override
    public Long findTotalAccidentesEnAtencion(Date fecha, int idGopUnidadFunc, Date fechaUltimoCierre) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "        AND a.id_gop_unidad_funcional = ?2\n";
        String sql_fecha_ultimo_cierre = fechaUltimoCierre == null ? "" : "        AND TIME(a.fecha_acc) >= TIME(?3)\n";
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    COUNT(a.id_accidente)\n"
                    + "FROM\n"
                    + "    accidente a\n"
                    + "WHERE\n"
                    + "    a.estado_reg = 0\n"
                    + sql_uf
                    + sql_fecha_ultimo_cierre
                    + "        AND DATE(a.fecha_acc) = DATE(?1)\n"
                    + "        AND a.fecha_cierre_recomoto IS NULL\n");
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFunc);
            q.setParameter(3, Util.dateTimeFormat(fechaUltimoCierre));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public Long existeAccidenteAbiertoByIdEmpleadoByIdVehiculoAndIdNovDetAndIdGopUF(Integer idEmpleado, Integer idVehiculo, Integer idNovDet, Integer idGopUF) {
        try {
            String sql = "SELECT \n"
                    + "         COUNT(*)  AS existe\n"
                    + "FROM\n"
                    + "    accidente\n"
                    + "WHERE\n"
                    + "    id_novedad_tipo_detalle = ?1\n"
                    + "        AND id_empleado = ?2\n"
                    + "        AND id_vehiculo = ?3\n"
                    + "        AND id_gop_unidad_funcional = ?4\n"
                    + "        AND fecha_cierre_recomoto IS NULL;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idNovDet);
            q.setParameter(2, idEmpleado);
            q.setParameter(3, idVehiculo);
            q.setParameter(4, idGopUF);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public List<Accidente> findAccidenteAbiertosRecomoto(Integer idVehiculo, Integer idEmpleado, Integer idNovDet, Date fechaIni, Date fechaFin, int idGopUF) {
        try {
            String sql = "SELECT * FROM accidente WHERE DATE(fecha_acc) BETWEEN ?2 AND ?3 ";
            String sql_unida_func = idGopUF == 0 ? "" : "        AND id_gop_unidad_funcional = ?1 ";
            if (idEmpleado != null) {
                sql = sql + "AND id_empleado = " + idEmpleado + " ";
            }
            if (idVehiculo != null) {
                sql = sql + "AND id_vehiculo = " + idVehiculo + " ";
            }
            if (idNovDet != null) {
                sql = sql + "AND id_novedad_tipo_detalle = " + idNovDet + " ";
            }
            sql = sql + sql_unida_func;
            sql = sql + "AND estado_reg = 0 ";
            sql = sql + "AND fecha_cierre_recomoto IS NULL";
            Query q = em.createNativeQuery(sql, Accidente.class);
            q.setParameter(1, idGopUF);
            q.setParameter(2, Util.dateFormat(fechaIni));
            q.setParameter(3, Util.dateFormat(fechaFin));
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ReporteLucroCesante> findByRangoFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND ac.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT \n"
                    + "    e.id_empleado,\n"
                    + "    v.codigo,\n"
                    + "    DATE_FORMAT(ac.fecha_acc,\"%Y-%m-%d %H:%i:%S\") as fecha_acc,\n"
                    + "    e.codigo_tm,\n"
                    + "    CONCAT(e.nombres, ' ', e.apellidos) AS nombre,\n"
                    + "    IF(av.id_acc_inmovilizado is not null or av.id_acc_inmovilizado <> 2,\n"
                    + "        av.fecha_salida_inmovilizado,\n"
                    + "        NULL) AS fecha_salida_inmovilizado,\n"
                    + "   IF(av.id_acc_inmovilizado is not null or av.id_acc_inmovilizado <> 2,\n"
                    + "        DATEDIFF(av.fecha_salida_inmovilizado,\n"
                    + "                ac.fecha_acc),\n"
                    + "        NULL) AS dias_patio,\n"
                    + "    IFNULL((SELECT \n"
                    + "                    SUM(acst.valor)\n"
                    + "                FROM\n"
                    + "                    accidente_costos acst\n"
                    + "                WHERE\n"
                    + "                    acst.id_accidente = ac.id_accidente\n"
                    + "                        AND atc.directo = 1),\n"
                    + "            0) AS costos_directos,\n"
                    + "    IFNULL((SELECT \n"
                    + "                    SUM(acst.valor)\n"
                    + "                FROM\n"
                    + "                    accidente_costos acst\n"
                    + "                WHERE\n"
                    + "                    acst.id_accidente = ac.id_accidente\n"
                    + "                        AND atc.directo <> 1),\n"
                    + "            0) AS costos_indirectos\n"
                    + "FROM\n"
                    + "    accidente ac\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON ac.id_empleado = e.id_empleado\n"
                    + "        INNER JOIN\n"
                    + "    vehiculo v ON ac.id_vehiculo = v.id_vehiculo\n"
                    + "        LEFT JOIN\n"
                    + "    accidente_vehiculo av ON ac.id_accidente = av.id_accidente\n"
                    + "        LEFT JOIN\n"
                    + "    accidente_costos acs ON ac.id_accidente = acs.id_accidente\n"
                    + "        LEFT JOIN\n"
                    + "    acc_tipo_costos atc ON acs.id_acc_tipo_costos = atc.id_acc_tipo_costos\n"
                    + "WHERE\n"
                    + "    DATE(ac.fecha_acc) BETWEEN ?1 AND ?2\n"
                    + "        AND ac.estado_reg = 0\n"
                    + sql_unida_func
                    + "GROUP BY e.id_empleado\n"
                    + "ORDER BY v.codigo, ac.fecha_acc;";

            Query query = em.createNativeQuery(sql, "ReporteLucroCesanteMapping");
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
    public List<BitacoraAccidentalidad> obtenerDatosBitacoraAcc(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND ac.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT DISTINCT ac.id_accidente,\n"
                    + "DATE_FORMAT(ac.fecha_acc, '%Y-%m-%d') AS fecha,\n"
                    + "DATE_FORMAT(ac.fecha_acc, '%H:%i:%S') AS hora,\n"
                    + "DATE_FORMAT(ac.fecha_asistencia, '%H:%i:%S') AS hora_asistida,\n"
                    + "DATE_FORMAT(ac.fecha_cierre_recomoto, '%H:%i:%S') AS hora_cierre_asistida,\n"
                    + "DATE_FORMAT(ac.fecha_cierre, '%H:%i:%S') AS hora_cierre_caso,\n"
                    + "IFNULL(rt.name,'') AS ruta,\n"
                    + "(SELECT al.direccion from accidente_lugar al where al.id_accidente = ac.id_accidente) as direccion,\n"
                    + "v.placa,\n"
                    + "v.codigo,\n"
                    + "IFNULL(e.codigo_tm, '') AS codigo_operador,\n"
                    + "(CASE \n"
                    + "WHEN ac.caso_tm = 1 THEN 'SI'\n"
                    + "WHEN ac.caso_tm = 0 THEN 'NO'\n"
                    + "ELSE ''\n"
                    + "END) as caso_tm,\n"
                    + "(CASE \n"
                    + "WHEN ac.juridica = 1 THEN 'SI'\n"
                    + "WHEN ac.juridica = 0 THEN 'NO'\n"
                    + "ELSE ''\n"
                    + "END) as juridica,\n"
                    + "CONCAT(e.nombres, ' ', e.apellidos) AS nombre_operador,\n"
                    + "e.identificacion,\n"
                    + "det.titulo_tipo_novedad AS tipo_evento,\n"
                    + "IFNULL(cl.clase,'') AS clasificacion,\n"
                    + "IFNULL((SELECT \n"
                    + "arb.arbol\n"
                    + "FROM\n"
                    + "accidente_analisis aa\n"
                    + "INNER JOIN\n"
                    + "acc_arbol arb ON aa.id_acc_arbol = arb.id_acc_arbol\n"
                    + "WHERE\n"
                    + "aa.id_accidente = ac.id_accidente\n"
                    + " AND aa.estado_reg = 0\n"
                    + "ORDER BY aa.creado\n"
                    + "LIMIT 1),'') AS causalidad,\n"
                    + "IFNULL((SELECT \n"
                    + "     acc.causa\n"
                    + " FROM\n"
                    + "     accidente_analisis aa\n"
                    + "     INNER JOIN acc_causa acc on acc.id_acc_causa = aa.id_acc_causa\n"
                    + " WHERE\n"
                    + "     aa.id_accidente = ac.id_accidente\n"
                    + "         AND aa.estado_reg = 0\n"
                    + " ORDER BY aa.creado\n"
                    + "LIMIT 1),'') AS hipotesis,\n"
                    + "IFNULL(atv.nombre,'') AS estado_conciliacion,\n"
                    + "ac.valor_conciliado as valor_conciliado,\n"
                    + "CONCAT(ELT(WEEKDAY(ac.fecha_acc) + 1,\n"
                    + "             'Lunes',\n"
                    + "             'Martes',\n"
                    + "             'Miercoles',\n"
                    + "          'Jueves',\n"
                    + "          'Viernes',\n"
                    + "          'Sabado',\n"
                    + "          'Domingo')) AS dia_evento,\n"
                    + "DATE_FORMAT(ac.fecha_acc, '%H') AS franja_horaria,\n"
                    + "IFNULL((SELECT \n"
                    + "     GROUP_CONCAT(atv.tipo_vehiculo\n"
                    + "             SEPARATOR ',') AS tipo_vehiculos\n"
                    + " FROM\n"
                    + "     accidente_conductor acn\n"
                    + "         LEFT JOIN\n"
                    + "     accidente_vehiculo ave ON acn.id_accidente_vehiculo = ave.id_accidente_vehiculo\n"
                    + "         LEFT JOIN\n"
                    + "     acc_tipo_vehiculo atv ON ave.id_acc_tipo_vehiculo = atv.id_acc_tipo_vehiculo\n"
                    + " WHERE\n"
                    + "     atv.id_acc_tipo_vehiculo IS NOT NULL\n"
                    + "     AND ave.codigo_vehiculo IS NULL\n"
                    + "	 AND acn.id_accidente = ac.id_accidente),'') AS tipo_vehiculo_tercero,\n"
                    + "     CASE\n"
                    + "    WHEN (\n"
                    + "        SELECT \n"
                    + "             GROUP_CONCAT(atv.tipo_vehiculo SEPARATOR ',') AS tipo_vehiculos\n"
                    + "         FROM\n"
                    + "             accidente_conductor acn\n"
                    + "             LEFT JOIN\n"
                    + "             accidente_vehiculo ave ON acn.id_accidente_vehiculo = ave.id_accidente_vehiculo\n"
                    + "             LEFT JOIN\n"
                    + "             acc_tipo_vehiculo atv ON ave.id_acc_tipo_vehiculo = atv.id_acc_tipo_vehiculo\n"
                    + "         WHERE\n"
                    + "             atv.id_acc_tipo_vehiculo IS NOT NULL\n"
                    + "             AND ave.codigo_vehiculo IS NULL\n"
                    + "             AND acn.id_accidente = ac.id_accidente\n"
                    + "    ) = 'ZONAL' THEN (\n"
                    + "        SELECT emp.empresa_operadora \n"
                    + "        FROM rgmo.accidente_vehiculo v\n"
                    + "        INNER JOIN acc_empresa_operadora emp ON v.id_acc_empresa_operadora = emp.id_acc_empresa_operadora\n"
                    + "        WHERE v.id_accidente = ac.id_accidente\n"
                    + "        LIMIT 1 OFFSET 1\n"
                    + "    )\n"
                    + "    ELSE '' \n"
                    + "END AS empresa_operadora,\n"
                    + "(SELECT \n"
                    + "     IF(ave.id_acc_inmovilizado IS NOT NULL\n"
                    + "                 AND ave.id_acc_inmovilizado <> 2,\n"
                    + "             1,\n"
                    + "             0) AS inmovilizado\n"
                    + " FROM\n"
                    + "     accidente_vehiculo ave\n"
                    + " WHERE\n"
                    + "     ave.id_accidente = ac.id_accidente\n"
                    + "         AND ave.estado_reg = 0\n"
                    + " ORDER BY 1 DESC\n"
                    + "        LIMIT 1) AS inmovilizado,\n"
                    + " IF(ac.nro_ipat IS NOT NULL, 1, 0) AS ipat,\n"
                    + " (SELECT \n"
                    + "         observaciones\n"
                    + "     FROM\n"
                    + "         accidente_analisis aa\n"
                    + "     WHERE\n"
                    + "         aa.id_accidente = ac.id_accidente\n"
                    + "             AND aa.estado_reg = 0\n"
                    + "     ORDER BY creado\n"
                    + "     LIMIT 1) AS observacion,\n"
                    + " (SELECT \n"
                    + "         COUNT(avi.id_accidente_victima)\n"
                    + "     FROM\n"
                    + "         accidente_victima avi\n"
                    + "     WHERE\n"
                    + "         id_accidente = ac.id_accidente\n"
                    + "          AND avi.estado_reg = 0) AS victimas,\n"
                    + "IFNULL(acccosto.directo,0) as costos_directos, \n"
                    + "IFNULL(acccosto.indirecto,0) as costos_indirectos, \n"
                    + "(CASE WHEN nv.procede = 1 THEN nv.puntos_pm_conciliados ELSE 0 END) AS puntos_afectacion\n"
                    + "FROM\n"
                    + " accidente ac\n"
                    + "     LEFT JOIN\n"
                    + " empleado e ON ac.id_empleado = e.id_empleado\n"
                    + "     LEFT JOIN\n"
                    + " vehiculo v ON ac.id_vehiculo = v.id_vehiculo\n"
                    + "     INNER JOIN\n"
                    + " novedad_tipo_detalles det ON ac.id_novedad_tipo_detalle = det.id_novedad_tipo_detalle\n"
                    + "     LEFT JOIN\n"
                    + " novedad nv ON ac.id_novedad = nv.id_novedad\n"
                    + "     LEFT JOIN\n"
                    + " acc_clase cl ON ac.id_clase = cl.id_acc_clase\n"
                    + "     LEFT JOIN\n"
                    + "acc_atencion_via atv ON ac.id_acc_atencion_via = atv.id_acc_atencion_via\n"
                    + " LEFT JOIN\n"
                    + "prg_tc tc ON ac.id_prg_tc = tc.id_prg_tc\n"
                    + " LEFT JOIN\n"
                    + "prg_route rt ON tc.id_ruta = rt.id_prg_route\n"
                    + " LEFT JOIN\n"
                    + "accidente_vehiculo av ON ac.id_accidente = av.id_accidente\n"
                    + "LEFT JOIN (SELECT acst.id_accidente, \n"
                    + "	IFNULL(SUM( CASE WHEN atc.directo = 1 THEN acst.valor END),0) AS directo,\n"
                    + "	IFNULL(SUM( CASE WHEN atc.directo <> 1 THEN acst.valor END),0) AS indirecto\n"
                    + "	FROM accidente_costos acst\n"
                    + "	LEFT JOIN acc_tipo_costos atc ON acst.id_acc_tipo_costos = atc.id_acc_tipo_costos\n"
                    + "	group by 1) AS acccosto ON ac.id_accidente = acccosto.id_accidente\n"
                    + "WHERE\n"
                    + "    DATE(ac.fecha_acc) BETWEEN ?1 AND ?2\n"
                    + "        AND ac.estado_reg = 0\n"
                    + sql_unida_func
                    + "ORDER BY fecha asc, hora asc, v.codigo;";

            Query query = em.createNativeQuery(sql, "BitacoraAccMapping");
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
