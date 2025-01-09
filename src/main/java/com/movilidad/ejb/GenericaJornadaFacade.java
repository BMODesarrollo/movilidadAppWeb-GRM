/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.aja.jornada.model.GenericaJornadaLiqUtil;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaInicial;
import com.movilidad.util.beans.ConsolidadoDetalladoCAM;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.util.beans.ConsolidadoLiquidacionCAM;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.LiquidacionSercon;
import com.movilidad.util.beans.PrenominaCAM;
import com.movilidad.util.beans.ReporteHorasCM;
import com.movilidad.util.beans.ReporteInterventoria;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class GenericaJornadaFacade extends AbstractFacade<GenericaJornada> implements GenericaJornadaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaFacade() {
        super(GenericaJornada.class);
    }

    @Override
    public List<GenericaJornada> getByDate(Date desde, Date hasta, int idArea) {
        try {
            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    generica_jornada p\n"
                    + "        RIGHT JOIN\n"
                    + "    empleado e ON e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "    (p.fecha BETWEEN ?1 AND ?2)\n"
                    + "        AND p.estado_reg = 0 AND p.id_param_area= ?3\n"
                    + "ORDER BY e.identificacion ASC , p.fecha ASC;", GenericaJornada.class)
                    .setParameter(1, Util.toDate(Util.dateFormat(desde)))
                    .setParameter(3, idArea)
                    .setParameter(2, Util.toDate(Util.dateFormat(hasta))).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaJornada validarEmplSinJornada(int idEmpleado, Date fecha) {
        try {
            return (GenericaJornada) this.em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada g\n"
                    + "WHERE\n"
                    + "    g.id_empleado = ?1 AND g.fecha = ?2;", GenericaJornada.class)
                    .setParameter(1, idEmpleado)
                    .setParameter(2, Util.dateFormat(fecha))
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public long validarPeriodoLiquidadoEmpleado(Date fromDate, Date toDate, int idEmpleado) {
        try {
            return (long) this.em.createNativeQuery("SELECT \n"
                    + "    COUNT(g.id_generica_jornada)\n"
                    + "FROM\n"
                    + "    generica_jornada g\n"
                    + "WHERE\n"
                    + "    g.liquidado = 1\n"
                    + "        AND g.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND g.id_empleado = ?3;")
                    .setParameter(1, Util.dateFormat(fromDate))
                    .setParameter(2, Util.dateFormat(toDate))
                    .setParameter(3, idEmpleado)
                    .getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
            return 0;
        }
    }

    @Override
    public void borradoMasivo(int idEmpleado, int idMotivo, Date desde, Date hasta, String observacion, String username, int valorBorrado) {
        Query q = em.createNativeQuery("UPDATE generica_jornada p \n"
                + "SET \n"
                + "    p.observaciones = ?1,\n"
                + "    p.prg_modificada = 1,\n"
                + "    p.autorizado = 1,\n"
                + "    p.user_autorizado = ?2,\n"
                + "    p.user_genera = ?3,\n"
                + "    p.fecha_genera = ?4,\n"
                + "    p.fecha_autoriza = ?4,\n"
                + "    p.nomina_borrada = ?6,\n"
                + "    p.id_generica_jornada_motivo = ?7,\n"
                + "    p.modificado = ?4,\n"
                + "    p.username = ?11\n"
                + "WHERE\n"
                + "    p.id_empleado = ?8\n"
                + "        AND p.fecha BETWEEN ?12 AND ?13 AND p.liquidado<>1");
        q.setParameter(1, observacion);
        q.setParameter(2, username);
        q.setParameter(3, username);
        q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(6, valorBorrado);
        q.setParameter(7, idMotivo);
        q.setParameter(8, idEmpleado);
        q.setParameter(11, username);
        q.setParameter(12, Util.dateFormat(desde));
        q.setParameter(13, Util.dateFormat(hasta));
        q.executeUpdate();
    }

    @Override
    public void update(LiquidacionSercon l) {
        try {
            String consulta = "UPDATE generica_jornada g SET "
                    + "g.diurnas = '" + l.getDiurnas() + "', "
                    + "g.nocturnas = '" + l.getNocturnas() + "', "
                    + "g.extra_diurna = '" + l.getExtraDiurna() + "', "
                    + "g.extra_nocturna = '" + l.getExtraNocturna() + "', "
                    + "g.festivo_diurno = '" + l.getFestivoDiurno() + "', "
                    + "g.festivo_nocturno = '" + l.getFestivoNocturno() + "', "
                    + "g.festivo_extra_diurno = '" + l.getFestivoExtraDiurno() + "', "
                    + "g.festivo_extra_nocturno = '" + l.getFestivoExtraNocturno() + "', "
                    + "g.compensatorio = '" + l.getCompensatorio() + "' "
                    + "WHERE g.fecha = '" + l.getFecha() + "' "
                    + "AND g.id_empleado = (SELECT id_empleado FROM empleado WHERE empleado.codigo_tm = '" + l.getCodigo() + "' AND empleado.estado_reg = 0) "
                    + "AND g.estado_reg = 0;";
            Query q = this.em.createNativeQuery(consulta);
            q.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update DaoPrgSerconImpl");
        }
    }

    @Override
    public void nominaBorrada(int idGenericaJornada, int valor, String username) {
        try {
            this.em.createNativeQuery("update generica_jornada g set g.nomina_borrada =?1, g.modificado=?3, g.username=?4 "
                    + "where g.id_generica_jornada=?2").setParameter(1, valor)
                    .setParameter(2, idGenericaJornada)
                    .setParameter(3, MovilidadUtil.fechaHoy())
                    .setParameter(4, username)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
        }
    }

    @Override
    public void liquidarPorRangoFecha(Date fromDate, Date toDate, String userName, int idArea) {
        try {
            this.em.createNativeQuery("UPDATE generica_jornada g "
                    + "SET "
                    + "    g.liquidado = 1, "
                    + "    g.user_liquida = ?3, "
                    + "    g.fecha_liquida = ?4, "
                    + "    g.modificado = ?5, "
                    + "    g.username = ?6 "
                    + "WHERE "
                    + "    g.fecha BETWEEN ?1 AND ?2 AND g.liquidado <> 1 "
                    + "    AND g.id_param_area = ?7;").setParameter(1, Util.dateFormat(fromDate))
                    .setParameter(2, Util.dateFormat(toDate))
                    .setParameter(3, userName)
                    .setParameter(4, MovilidadUtil.fechaCompletaHoy())
                    .setParameter(5, MovilidadUtil.fechaCompletaHoy())
                    .setParameter(6, userName)
                    .setParameter(7, idArea)
                    .executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en update nomina borrada");
        }
    }

    @Override
    public int liquidarPorId(int id, String userName) {
        try {
            return this.em.createNativeQuery("UPDATE generica_jornada g "
                    + "SET "
                    + "    g.liquidado = 1, "
                    + "    g.user_liquida = ?3, "
                    + "    g.fecha_liquida = ?4, "
                    + "    g.modificado = ?4, "
                    + "    g.username = ?3 "
                    + "WHERE "
                    + "    g.id_generica_jornada = ?2;").setParameter(2, id)
                    .setParameter(3, userName)
                    .setParameter(4, MovilidadUtil.fechaCompletaHoy())
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
            return 0;
        }
    }

    @Override
    public void cambioEmpleado(GenericaJornada jornada1, GenericaJornada jornada2) {
        try {

            Query q = em.createNativeQuery("UPDATE generica_jornada p "
                    + "SET "
                    + "p.observaciones = ?1, "
                    + "p.prg_modificada = 1, "
                    + "p.autorizado = 1, "
                    + "p.user_autorizado = ?2, "
                    + "p.user_genera = ?3, "
                    + "p.fecha_genera = ?4, "
                    + "p.fecha_autoriza = ?5, "
                    + "p.id_empleado = ?6, "
                    + "p.id_generica_jornada_motivo = ?7, "
                    + "p.modificado = ?10, "
                    + "p.username = ?11 "
                    + "WHERE p.id_generica_jornada=?8");
            q.setParameter(1, jornada1.getObservaciones());
            q.setParameter(2, jornada1.getUserAutorizado());
            q.setParameter(3, jornada1.getUserGenera());
            q.setParameter(4, jornada1.getFechaGenera());
            q.setParameter(5, jornada1.getFechaAutoriza());
            q.setParameter(6, jornada1.getIdEmpleado().getIdEmpleado());
            q.setParameter(7, jornada1.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            q.setParameter(8, jornada1.getIdGenericaJornada());
            q.setParameter(10, MovilidadUtil.fechaCompletaHoy());
            q.setParameter(11, jornada1.getUsername());
            q.executeUpdate();

            Query qq = em.createNativeQuery("UPDATE generica_jornada p "
                    + "SET "
                    + "p.observaciones = ?1, "
                    + "p.prg_modificada = 1, "
                    + "p.autorizado = 1, "
                    + "p.user_autorizado = ?2, "
                    + "p.user_genera = ?3, "
                    + "p.fecha_genera = ?4, "
                    + "p.fecha_autoriza = ?5, "
                    + "p.id_empleado = ?6, "
                    + "p.id_generica_jornada_motivo = ?7, "
                    + "p.modificado = ?10, "
                    + "p.username = ?11 "
                    + "WHERE p.id_generica_jornada=?8");
            qq.setParameter(1, jornada2.getObservaciones());
            qq.setParameter(2, jornada2.getUserAutorizado());
            qq.setParameter(3, jornada2.getUserGenera());
            qq.setParameter(4, jornada2.getFechaGenera());
            qq.setParameter(5, jornada2.getFechaAutoriza());
            qq.setParameter(6, jornada2.getIdEmpleado().getIdEmpleado());
            qq.setParameter(7, jornada2.getIdGenericaJornadaMotivo().getIdGenericaJornadaMotivo());
            qq.setParameter(8, jornada2.getIdGenericaJornada());
            qq.setParameter(10, MovilidadUtil.fechaCompletaHoy());
            qq.setParameter(11, jornada2.getUsername());

            qq.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public GenericaJornada getPrgSerconByCodigoTM(String codigoTM, Date fecha) {
        try {
            return (GenericaJornada) this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    generica_jornada p\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "    p.fecha = ?2\n"
                    + "        AND e.identificacion = ?1;", GenericaJornada.class)
                    .setParameter(1, codigoTM)
                    .setParameter(2, Util.dateFormat(fecha))
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaJornada> getJornadasByFechaAndArea(Date fecha, int idArea) {
        try {
            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    generica_jornada p\n"
                    + "WHERE\n"
                    + "    p.fecha = ?2\n"
                    + "        AND p.id_param_area = ?1;", GenericaJornada.class)
                    .setParameter(1, idArea)
                    .setParameter(2, Util.dateFormat(fecha))
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public int descansoEnSemana(Date lunes, Date domingo, int idEmpleado, int idArea) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    SUM(CASE\n"
                    + "        WHEN nomina_borrada = 1 THEN 1\n"
                    + "        ELSE CASE\n"
                    + "            WHEN\n"
                    + "                TIME_TO_SEC(time_origin) = 0\n"
                    + "                    AND (real_time_origin IS NULL\n"
                    + "                    OR (TIME_TO_SEC(real_time_origin) >= 0\n"
                    + "                    AND autorizado = 0))\n"
                    + "            THEN\n"
                    + "                1\n"
                    + "            ELSE CASE\n"
                    + "                WHEN\n"
                    + "                    TIME_TO_SEC(time_origin) >= 0\n"
                    + "                        AND TIME_TO_SEC(real_time_origin) = 0\n"
                    + "                        AND autorizado = 1\n"
                    + "                THEN\n"
                    + "                    1\n"
                    + "                ELSE 0\n"
                    + "            END\n"
                    + "        END\n"
                    + "    END) AS descanso\n"
                    + "FROM\n"
                    + "    generica_jornada\n"
                    + "WHERE\n"
                    + "    id_param_area = ?4\n"
                    + "        AND fecha BETWEEN ?1 AND ?2\n"
                    + "        AND fecha NOT IN (SELECT \n"
                    + "            fecha\n"
                    + "        FROM\n"
                    + "            param_feriado\n"
                    + "        WHERE\n"
                    + "            fecha BETWEEN ?1 AND ?2)\n"
                    + "        AND id_empleado = ?3");
            q.setParameter(1, Util.dateFormat(lunes));
            q.setParameter(2, Util.dateFormat(domingo));
            q.setParameter(3, idEmpleado);
            q.setParameter(4, idArea);
            if (q.getSingleResult() == null) {
                return 0;
            }
            return ((BigDecimal) q.getSingleResult()).intValue();
        } catch (Exception e) {
//            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<GenericaJornada> getJornadasByDateAndEmpleado(Date lunes, Date domingo, int idEmpleado) {
        try {

            Query q = em.createNativeQuery("SELECT "
                    + "gj.* "
                    + "FROM "
                    + "generica_jornada gj "
                    + "WHERE "
                    + "gj.id_empleado= ?3 "
                    + "AND "
                    + "gj.fecha BETWEEN ?1 AND ?2 AND liquidado<>1;", GenericaJornada.class);
            q.setParameter(1, Util.dateFormat(lunes));
            q.setParameter(2, Util.dateFormat(domingo));
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ReporteInterventoria> obtenerDatosInfoInterventoriaConArea(Date desde, Date hasta, Integer[] areas) {
        try {
            String sql = "SELECT\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion as cedula,\n"
                    + "	UCASE(CONCAT(e.apellidos,' ',e.nombres)) as nombre_completo,\n"
                    + "	etc.nombre_cargo,\n"
                    + "	CASE\n"
                    + "		WHEN TIME_TO_SEC(gj.real_time_origin) > 0\n"
                    + "		AND nomina_borrada = 0\n"
                    + "		AND autorizado = 1 THEN gj.real_time_origin\n"
                    + "		ELSE\n"
                    + "		CASE\n"
                    + "			WHEN nomina_borrada = 1 THEN '00:00:00'\n"
                    + "			ELSE gj.time_origin\n"
                    + "		END\n"
                    + "	END AS hora_inicio,\n"
                    + "	CASE\n"
                    + "		WHEN TIME_TO_SEC(gj.real_time_destiny) > 0\n"
                    + "		AND nomina_borrada = 0\n"
                    + "		AND autorizado = 1 THEN gj.real_time_destiny\n"
                    + "		ELSE\n"
                    + "		CASE\n"
                    + "			WHEN nomina_borrada = 1 THEN '00:00:00'\n"
                    + "			ELSE gj.time_destiny\n"
                    + "		END\n"
                    + "	END AS hora_fin\n"
                    + "FROM\n"
                    + "	generica_jornada gj\n"
                    + "INNER JOIN empleado e ON\n"
                    + "	gj.id_empleado = e.id_empleado\n"
                    + "INNER JOIN empleado_tipo_cargo etc ON\n"
                    + "	e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                    + "WHERE\n"
                    + "	fecha BETWEEN ?1 AND ?2\n"
                    + "	AND id_param_area IN (?3)\n"
                    + "ORDER BY\n"
                    + "	gj.fecha DESC";

            Query query = em.createNativeQuery(sql, "ReporteInterventoriaMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, Arrays.toString(areas).replace("[", "").replace("]", ""));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteInterventoria> obtenerDatosInfoInterventoriaSinArea(Date desde, Date hasta) {
        try {
            String sql = "SELECT\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion as cedula,\n"
                    + "	UCASE(CONCAT(e.apellidos,' ',e.nombres)) as nombre_completo,\n"
                    + "	etc.nombre_cargo,\n"
                    + "	CASE\n"
                    + "		WHEN TIME_TO_SEC(gj.real_time_origin) > 0\n"
                    + "		AND nomina_borrada = 0\n"
                    + "		AND autorizado = 1 THEN gj.real_time_origin\n"
                    + "		ELSE\n"
                    + "		CASE\n"
                    + "			WHEN nomina_borrada = 1 THEN '00:00:00'\n"
                    + "			ELSE gj.time_origin\n"
                    + "		END\n"
                    + "	END AS hora_inicio,\n"
                    + "	CASE\n"
                    + "		WHEN TIME_TO_SEC(gj.real_time_destiny) > 0\n"
                    + "		AND nomina_borrada = 0\n"
                    + "		AND autorizado = 1 THEN gj.real_time_destiny\n"
                    + "		ELSE\n"
                    + "		CASE\n"
                    + "			WHEN nomina_borrada = 1 THEN '00:00:00'\n"
                    + "			ELSE gj.time_destiny\n"
                    + "		END\n"
                    + "	END AS hora_fin\n"
                    + "FROM\n"
                    + "	generica_jornada gj\n"
                    + "INNER JOIN empleado e ON\n"
                    + "	gj.id_empleado = e.id_empleado\n"
                    + "INNER JOIN empleado_tipo_cargo etc ON\n"
                    + "	e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                    + "WHERE\n"
                    + "	fecha BETWEEN ?1 AND ?2\n"
                    + "ORDER BY\n"
                    + "	gj.fecha DESC";

            Query query = em.createNativeQuery(sql, "ReporteInterventoriaMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasCM> obtenerDatosNominaCMConArea(Date desde, Date hasta, Integer[] areas) {
        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	e.id_empleado_cargo, \n"
                    + "	gj.fecha, \n"
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
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and id_param_area in (?3)\n"
                    + "and e.id_empleado_empleador is null\n"
                    + "group by\n"
                    + "	gj.id_empleado;";
            Query query = em.createNativeQuery(sql, "ReporteNominaCMMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, Arrays.toString(areas).replace("[", "").replace("]", ""));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ReporteHorasCM> obtenerDatosNominaCMSinArea(Date desde, Date hasta) {
        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	e.id_empleado_cargo, \n"
                    + "	gj.fecha, \n"
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
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and e.id_empleado_empleador is null\n"
                    + "group by\n"
                    + "	gj.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteNominaCMMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GenericaJornada> getByDateAllArea(Date desde, Date hasta) {
        try {
            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    generica_jornada p\n"
                    + "        RIGHT JOIN\n"
                    + "    empleado e ON e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "    (p.fecha BETWEEN ?1 AND ?2)\n"
                    + "        AND p.estado_reg = 0 \n"
                    + "ORDER BY e.identificacion ASC , p.fecha ASC;", GenericaJornada.class)
                    .setParameter(1, Util.toDate(Util.dateFormat(desde)))
                    .setParameter(2, Util.toDate(Util.dateFormat(hasta))).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ConsolidadoLiquidacion> obtenerDatosConsolidado(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
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
                    + "group by\n"
                    + "	gj.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<ConsolidadoLiquidacion> obtenerDatosCargados(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
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
                    + "	generica_jornada_inicial gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 and ?2\n"
                    + "and gj.status = 0\n"
                    + "and id_param_area = ?3\n"
                    + "group by\n"
                    + "	gj.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ConsolidadoLiquidacionDetallado> obtenerDatosConsolidadoDetallado(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END/ 3600,0) as diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END / 3600,0) as nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END/ 3600,0) as dominical_comp_nocturna_extra,\n"
                    + "	gjm.descripcion as motivo,\n"
                    + "	gj.observaciones\n"
                    + " from\n"
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "left join generica_jornada_motivo gjm on\n"
                    + "	gj.id_generica_jornada_motivo = gjm.id_generica_jornada_motivo\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and gj.id_param_area = ?3\n"
                    + "order by 1 asc,3 asc;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqDetalladoMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<ConsolidadoLiquidacionDetallado> obtenerDatosCargadosDetallado(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END/ 3600,0) as diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END / 3600,0) as nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END/ 3600,0) as extra_nocturna,\n"
                    + " ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END/ 3600,0) as dominical_comp_nocturna_extra,\n"
                    + "	gjm.descripcion as motivo,\n"
                    + "	gj.observaciones\n"
                    + " from\n"
                    + "	generica_jornada_inicial gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "left join generica_jornada_motivo gjm on\n"
                    + "	gj.id_generica_jornada_motivo = gjm.id_generica_jornada_motivo\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and gj.status = 0\n"
                    + "and gj.id_param_area = ?3\n"
                    + "order by 1 asc,3 asc;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqDetalladoMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GenericaJornada> findByDateAndLiquidado(Date desde, Date hasta, Integer idArea, Integer liquidado, int id) {
        try {
            String sql_id_emp = id == 0 ? "" : "       AND gj.id_empleado = ?5\n";
            String sql = "SELECT "
                    + "gj.* "
                    + "FROM "
                    + "generica_jornada gj "
                    + "WHERE gj.id_param_area= ?1 "
                    + "AND gj.fecha BETWEEN ?2 AND ?3 "
                    + "AND gj.liquidado = ?4" + sql_id_emp;
            Query q = em.createNativeQuery(sql, GenericaJornada.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateFormat(desde));
            q.setParameter(3, Util.dateFormat(hasta));
            q.setParameter(4, liquidado);
            q.setParameter(5, id);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaJornada> getJornadasPorRangoFechasIdAreaIdEmpleado(Date desde, Date hasta, Integer idArea, Integer idEmpleado) {
        try {
            String sql = "SELECT "
                    + "gj.* "
                    + "FROM "
                    + "generica_jornada gj "
                    + "WHERE gj.id_param_area= ?1 "
                    + "AND gj.fecha BETWEEN ?2 AND ?3 "
                    + "AND gj.id_empleado = ?4 AND gj.estado_reg=0;";
            Query q = em.createNativeQuery(sql, GenericaJornada.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateFormat(desde));
            q.setParameter(3, Util.dateFormat(hasta));
            q.setParameter(4, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int eliminarJornada(Integer idArea, Date desde, Date hasta, int idEmpleado) {
        try {
            String sql_id_emp = idEmpleado == 0 ? "" : "       AND gj.id_empleado = ?4\n";
            String sql = "DELETE FROM generica_jornada gj "
                    + "WHERE gj.fecha between ?1 AND ?2 "
                    + "AND gj.id_param_area = ?3 "
                    + "AND gj.liquidado = 0 "
                    + "AND gj.estado_reg = 0 "
                    + sql_id_emp;
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idArea);
            q.setParameter(4, idEmpleado);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int eliminarJornadaInicial(Integer idArea, Date desde, Date hasta, int idEmpleado) {
        try {
            String sql_id_emp = idEmpleado == 0 ? "" : "       AND id_empleado = ?4\n";
            String sql = "DELETE FROM generica_jornada_inicial WHERE id_generica_jornada IN (\n"
                    + "    SELECT id_generica_jornada \n"
                    + "        FROM generica_jornada \n"
                    + "        WHERE fecha BETWEEN ?1 AND ?2 \n"
                    + "                AND  id_param_area = ?3 \n"
                    + "                AND  liquidado = 0\n"
                    + "                AND estado_reg = 0\n"
                    + sql_id_emp + ")";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idArea);
            q.setParameter(4, idEmpleado);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public GenericaJornada getJornadaByIdentificacionAndArea(String codigoTM, Date fecha, Integer area) {
        try {
            return (GenericaJornada) this.em.createNativeQuery("SELECT\n"
                    + "	p.*\n"
                    + "FROM\n"
                    + "	generica_jornada p\n"
                    + "INNER JOIN empleado e ON\n"
                    + "	e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "	p.fecha = ?2\n"
                    + "	AND p.id_param_area = ?3\n"
                    + "	AND e.identificacion = ?1;", GenericaJornada.class)
                    .setParameter(1, codigoTM)
                    .setParameter(2, Util.dateFormat(fecha))
                    .setParameter(3, area)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaJornada> getUltimaJornadaMesByArea(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select * "
                    + "from generica_jornada "
                    + "where fecha between ?1 and ?2 "
                    + "and id_param_area = ?3 "
                    + "and estado_reg = 0 "
                    + "and id_generica_jornada_tipo is not null "
                    + "order by fecha desc;";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConsolidadoLiquidacionCAM> obtenerDatosConsolidadoCAM(Date desde, Date hasta) {
        try {
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	etc.nombre_cargo as cargo,\n"
                    + "	ecc.costo as salario, \n"
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
                    + "inner join empleado_tipo_cargo etc on\n"
                    + "	e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                    + "inner join empleado_cargo_costo ecc on\n"
                    + "	e.id_empleado_cargo = ecc.id_empleado_tipo_cargo\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 AND ?2\n"
                    + "	and ecc.desde <= ?1 and ecc.hasta >= ?2\n"
                    + "group by\n"
                    + "e.identificacion,\n"
                    + "nombre,\n"
                    + "cargo,\n"
                    + "salario;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiquidacionCAMMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Devuelve los datos correspondiente al informe de liquidación detallado
     * para CAM
     *
     * @param desde
     * @param hasta
     * @return datos correspondiente al informe de liquidación detallado para
     * CAM
     */
    @Override
    public List<ConsolidadoDetalladoCAM> obtenerDatosConsolidadoDetalladoCAM(Date desde, Date hasta) {
        try {
            String sql = "select\n"
                    + "	gj.fecha,\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	etc.nombre_cargo as cargo,\n"
                    + "	ecc.costo as salario, \n"
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
                    + "inner join empleado_tipo_cargo etc on\n"
                    + "	e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                    + "inner join empleado_cargo_costo ecc on\n"
                    + "	e.id_empleado_cargo = ecc.id_empleado_tipo_cargo\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 AND ?2\n"
                    + "	and ecc.desde <= ?1 and ecc.hasta >= ?2\n"
                    + "group by\n"
                    + "	gj.fecha,e.identificacion,\n"
                    + "	nombre,cargo,salario\n"
                    + "order by gj.fecha asc;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoDetalladoCAMMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(desde)));
            query.setParameter(2, Util.toDate(Util.dateFormat(hasta)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retorna datos para informe de prenómina para CAM
     *
     * @param desde
     * @param hasta
     * @param areas
     * @return Listado con los datos para informe de prenomina
     */
    @Override
    public List<PrenominaCAM> obtenerDatosInformePrenominaCAMPorArea(Date desde, Date hasta, Integer[] areas) {
        try {
            String areasSeleccionadas = Arrays.toString(areas).replace("[", "").replace("]", "").trim();
            String sql = "select\n"
                    + "	e.identificacion,\n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	e.fecha_ingreso as fecha_inicio, \n"
                    + "	etc.nombre_cargo as cargo,\n"
                    + "	UCASE(pa.area) as area, \n"
                    + "	ecc.costo as salario, \n"
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
                    + "inner join param_area pa on\n"
                    + "	pa.id_param_area = gj.id_param_area\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "inner join empleado_tipo_cargo etc on\n"
                    + "	e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                    + "inner join empleado_cargo_costo ecc on\n"
                    + "	e.id_empleado_cargo = ecc.id_empleado_tipo_cargo\n"
                    + "where\n"
                    + "	fecha BETWEEN ?1 AND ?2\n"
                    + "	and ecc.desde <= ?1 and ecc.hasta >= ?2\n"
                    + "	and e.id_empleado_empleador is not null\n"
                    + "	and gj.id_param_area in (" + areasSeleccionadas + ")\n"
                    + "group by\n"
                    + "	gj.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReportePrenominaCAMMapping");
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Date> validarDiasLiquidadosByFechasAndIdArea(Date desde, Date hasta, int idParamArea) {
        try {
            String sql = "SELECT distinct fecha FROM generica_jornada \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + "and id_param_area = ?3\n"
                    + "and liquidado = 0 and estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idParamArea);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Date> validarDiasLiquidadosByFechasAndIdAreaIndividual(Date desde, Date hasta, int idParamArea, int idEmpleado) {
        try {
            String sql = "SELECT distinct fecha FROM generica_jornada \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + "and id_param_area = ?3\n"
                    + "and id_empleado = ?4\n"
                    + "and liquidado = 0 and estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idParamArea);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConsolidadoNominaDetallado> obtenerDatosDetalladoNomina(Date desde, Date hasta, Integer idArea) {
        try {
            String sql = "select\n"
                    + "	uf.nombre as empresa,\n"
                    + "	e.identificacion,  \n"
                    + "	UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "	gj.fecha,\n"
                    + "	gj.time_origin,\n"
                    + "	gj.time_destiny,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END/ 3600,0) as diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END / 3600,0) as nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END/ 3600,0) as extra_diurna,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END/ 3600,0) as festivo_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END/ 3600,0) as festivo_nocturno ,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END/ 3600,0) as festivo_extra_diurno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END/ 3600,0) as festivo_extra_nocturno,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END/ 3600,0) as extra_nocturna,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END/ 3600,0) as dominical_comp_diurnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "	ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + " from\n"
                    + "	generica_jornada gj\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = gj.id_empleado\n"
                    + "    inner join gop_unidad_funcional uf on\n"
                    + "	uf.id_gop_unidad_funcional = e.id_gop_unidad_funcional\n"
                    + "where\n"
                    + "	gj.fecha BETWEEN ?1 and ?2\n"
                    + "and gj.id_param_area = ?3\n"
                    + "order by gj.id_empleado, gj.fecha;";

            Query query = em.createNativeQuery(sql, "ReporteDetalladoNominaMapping");
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getData(String data) {
        return data == null ? "00:00:00" : data;
    }

    @Override
    public void updatePrgSerconFromList(List<GenericaJornadaLiqUtil> sercones, int opc) {
        String consulta = "";
        String parte = "";
        Query q = null;
        if (opc == 1) {
            parte = " AND (ps.autorizado <> 1 OR ps.autorizado is NULL);";
        }
        try {
            for (GenericaJornadaLiqUtil s : sercones) {
                consulta = "UPDATE generica_jornada ps SET "
                        + "ps.diurnas = '" + getData(s.getDiurnas()) + "', "
                        + "ps.nocturnas = '" + getData(s.getNocturnas()) + "', "
                        + "ps.extra_diurna = '" + getData(s.getExtraDiurna()) + "', "
                        + "ps.extra_nocturna = '" + getData(s.getExtraNocturna()) + "', "
                        + "ps.festivo_diurno = '" + getData(s.getFestivoDiurno()) + "', "
                        + "ps.festivo_nocturno = '" + getData(s.getFestivoNocturno()) + "', "
                        + "ps.festivo_extra_diurno = '" + getData(s.getFestivoExtraDiurno()) + "', "
                        + "ps.festivo_extra_nocturno = '" + getData(s.getFestivoExtraNocturno()) + "', "
                        + "ps.production_time = '" + getData(s.getProductionTime()) + "', "
                        + "ps.dominical_comp_diurnas = '" + getData(s.getDominicalCompDiurnas()) + "', "
                        + "ps.dominical_comp_nocturnas = '" + getData(s.getDominicalCompNocturnas()) + "', "
                        + "ps.cargada = 1, "
                        + "ps.compensatorio = '" + getData(s.getCompensatorio()) + "' "
                        + "WHERE ps.fecha = '" + Util.dateFormat(s.getFecha()) + "' "
                        + "AND ps.id_empleado = " + s.getIdEmpleado().getIdEmpleado() + " "
                        + "AND ps.estado_reg = 0 " + parte;
                q = this.em.createNativeQuery(consulta);
                q.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public GenericaJornada validarEmplSinJornadaByParamAreaFechaEmpleado(int idEmpleado, Date fecha, int idParamArea) {
        try {
            String sql_param_area = idParamArea == 0 ? ""
                    : " and ps.id_param_area = ?3\n";

            List<GenericaJornada> resultados = this.em.createNativeQuery("SELECT \n"
                    + "    ps.*\n"
                    + "FROM\n"
                    + "    generica_jornada ps\n"
                    + "WHERE\n"
                    + "    ps.id_empleado = ?1\n"
                    + sql_param_area
                    + "        AND ps.fecha = ?2\n"
                    + "        AND ps.estado_reg = 0", GenericaJornada.class)
                    .setParameter(1, idEmpleado)
                    .setParameter(2, Util.dateFormat(fecha))
                    .setParameter(3, idParamArea)
                    .getResultList();

            if (resultados.isEmpty()) {
                return null; // Manejar el caso donde no se encuentra el resultado
            } else {
                return resultados.get(0); // Retornar el primer (y único) resultado
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<GenericaJornada> findBiometricoNovedades(int idArea, Date desde, Date hasta, int idGopUnidadFuncional, int gestiona) {
        //estado_marcacion 0-No procesado, 1-Retardo, 2-Abandono, 3-Ausencia, 4-Salida Temprana, 5-Extra NO autorizada, 6- NORMAL
        try {
            String sql_gestion = gestiona == 0 ? " AND gj.marcacion_gestionada = 0": "";
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?4 \n";
            String sql_date = desde == null ? "" : hasta == null ? "" : " AND gj.fecha BETWEEN ?2 AND ?3 \n";
            String sql = "SELECT gj.* FROM generica_jornada gj WHERE gj.estado_marcacion in (1,2,3,5) AND gj.liquidado = 0 AND gj.id_param_area = ?1\n"
                    + sql_date + sql_unida_func + sql_gestion + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idArea);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            query.setParameter(4, idGopUnidadFuncional);
            query.setParameter(5, gestiona);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<GenericaJornada> findBiometricoNovedadesGestionadas(int idParamArea, Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?4\n";
            String sql_date = desde == null ? "" : hasta == null ? "" : " AND gj.fecha BETWEEN ?2 AND ?3\n";
            String sql_area = idParamArea > 0 ? " AND gj.id_param_area = ?1\n" : ""; 
            String sql = "SELECT gj.* FROM generica_jornada gj WHERE gj.marcacion_gestionada = 1\n"
                    + sql_date + sql_unida_func + sql_area + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idParamArea);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            query.setParameter(4, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    //se traen las novedades que no han sido gestionadas y las que están pendientes por autorizar
    //en las novedades que se gestionan son las de estado 1-Retardo, 2-Abandono, 3-Ausencia, 5-Extra NO autorizada
    public List<GenericaJornada> findBiometricoNovedadesPorGestionar(int idParamArea, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?2\n";
            String sql_area = idParamArea > 0 ? " AND gj.id_param_area = ?1\n" : ""; 
            String sql = "SELECT gj.* FROM generica_jornada gj WHERE gj.estado_marcacion in (1,2,3,5) AND gj.liquidado = 0 AND gj.marcacion_autorizada = 0\n"
                    + sql_unida_func + sql_area + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idParamArea);
            query.setParameter(2, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<GenericaJornada> findBiometricoOK(int idParamArea, Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?4\n";
            String sql_area = idParamArea > 0 ? " AND gj.id_param_area = ?1\n" : ""; 
            String sql = "SELECT gj.* FROM generica_jornada gj WHERE gj.fecha BETWEEN ?2 AND ?3 AND gj.bio_entrada != 'null' AND (gj.estado_marcacion = 0 OR gj.estado_marcacion = 4)\n"
                    + sql_unida_func + sql_area + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idParamArea);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            query.setParameter(4, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<GenericaJornada> findHistoricoMarcaciones(int idParamArea, Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?4\n";
            String sql_area = idParamArea > 0 ? " AND gj.id_param_area = ?1\n" : ""; 
            String sql = "SELECT gj.* FROM generica_jornada gj WHERE ((gj.bio_entrada != 'null' OR gj.bio_salida != 'null') OR estado_marcacion != 0) AND (gj.fecha BETWEEN ?2 AND ?3) AND gj.estado_reg=0\n" 
                    + sql_unida_func + sql_area + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idParamArea);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            query.setParameter(4, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<GenericaJornadaInicial> findJornadasCargadas(int idParamArea, Date desde, Date hasta) {
        try {
            String sql_area = idParamArea > 0 ? " AND gj.id_param_area = ?1\n" : "";
            String sql = "SELECT gj.* FROM generica_jornada_inicial gj WHERE (gj.fecha BETWEEN ?2 AND ?3) AND gj.status = 0 AND gj.estado_reg=0\n"
                    + sql_area + ";";
            Query query = em.createNativeQuery(sql, GenericaJornada.class);
            query.setParameter(1, idParamArea);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
