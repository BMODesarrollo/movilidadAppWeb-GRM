package com.movilidad.ejb;

import com.movilidad.model.Novedad;
import com.movilidad.util.beans.AccidenteCtrl;
import com.movilidad.util.beans.InformeAccidente;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.ObjetoSigleton;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.text.SimpleDateFormat;
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
public class NovedadFacade extends AbstractFacade<Novedad> implements NovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadFacade() {
        super(Novedad.class);
    }

    @Override
    public Novedad findByNovedadDano(int id) {
        try {
            Query query = em.createQuery("SELECT n from Novedad n where n.idNovedadDano.idNovedadDano = :n")
                    .setParameter("n", id);
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Novedad findByMulta(int id) {
        try {
            Query query = em.createQuery("SELECT n from Novedad n where n.idMulta.idMulta = :multa ")
                    .setParameter("multa", id);
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                    : "  AND nv.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query query = em.createQuery("SELECT nv from Novedad nv where nv.estadoReg = 0 AND nv.fecha "
                    + "BETWEEN :fechaIni AND :fechaFin "
                    + sql_unida_func
                    + "ORDER BY nv.fecha DESC");
            query.setParameter("fechaIni", Util.toDate(Util.dateFormat(fechaIni)));
            query.setParameter("fechaFin", Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> getNovedadesMaestroConsulta(Date fechaInicio, Date fechaFin, List<Integer> detalles,
            int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                    : "  AND n.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query query = em.createQuery("SELECT n FROM Novedad n WHERE n.fecha BETWEEN :fechaInicio AND :fechaFin "
                    + sql_unida_func
                    + "AND n.idNovedadTipoDetalle.idNovedadTipoDetalle IN :detalles ORDER BY n.fecha DESC",
                    Novedad.class);
            query.setParameter("fechaInicio", Util.toDate(Util.dateFormat(fechaInicio)));
            query.setParameter("fechaFin", Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter("detalles", detalles);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Novedad> findAll(Date fecha) {
        Query query = em.createQuery("SELECT nv from Novedad nv "
                + "where nv.fecha = :fecha")
                .setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public List<Novedad> findByDateRangeForMtto(Date fechaIni, Date fechaFin) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query query = em.createNativeQuery("SELECT * from novedad  "
                    + "where fecha BETWEEN ?1 AND ?2 "
                    + "AND id_vehiculo is not null ORDER BY fecha DESC", Novedad.class)
                    .setParameter(1, c_fechaIni)
                    .setParameter(2, c_fechaFin);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> findChangeVehiculo(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?3\n";
            Query q = em.createNativeQuery("SELECT DISTINCT\n"
                    + "    n.id_novedad,\n"
                    + "    n.fecha,\n"
                    + "    n.id_novedad_tipo,\n"
                    + "    n.id_novedad_tipo_detalle,\n"
                    + "    n.id_empleado,\n"
                    + "    n.desde,\n"
                    + "    n.hasta,\n"
                    + "    n.observaciones,\n"
                    + "    n.id_novedad_dano,\n"
                    + "    n.id_multa,\n"
                    + "    n.id_vehiculo,\n"
                    + "    p.id_vehiculo AS old_vehiculo,\n"
                    + "    n.puntos_pm,\n"
                    + "    n.puntos_pm_conciliados,\n"
                    + "    n.procede,\n"
                    + "    n.liquidada,\n"
                    + "    n.username,\n"
                    + "    n.creado,\n"
                    + "    n.modificado,\n"
                    + "    n.estado_reg\n"
                    + "FROM\n"
                    + "    novedad n\n"
                    + "        INNER JOIN\n"
                    + "    prg_tc p ON p.old_vehiculo = n.id_vehiculo\n"
                    + "        AND (p.username = n.username or p.username='admin')\n"
                    + "        AND p.fecha = n.fecha\n"
                    + "        AND p.old_vehiculo IS NOT NULL\n"
                    + "WHERE\n"
                    + "    n.fecha BETWEEN ?1 AND ?2 \n"
                    + sql_unida_func
                    + ";        ", Novedad.class);
            q.setParameter(1, Util.dateFormat(fechaIni));
            q.setParameter(2, Util.dateFormat(fechaFin));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> findAllForMtto(Date fecha) {
        try {
            String c_fecha = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
            Query query = em.createNativeQuery("SELECT * from novedad "
                    + "where fecha = ?1 "
                    + "and id_vehiculo is not null ORDER BY fecha DESC", Novedad.class)
                    .setParameter(1, c_fecha);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void desasignarOperador(Date fechaIni, Date fechaFin, int codEmpleado) {
        Query query = em.createNativeQuery("UPDATE prg_tc p SET p.id_empleado = NULL "
                + "WHERE p.fecha "
                + "BETWEEN ?1 AND ?2 AND p.id_empleado = ?3 ;");
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, codEmpleado);
        query.executeUpdate();
    }

    @Override
    public List<Novedad> liquidaPM() {
        try {
            Query query = em.createNativeQuery("SELECT * FROM novedad INNER JOIN novedad_tipo_detalles "
                    + "ON novedad.id_novedad_tipo_detalle = novedad_tipo_detalles.id_novedad_tipo_detalle "
                    + "WHERE novedad_tipo_detalles.afecta_pm = 1 AND novedad.estado_reg = 0 "
                    + "ORDER BY novedad.fecha DESC", Novedad.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> findByDateRangeForLiquidaPM(Date fechaIni, Date fechaFin) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query query = em.createNativeQuery("SELECT * FROM novedad INNER JOIN novedad_tipo_detalles "
                    + "ON novedad.id_novedad_tipo_detalle = novedad_tipo_detalles.id_novedad_tipo_detalle "
                    + "WHERE novedad.fecha BETWEEN ?1 AND ?2 "
                    + "AND novedad_tipo_detalles.afecta_pm = 1 AND novedad.estado_reg = 0 "
                    + "ORDER BY novedad.fecha DESC", Novedad.class)
                    .setParameter(1, c_fechaIni)
                    .setParameter(2, c_fechaFin);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Novedad verificarNovedadPMSinFechas(Date fecha, int idEmpleado, int idNovedadTipoDetalle) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM novedad n WHERE n.id_empleado = ?1 "
                    + "and id_novedad_tipo_detalle =?2  and n.fecha = ?3 limit 1;", Novedad.class);
            query.setParameter(1, idEmpleado);
            query.setParameter(2, idNovedadTipoDetalle);
            query.setParameter(3, Util.dateFormat(fecha));
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Novedad validarNovedadConFechas(int empleado, Date desde, Date hasta) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM novedad n "
                    + "WHERE ((n.desde BETWEEN ?2 AND  ?3) "
                    + "OR (n.hasta BETWEEN ?2 AND  ?3)) AND n.id_empleado= ?1 LIMIT 1;", Novedad.class);
            query.setParameter(1, empleado);
            query.setParameter(2, Util.dateFormat(desde));
            query.setParameter(3, Util.dateFormat(hasta));
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Novedad validarNovedad(int empleado, int idDetalleTipoNovedad) {
        try {
            Query query = em.createNativeQuery("SELECT n.* FROM novedad n "
                    + "WHERE n.id_novedad_tipo_detalle = ?2 "
                    + "AND n.id_empleado = ?1 "
                    + "AND fecha = ?3", Novedad.class);
            query.setParameter(1, empleado);
            query.setParameter(2, idDetalleTipoNovedad);
            query.setParameter(3, Util.dateFormat(new Date()));
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> findByDateRangeAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    novedad\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN ?1 AND ?2\n"
                    + "        AND (id_novedad_tipo IN (3 , 6)\n"
                    + "        OR id_novedad_tipo_detalle IN (SELECT \n"
                    + "            id_novedad_tipo_detalle\n"
                    + "        FROM\n"
                    + "            novedad_tipo_detalles\n"
                    + "        WHERE\n"
                    + "            afecta_pm = ?3))\n"
                    + "        AND id_empleado = ?4;", Novedad.class)
                    .setParameter(1, Util.dateFormat(fechaIni))
                    .setParameter(2, Util.dateFormat(fechaFin))
                    .setParameter(3, 1)
                    .setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Novedad> findByDateRangeAndIdEmpleadoSenior(Date fechaIni, Date fechaFin, int idEmpleado,
            boolean procede) {
        try {
            String procedeQuery = procede ? " procede = 1 AND \n" : "";
            Query query = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    novedad\n"
                    + "WHERE\n"
                    + procedeQuery
                    + "fecha BETWEEN ?1 AND ?2\n"
                    + "        AND  (id_novedad_tipo_detalle in (select ntd.id_novedad_tipo_detalle from pm_novedad_incluir ntd where ntd.activo=?3 and ntd.estado_reg=0) or id_novedad_tipo_detalle is null)\n"
                    + "        AND id_empleado = ?4;", Novedad.class)
                    .setParameter(1, Util.dateFormat(fechaIni))
                    .setParameter(2, Util.dateFormat(fechaFin))
                    .setParameter(3, 1)
                    .setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoSeguim(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ns.id_novedad\n"
                    + "FROM\n"
                    + "    novedad_seguimiento ns\n"
                    + "WHERE\n"
                    + "    ns.id_novedad IN (SELECT \n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND (n.id_novedad_tipo IN (3 , 6)\n"
                    + "                OR n.id_novedad_tipo_detalle IN (SELECT \n"
                    + "                    ntd.id_novedad_tipo_detalle\n"
                    + "                FROM\n"
                    + "                    novedad_tipo_detalles ntd\n"
                    + "                WHERE\n"
                    + "                    ntd.afecta_pm = 1))\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, c_fechaIni);
            q.setParameter(2, c_fechaFin);
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoSeguimSenior(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ns.id_novedad\n"
                    + "FROM\n"
                    + "    novedad_seguimiento ns\n"
                    + "WHERE\n"
                    + "    ns.id_novedad IN (SELECT \n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND (n.id_novedad_tipo_detalle in (select ntd.id_novedad_tipo_detalle from pm_novedad_incluir ntd where ntd.activo=1 and ntd.estado_reg=0) or n.id_novedad_tipo_detalle is null)\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, c_fechaIni);
            q.setParameter(2, c_fechaFin);
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoDocu(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query q = em.createNativeQuery("SELECT \n"
                    + "    nd.id_novedad\n"
                    + "FROM\n"
                    + "    novedad_documentos nd\n"
                    + "WHERE\n"
                    + "    nd.id_novedad IN (SELECT \n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND (n.id_novedad_tipo IN (3 , 6)\n"
                    + "                OR n.id_novedad_tipo_detalle IN (SELECT \n"
                    + "                    ntd.id_novedad_tipo_detalle\n"
                    + "                FROM\n"
                    + "                    novedad_tipo_detalles ntd\n"
                    + "                WHERE\n"
                    + "                    ntd.afecta_pm = 1))\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, c_fechaIni);
            q.setParameter(2, c_fechaFin);
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoDocuSenior(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            String c_fechaIni = new SimpleDateFormat("yyyy-MM-dd").format(fechaIni);
            String c_fechaFin = new SimpleDateFormat("yyyy-MM-dd").format(fechaFin);
            Query q = em.createNativeQuery("SELECT \n"
                    + "    nd.id_novedad\n"
                    + "FROM\n"
                    + "    novedad_documentos nd\n"
                    + "WHERE\n"
                    + "    nd.id_novedad IN (SELECT \n"
                    + "            n.id_novedad\n"
                    + "        FROM\n"
                    + "            novedad n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND  (n.id_novedad_tipo_detalle in (select ntd.id_novedad_tipo_detalle from pm_novedad_incluir ntd where ntd.activo=1 and ntd.estado_reg=0) or n.id_novedad_tipo_detalle is null)\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, c_fechaIni);
            q.setParameter(2, c_fechaFin);
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<AccidenteCtrl> obtenerDetalleAccidente(Date fecha, int idGopUnidadFuncional) {
        try {
            // String sql = "select\n"
            // + " e.codigo_tm as codigo_operador,\n"
            // + " v.codigo as codigo_vehiculo,\n"
            // + " CONCAT(e.nombres, ' ', e.apellidos) as nombre_operador,\n"
            // + " COUNT(if(n.id_novedad_tipo_detalle = 23, 1, null)) as incidente,\n"
            // + " COUNT(if(n.id_novedad_tipo_detalle = 25, 1, null)) as percance,\n"
            // + " COUNT(if(n.id_novedad_tipo_detalle = 16, 1, null)) as TM01,\n"
            // + " COUNT(if(n.id_novedad_tipo_detalle = 20, 1, null)) as TM02,\n"
            // + " COUNT(if(n.id_novedad_tipo_detalle = 21, 1, null)) as TM16\n"
            // + "from\n"
            // + " novedad n\n"
            // + "inner join vehiculo v on\n"
            // + " n.id_vehiculo = v.id_vehiculo\n"
            // + "inner join empleado e on\n"
            // + " n.id_empleado = e.id_empleado\n"
            // + "where\n"
            // + " n.id_novedad_tipo = 2\n"
            // + " and fecha = ?1\n"
            // + " and n.id_novedad_tipo_detalle in (16,20,21,23,25)\n"
            // + "group by\n"
            // + " n.id_empleado,n.id_vehiculo;";
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?2\n";
            String sql = "select\n"
                    + "	e.codigo_tm as codigo_operador,\n"
                    + "	v.codigo as codigo_vehiculo,\n"
                    + "	CONCAT(e.nombres, ' ', e.apellidos) as nombre_operador,\n"
                    + "	COUNT(if(nt.titulo_tipo_novedad = 'Incidente', 1, null)) as incidente,\n"
                    + "	COUNT(if(nt.titulo_tipo_novedad = 'Percance', 1, null)) as percance,\n"
                    + "	COUNT(if(nt.titulo_tipo_novedad = 'TM01', 1, null)) as TM01,\n"
                    + "	COUNT(if(nt.titulo_tipo_novedad = 'TM02', 1, null)) as TM02,\n"
                    + "	COUNT(if(nt.titulo_tipo_novedad = 'TM16', 1, null)) as TM16\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "inner join vehiculo v on\n"
                    + "	n.id_vehiculo = v.id_vehiculo\n"
                    + "inner join novedad_tipo_detalles nt on\n"
                    + "	n.id_novedad_tipo_detalle = nt.id_novedad_tipo_detalle\n"
                    + "inner join empleado e on\n"
                    + "	n.id_empleado = e.id_empleado\n"
                    + "where\n"
                    + "	n.id_novedad_tipo = 2\n"
                    + "	and fecha = ?1\n"
                    + sql_unida_func
                    + "	and nt.titulo_tipo_novedad in ('Incidente','Percance','TM01','TM02','TM16')\n"
                    + "group by\n"
                    + "	n.id_empleado,\n"
                    + "	n.id_vehiculo;";
            Query query = em.createNativeQuery(sql, "AccidenteCtrlMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Novedad> getAccidentes(Date fechaIncio, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?3\n";
            String sql = "select\n"
                    + "	n.*\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "inner join vehiculo v on\n"
                    + "	n.id_vehiculo = v.id_vehiculo\n"
                    + "inner join empleado e on\n"
                    + "	n.id_empleado = e.id_empleado\n"
                    + "where\n"
                    + "	n.id_novedad_tipo = 2\n"
                    + sql_unida_func
                    + "	and fecha BETWEEN ?1 and ?2\n"
                    + "	and n.id_novedad_tipo_detalle in (16,20,21,23,25);";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Novedad> getQuejas(Date fechaIncio, Date fechaFin) {
        try {
            String sql = "select\n"
                    + "	n.*\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "where\n"
                    + "	n.id_novedad_tipo = 8\n"
                    + "	and fecha BETWEEN ?1 and ?2\n"
                    + "	and n.id_novedad_tipo_detalle = 91;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Novedad> getNovedadesSNC(Date fechaIncio, Date fechaFin) {
        try {
            String sql = "select\n"
                    + "	n.*\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "inner join novedad_tipo_detalles nt on\n"
                    + "	n.id_novedad_tipo_detalle = nt.id_novedad_tipo_detalle\n"
                    + "where\n"
                    + "	fecha between ?1 and ?2\n"
                    + "	and nt.id_snc_detalle is not null;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public InformeAccidente obtenerDetalleAccidente(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?3\n";
            String sql = "select\n"
                    + "	COUNT(if(n.id_novedad_tipo_detalle = 47, 1, null)) as incidente,\n"
                    + "	COUNT(if(n.id_novedad_tipo_detalle = 19, 1, null)) as TM01,\n"
                    + "	COUNT(if(n.id_novedad_tipo_detalle = 45, 1, null)) as TM02,\n"
                    + "	COUNT(if(n.id_novedad_tipo_detalle = 46, 1, null)) as TM16\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "inner join vehiculo v on\n"
                    + "	n.id_vehiculo = v.id_vehiculo\n"
                    + "inner join empleado e on\n"
                    + "	n.id_empleado = e.id_empleado\n"
                    + "where\n"
                    + "	n.id_novedad_tipo = 2\n"
                    + sql_unida_func
                    + "	and fecha BETWEEN ?1 and ?2\n"
                    + "	and n.id_novedad_tipo_detalle in (19,45,46,47);";
            Query query = em.createNativeQuery(sql, "InformeAccidenteMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIni)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter(3, idGopUnidadFuncional);
            return (InformeAccidente) query.getSingleResult();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Novedad> findAusentismosByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
        String sql = "select * from \n"
                + "novedad \n"
                + "where id_novedad_tipo = 1\n"
                + "and id_novedad_tipo_detalle in (" + SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.KEY_IDS_DETS_AUSENTISMO)
                + ")\n"
                + sql_unida_func
                + "and procede = 1\n"
                + "and fecha between ?1 and ?2;";
        Query query = em.createNativeQuery(sql, Novedad.class);
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<Novedad> obtenerTq04(Date fechaIncio, Date fechaFin) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "inner join novedad_tipo_detalles ntd on\n"
                    + "	n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                    + "where ntd.titulo_tipo_novedad = 'TQ04 Bloqueado en vía'\n"
                    + "and fecha between ?1 and ?2;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Novedad> obtenerCambiosVehiculo(Date fechaIncio, Date fechaFin) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	novedad n\n"
                    + "where\n"
                    + "	fecha between ?1 and ?2\n"
                    + "	and n.old_vehiculo is not null ;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIncio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Novedad> findNovsAfectaDisp(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    df.*\n"
                + "FROM\n"
                + "    disponibilidad_flota_rigel df;", Novedad.class);
        // q.setParameter(1, Util.dateFormat(desde));
        // q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, ConstantsUtil.NOV_ESTADO_ABIERTO);
        q.setParameter(4, ConstantsUtil.MTTO_AFECTA_DISPONIBILIDAD);
        return q.getResultList();
    }

    @Override
    public int updateClasificacion(int idNovedad, int idClasificacionNov) {
        Query q = em.createNativeQuery("UPDATE novedad SET id_disp_clasificacion_novedad= ?2 "
                + "WHERE id_novedad= ?1");
        q.setParameter(1, idNovedad);
        q.setParameter(2, idClasificacionNov);
        return q.executeUpdate();
    }

    @Override
    public int updateEstadoNovedad(int idNovedad, int estadoNovedad, Date fechaHoraCierre, String usuarioCierre) {
        String sql_fecha_cierre = fechaHoraCierre == null ? "" : ", fecha_cierre= ?3 ";
        String sql_user_cierre = usuarioCierre == null ? "" : ", usuario_cierre= ?4 ";
        Query q = em.createNativeQuery("UPDATE novedad SET "
                + "estado_novedad= ?2 "
                + sql_fecha_cierre
                + sql_user_cierre
                + "WHERE id_novedad= ?1");
        q.setParameter(1, idNovedad);
        q.setParameter(2, estadoNovedad);
        q.setParameter(3, fechaHoraCierre);
        q.setParameter(4, usuarioCierre);
        return q.executeUpdate();
    }

    @Override
    public Novedad findNovedadByIdVehiculo(Integer idVehiculo, int estadoNovedad) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    novedad n\n"
                    + "        LEFT JOIN\n"
                    + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                    + "WHERE\n"
                    + "    n.id_vehiculo = ?1 AND n.estado_reg = 0\n"
                    + "        AND ntd.afecta_disponibilidad = 1\n"
                    + "        AND ntd.estado_reg = 0\n"
                    + "        AND n.estado_novedad = ?2\n"
                    + "        ORDER BY n.fecha DESC LIMIT 1", Novedad.class);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, estadoNovedad);
            return (Novedad) q.getSingleResult();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Novedad> findNovsAfectaDispByFecha(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    n.*\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        LEFT JOIN\n"
                + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "WHERE\n"
                + "    ntd.afecta_disponibilidad = ?4\n"
                + "        AND n.fecha BETWEEN ?1 AND ?2\n"
                // + " AND n.estado_novedad <> ?3\n"
                + "        AND n.estado_reg = 0\n"
                + "        AND ntd.estado_reg = 0 ORDER BY n.creado ASC;", Novedad.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        // q.setParameter(3, ConstantsUtil.NOV_ESTADO_CERRADO);
        q.setParameter(4, ConstantsUtil.MTTO_AFECTA_DISPONIBILIDAD);
        return q.getResultList();
    }

    @Override
    public List<Novedad> findByDateRangeAndIdEmpleadoAndIdGopUnidadFunc(Date fechaIni,
            Date fechaFin, int idEmpleadoMaster, int idGopUnidadFuncional, int idGrupo) {

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         n.id_gop_unidad_funcional = ?6 AND\n";

        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    novedad n\n"
                    + "WHERE\n"
                    + sql_unida_func
                    + "n.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND (n.id_novedad_tipo_detalle IN (select ntd.id_novedad_tipo_detalle from pm_novedad_incluir ntd where ntd.activo=1 and ntd.estado_reg=0) or n.id_novedad_tipo_detalle is null)\n"
                    + "        AND (n.id_empleado IN ((SELECT \n"
                    + "            pgd.id_empleado\n"
                    + "        FROM\n"
                    + "            pm_grupo_detalle pgd\n"
                    + "        WHERE\n"
                    + "            pgd.id_grupo = ?5 AND pgd.estado_reg = 0))\n"
                    + "        OR (n.id_empleado = ?4));", Novedad.class)
                    .setParameter(1, Util.dateFormat(fechaIni))
                    .setParameter(2, Util.dateFormat(fechaFin))
                    .setParameter(3, 1)
                    .setParameter(4, idEmpleadoMaster)
                    .setParameter(5, idGrupo)
                    .setParameter(6, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Novedad> findNovsAfectaDispByFechaAndEstado(Date desde, Date hasta, Integer estadoNovedad) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    n.*\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        LEFT JOIN\n"
                + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "WHERE\n"
                + "    ntd.afecta_disponibilidad = ?4\n"
                + "        AND n.fecha BETWEEN ?1 AND ?2\n"
                + "        AND n.estado_novedad = ?3\n"
                + "        AND n.estado_reg = 0\n"
                + "        AND ntd.estado_reg = 0 ORDER BY n.creado ASC;", Novedad.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, estadoNovedad);
        // q.setParameter(3, ConstantsUtil.NOV_ESTADO_CERRADO);
        q.setParameter(4, ConstantsUtil.MTTO_AFECTA_DISPONIBILIDAD);
        return q.getResultList();
    }

    @Override
    public Long existLiquidacionByFecha(Date d) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "   COUNT(*) AS liquidado\n"
                    + "FROM\n"
                    + "    tbl_liquidacion_empleado_mes\n"
                    + "WHERE\n"
                    + "    DATE(?1) BETWEEN desde AND hasta");
            q.setParameter(1, Util.dateFormat(d));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Long findTotalNovedadesAtvByEstado(Date fecha, int estado_nov, int idGopUnidadFuncional,
            Date fechaUltimoCierre) {
        String sql_uf = idGopUnidadFuncional == 0 ? "" : "        AND n.id_gop_unidad_funcional = ?3\n";
        String sql_fecha_ultimo_cierre = fechaUltimoCierre == null ? "" : "        AND TIME(n.creado) >= TIME(?4)\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    COUNT(n.id_novedad) as total\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        x.*\n"
                + "    FROM\n"
                + "        novedad_tipo_detalles x\n"
                + "    WHERE\n"
                + "        x.atv = 1 AND x.estado_reg = 0) ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "WHERE\n"
                + "    n.estado_novedad = ?2\n"
                + "        AND n.estado_reg = 0\n"
                + "        AND n.fecha_recibido_atv IS NULL"
                + sql_uf
                + sql_fecha_ultimo_cierre
                + "        AND DATE(n.fecha) = DATE(?1)");
        try {
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, estado_nov);
            q.setParameter(3, idGopUnidadFuncional);
            q.setParameter(4, Util.dateTimeFormat(fechaUltimoCierre));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public Long totalAusentismos(Date fecha, int idGopUnidadFunc, int idNovedaTipoAusentismo, Date fechaUltimoCierre) {
        String sql_uf = idGopUnidadFunc == 0 ? "" : "        AND n.id_gop_unidad_funcional = ?2\n";
        String sql_fecha_ultimo_cierre = fechaUltimoCierre == null ? "" : "        AND TIME(n.creado) >= TIME(?4)\n";
        try {
            String sql = "SELECT \n"
                    + "    count(n.id_novedad) as valor\n"
                    + "FROM\n"
                    + "    novedad n\n"
                    + "WHERE\n"
                    + "    DATE(n.fecha) = DATE(?1)\n"
                    + "        AND n.id_novedad_tipo=?3\n"
                    + sql_uf
                    + sql_fecha_ultimo_cierre
                    + "        AND n.estado_reg=0;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidadFunc);
            q.setParameter(3, idNovedaTipoAusentismo);
            q.setParameter(4, Util.dateTimeFormat(fechaUltimoCierre));
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            return Long.valueOf(0);
        }

    }

    @Override
    public List<Novedad> findNovsAfectaATV(Date desde, Date hasta, int idGopUF) {
        String uf = idGopUF != 0 ? " AND n.id_gop_unidad_funcional = " + idGopUF + " " : " ";
        Query q = em.createNativeQuery("SELECT \n"
                + "    n.*\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        LEFT JOIN\n"
                + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "WHERE\n"
                + "    ntd.atv = 1\n"
                + "        AND n.fecha BETWEEN DATE(?1) AND DATE(?2)\n"
                + uf
                + "        AND n.estado_reg = 0\n"
                + "        AND n.id_atv_tipo_atencion IS NOT NULL\n"
                + "        AND n.id_vehiculo IS NOT NULL\n"
                + "        AND ntd.estado_reg = 0\n"
                + "ORDER BY n.creado DESC;", Novedad.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    @Override
    public int liquidarNovedadAtvByIdGopUFAndFechas(Integer idGopUF, Date desde, Date hasta) {
        String sql = "UPDATE novedad n\n"
                + "        INNER JOIN\n"
                + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "        INNER JOIN\n"
                + "    atv_vehiculos_atencion ava ON n.id_atv_vehiculo_atencion = ava.id_atv_vehiculos_atencion\n"
                + "        INNER JOIN\n"
                + "    vehiculo v ON n.id_vehiculo = v.id_vehiculo \n"
                + "SET \n"
                + "    n.liquidado_atv = 1,\n"
                + "    n.costo_liquidado_atv = (SELECT \n"
                + "            costo\n"
                + "        FROM\n"
                + "            atv_costo_servicio\n"
                + "        WHERE\n"
                + "            ((?2 BETWEEN desde AND hasta\n"
                + "                OR ?3 BETWEEN desde AND hasta)\n"
                + "                OR (desde BETWEEN ?2 AND ?3\n"
                + "                OR hasta BETWEEN ?2 AND ?3))\n"
                + "                AND id_atv_prestador = ava.id_atv_prestador\n"
                + "                AND id_vehiculo_tipo = v.id_vehiculo_tipo\n"
                + "                AND estado_reg = 0\n"
                + "        LIMIT 1)\n"
                + "WHERE\n"
                + "    ntd.atv = 1\n"
                + "        AND n.fecha BETWEEN DATE(?2) AND DATE(?3)\n"
                + "        AND n.estado_reg = 0\n"
                + "        AND n.id_vehiculo IS NOT NULL\n"
                + "        AND ntd.estado_reg = 0\n"
                + "        AND n.cierre_app_atv = 4\n"
                + "        AND n.id_gop_unidad_funcional = ?1\n"
                + "        AND n.fecha_recibido_atv IS NOT NULL\n"
                + "        AND n.liquidado_atv = 0";
        Query q = em.createNativeQuery(sql);
        q.setParameter(1, idGopUF);
        q.setParameter(2, Util.dateFormat(desde));
        q.setParameter(3, Util.dateFormat(hasta));
        return q.executeUpdate();
    }

    @Override
    public List<Novedad> findNovsAfectaATVByPropietario(Date fecha, Integer idAtvPrestador) {
        String sql = "SELECT \n"
                + "    n.*\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        LEFT JOIN\n"
                + "    novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                + "        INNER JOIN\n"
                + "    atv_vehiculos_atencion avt ON n.id_atv_vehiculo_atencion = avt.id_atv_vehiculos_atencion\n"
                + "WHERE\n"
                + "    ntd.atv = 1 AND MONTH(fecha) = ?1\n"
                + "        AND YEAR(fecha) = ?2\n"
                + "        AND n.estado_reg = 0\n"
                + "        AND n.id_vehiculo IS NOT NULL\n"
                + "        AND n.id_atv_tipo_atencion IS NOT NULL\n"
                + "        AND ntd.estado_reg = 0\n"
                + "        AND avt.id_atv_prestador = ?3\n"
                + "ORDER BY n.creado DESC";
        Query q = em.createNativeQuery(sql, Novedad.class);
        q.setParameter(1, Util.monthOfYear(fecha));
        q.setParameter(2, Util.year(fecha));
        q.setParameter(3, idAtvPrestador);
        return q.getResultList();
    }

    @Override
    public List<Novedad> findNovedadAtvLiquidadaByPropietario(Date desde, Date hasta, Integer idAtvPrestador,
            int idGopUF) {
        String uf = idGopUF == 0 ? " " : " AND n.id_gop_unidad_funcional = " + idGopUF + " ";
        String sql = "SELECT \n"
                + "    n.*\n"
                + "FROM\n"
                + "    novedad n\n"
                + "        INNER JOIN\n"
                + "    atv_vehiculos_atencion ava ON n.id_atv_vehiculo_atencion = ava.id_atv_vehiculos_atencion\n"
                + "WHERE\n"
                + "    n.fecha BETWEEN DATE(?1) AND DATE(?2)\n"
                + "        AND n.id_atv_tipo_atencion IS NOT NULL\n"
                + "        AND n.liquidado_atv = 1\n"
                + "        AND ava.id_atv_prestador = ?3\n"
                + uf
                + "        AND n.estado_reg = 0\n"
                + "ORDER BY n.creado DESC";
        Query q = em.createNativeQuery(sql, Novedad.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idAtvPrestador);
        return q.getResultList();
    }

    @Override
    public List<Novedad> findAusentismosAutorizacionNovedadByDateRange(Date fechaIni, Date fechaFin,
            int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "select * from \n"
                    + "novedad \n"
                    + "where id_novedad_tipo = "
                    + SingletonConfigEmpresa.getMapConfiMapEmpresa().get("KEY_ID_NOV_AUSENTISMO") + "\n"
                    + "and id_novedad_tipo_detalle in (SELECT \n"
                    + "    ntd.id_novedad_tipo_detalle\n"
                    + "FROM\n"
                    + "    nomina_server_param_det det\n"
                    + "        INNER JOIN\n"
                    + "    novedad_tipo_detalles ntd ON det.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                    + "WHERE\n"
                    + "    ntd.id_novedad_tipo = 1 and det.estado_reg = 0)\n"
                    + sql_unida_func
                    + " and id_empleado is not null\n"
                    + " and procede = 1\n"
                    + "and fecha between ?1 and ?2;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Novedad> obtenerAusentismosConsulta(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
        String sql = "select * from \n"
                + "novedad \n"
                + "where id_novedad_tipo = 1\n"
                + sql_unida_func
                + "and procede = 1\n"
                + " and (fecha between ?1 and ?2\n"
                + " or ((?1 BETWEEN desde AND hasta\n"
                + "                OR ?2 BETWEEN desde AND hasta)\n"
                + "                OR (desde BETWEEN ?1 AND ?2\n"
                + "                OR hasta BETWEEN ?1 AND ?2)))\n"
                + " order by id_empleado,fecha;";
        Query query = em.createNativeQuery(sql, Novedad.class);
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    public List<Novedad> getAusentismosByRangoFechasAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado,
            int idNovedad) {
        String sql = "select * from \n"
                + "novedad \n"
                + "where id_novedad_tipo = " + SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.KEY_ID_NOV_AUSENTISMO)
                + "\n"
                + "and id_novedad <> ?4\n"
                + "and procede = 1\n"
                + "and id_empleado = ?3\n"
                + "and fecha between ?1 and ?2;";
        Query query = em.createNativeQuery(sql, Novedad.class);
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, idEmpleado);
        query.setParameter(4, idNovedad);
        return query.getResultList();
    }

    @Override
    public void desasignarOperadorAusentismo(Date fechaIni, Date fechaFin, int idEmpleado) {
        Query query = em.createNativeQuery("UPDATE prg_tc p SET p.id_empleado = NULL, \n"
                + "p.old_empleado = ?3 \n"
                + "WHERE p.fecha \n"
                + "BETWEEN ?1 AND ?2 AND p.id_empleado = ?3 ;");
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, idEmpleado);
        query.executeUpdate();
    }

    @Override
    public Novedad findNovedadAfectaDisponibilidadFechaVehiculo(int idVehiculo, Date d) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "n.*\n"
                    + "FROM\n"
                    + "novedad n\n"
                    + "LEFT JOIN\n"
                    + "novedad_tipo_detalles ntd ON n.id_novedad_tipo_detalle = ntd.id_novedad_tipo_detalle\n"
                    + "WHERE\n"
                    + "ntd.afecta_disponibilidad = 1\n"
                    + "AND DATE(n.fecha) = ?2\n"
                    + "AND n.id_vehiculo = ?1\n"
                    + "AND n.estado_reg = 0\n"
                    + "AND ntd.estado_reg = 0\n"
                    + "LIMIT 1", Novedad.class);
            q.setParameter(1, idVehiculo);
            q.setParameter(2, d);
            // q.setParameter(1, idArea);
            return (Novedad) q.getSingleResult();
        } catch (Exception e) {
            return new Novedad();
        }
    }

    @Override
    public boolean findNovedadByUserCreate(int idNovedad, String username, Date fecha) {
        try {
            Query query = em.createNativeQuery("SELECT nv.* FROM novedad nv "
                    + " WHERE nv.username = ?1 \n"
                    + " AND nv.estado_reg = 0 \n"
                    + " AND nv.usuario_cierre IS null\n"
                    + " AND nv.fecha_cierre IS null\n"
                    + " AND nv.fecha = DATE(?3)\n"
                    + " AND nv.id_novedad = ?2;");
            query.setParameter(1, username);
            query.setParameter(2, idNovedad);
            query.setParameter(3, fecha);
            return query.getResultList() != null && !query.getResultList().isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Novedad findUltimoAusentismoByDateRangeEmple(Date fecha, int idEmpleado) {
        try {
            String sql = "select * from \n"
                    + "novedad \n"
                    + "where id_novedad_tipo = 1\n"
                    + "and id_novedad_tipo_detalle in (" + SingletonConfigEmpresa.getMapConfiMapEmpresa()
                            .get(ConstantsUtil.KEY_IDS_DETS_AUSENTISMO)
                    + ") \n"
                    + "and procede = 1\n"
                    + "and fecha = ?1 and id_empleado= ?2;";
            Query query = em.createNativeQuery(sql, Novedad.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idEmpleado);
            return (Novedad) query.getSingleResult();
        } catch (Exception e) {
            return new Novedad();
        }
    }

    @Override
    public Novedad findNovedadInfraccion(int idTipoNovedad, Date fecha, int idEmpleado, int placaVehiculo,
            String observaciones) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "n.*\n"
                    + "FROM\n"
                    + "novedad n\n"
                    + "WHERE\n"
                    + "n.id_novedad_tipo = ?1\n"
                    + "AND DATE(n.fecha) = ?2\n"
                    + "AND n.id_empleado = ?3\n"
                    + "AND n.id_vehiculo = ?4\n"
                    + "AND n.observaciones = ?5\n"
                    + "AND n.estado_reg = 0\n"
                    + "LIMIT 1", Novedad.class);
            q.setParameter(1, idTipoNovedad);
            q.setParameter(2, fecha);
            q.setParameter(3, idEmpleado);
            q.setParameter(4, placaVehiculo);
            q.setParameter(5, observaciones);
            return (Novedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> obtenerNovedadesSinRecapacitacion(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
        String sql = "SELECT *\n"
                + "FROM novedad\n"
                + "WHERE fecha BETWEEN ?1 AND ?2\n"
                + "AND estado_reg = 0\n"
                + "AND id_novedad_tipo IN (2, 12) AND id_novedad_tipo_detalle in (19,47,99,100,101)"
                + sql_unida_func
                + "AND id_novedad NOT IN (\n"
                + "    SELECT id_novedad\n"
                + "    FROM recapacitacion_maestro\n"
                + ")"
                + "ORDER BY fecha ASC;";
        Query query = em.createNativeQuery(sql, Novedad.class);
        query.setParameter(1, Util.dateFormat(fechaIni));
        query.setParameter(2, Util.dateFormat(fechaFin));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }
}
