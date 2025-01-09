/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.aja.jornada.model.PrgSerconLiqUtil;
import com.movilidad.dto.EmpleadoDescansoDTO;
import com.movilidad.dto.EntradaSalidaJornadaDTO;
import com.movilidad.dto.HoraPrgEjecDTO;
import com.movilidad.dto.PrgSerconDTO;
import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaJornadaInicial;
import com.movilidad.model.PrgSercon;
import com.movilidad.model.PrgSerconInicial;
import com.movilidad.util.beans.ConsolidadoLiquidacion;
import com.movilidad.util.beans.ConsolidadoLiquidacionDetallado;
import com.movilidad.util.beans.ConsolidadoLiquidacionGMO;
import com.movilidad.util.beans.ConsolidadoNominaDetallado;
import com.movilidad.util.beans.InformeAmplitud;
import com.movilidad.util.beans.LiquidacionSercon;
import com.movilidad.utils.UtilJornada;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import liquidadorjornada.Jornada;

/**
 *
 * @author solucionesit
 */
@Stateless
public class PrgSerconFacade extends AbstractFacade<PrgSercon> implements PrgSerconFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSerconFacade() {
        super(PrgSercon.class);
    }

    @Override
    public List<PrgSercon> findByFechaAndIdGopUnidadFunc(Date date, Integer idGopUnidadFuncional) {
        try {

            String sql = "SELECT * FROM prg_sercon WHERE fecha = ?1 ";

            if (idGopUnidadFuncional != null) {
                sql += "AND id_gop_unidad_funcional =" + idGopUnidadFuncional + "\n";
            }

            sql += "and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PrgSercon.class);
            query.setParameter(1, Util.dateFormat(date));

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update(LiquidacionSercon l) {
        try {
            String consulta = "UPDATE prg_sercon SET "
                    + "prg_sercon.diurnas = '" + l.getDiurnas() + "', "
                    + "prg_sercon.nocturnas = '" + l.getNocturnas() + "', "
                    + "prg_sercon.extra_diurna = '" + l.getExtraDiurna() + "', "
                    + "prg_sercon.extra_nocturna = '" + l.getExtraNocturna() + "', "
                    + "prg_sercon.festivo_diurno = '" + l.getFestivoDiurno() + "', "
                    + "prg_sercon.festivo_nocturno = '" + l.getFestivoNocturno() + "', "
                    + "prg_sercon.festivo_extra_diurno = '" + l.getFestivoExtraDiurno() + "', "
                    + "prg_sercon.festivo_extra_nocturno = '" + l.getFestivoExtraNocturno() + "', "
                    + "prg_sercon.compensatorio = '" + l.getCompensatorio() + "' "
                    + "WHERE prg_sercon.fecha = '" + l.getFecha() + "' "
                    + "AND prg_sercon.id_empleado = (SELECT id_empleado FROM empleado WHERE empleado.codigo_tm = '" + l.getCodigo() + "' AND empleado.estado_reg = 0) "
                    + "AND prg_sercon.estado_reg = 0;";
            Query q = this.em.createNativeQuery(consulta);
            q.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update DaoPrgSerconImpl");
        }
    }

    @Override
    public void updateVistoInPrgSercon(int idPrgSercon) {
        try {
            String consulta = "UPDATE prg_sercon ps \n"
                    + "SET \n"
                    + "    ps.visto = 1\n"
                    + "WHERE\n"
                    + "    ps.id_prg_sercon=?1;";
            Query q = this.em.createNativeQuery(consulta);
            q.setParameter(1, idPrgSercon);
            q.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update DaoPrgSerconImpl");
        }
    }

    @Override
    public void updateSercon(PrgSercon s) {
        try {
            String consulta = "UPDATE prg_sercon ps \n"
                    + "SET \n"
                    + "    ps.diurnas = ?1,\n"
                    + "    ps.nocturnas = ?2,\n"
                    + "    ps.extra_diurna = ?3,\n"
                    + "    ps.extra_nocturna = ?4,\n"
                    + "    ps.festivo_diurno = ?5,\n"
                    + "    ps.festivo_nocturno = ?6,\n"
                    + "    ps.festivo_extra_diurno = ?7,\n"
                    + "    ps.festivo_extra_nocturno = ?8,\n"
                    + "    ps.compensatorio = ?9\n"
                    + "WHERE\n"
                    + "    ps.fecha = ?10 AND ps.id_empleado = ?11\n"
                    + "        AND ps.estado_reg = 0;";
            Query q = this.em.createNativeQuery(consulta);
            q.setParameter(1, s.getDiurnas());
            q.setParameter(2, s.getNocturnas());
            q.setParameter(3, s.getExtraDiurna());
            q.setParameter(4, s.getExtraNocturna());
            q.setParameter(5, s.getFestivoDiurno());
            q.setParameter(6, s.getFestivoNocturno());
            q.setParameter(7, s.getFestivoExtraDiurno());
            q.setParameter(8, s.getFestivoExtraNocturno());
            q.setParameter(9, s.getCompensatorio());
            q.setParameter(10, Util.dateFormat(s.getFecha()));
            q.setParameter(11, s.getIdEmpleado().getIdEmpleado());
            q.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update DaoPrgSerconImpl");
        }

    }

    @Override
    public void updateSerconFromList(List<PrgSercon> sercones, int opc) {
        String consulta = "";
        String parte = "";
        Query q = null;
        if (opc == 1) {
            parte = " AND (ps.autorizado <> 1 OR ps.autorizado is NULL);";
        }
        try {
            for (PrgSercon s : sercones) {
                consulta = "UPDATE prg_sercon ps SET "
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
//                System.out.println("consulta-->" + consulta);
                q = this.em.createNativeQuery(consulta);
                q.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en update DaoPrgSerconImpl");
        }

    }

    private String getData(String data) {
        return data == null ? "00:00:00" : data;
    }

    @Override
    public long countByFechas(Date fromDate, Date toDate, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT \n"
                    + "    COUNT(p.id_prg_sercon)\n"
                    + "FROM\n"
                    + "    prg_sercon p\n"
                    + "WHERE\n"
                    + "    p.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND p.cargada = 1\n"
                    + sql_unida_func;
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(fromDate));
            q.setParameter(2, Util.dateFormat(toDate));
            q.setParameter(3, idGopUnidadFuncional);
            return (long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void nominaBorrada(int idPrgSercon, int valor, String username) {
        try {
            this.em.createNativeQuery("update prg_sercon set nomina_borrada =?1, modificado=?3, username=?4 "
                    + "where id_prg_sercon=?2").setParameter(1, valor)
                    .setParameter(2, idPrgSercon).setParameter(3, MovilidadUtil.fechaHoy()).setParameter(4, username).executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
        }
    }

    @Override
    public List<PrgSercon> getPrgSerconByDateAndUnidadFunc(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and p.id_gop_unidad_funcional = ?3\n";

        try {
            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_sercon p\n"
                    + "        RIGHT JOIN\n"
                    + "    empleado e ON e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "    (p.fecha BETWEEN ?1 AND ?2)\n"
                    + sql_unida_func
                    + "        AND p.estado_reg = 0\n"
                    + "ORDER BY e.codigo_tm ASC , p.fecha ASC;", PrgSercon.class)
                    .setParameter(1, Util.toDate(Util.dateFormat(desde)))
                    .setParameter(2, Util.toDate(Util.dateFormat(hasta)))
                    .setParameter(3, idGopUnidadFuncional)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<InformeAmplitud> getAmplitudes(Date desde, Date hasta, String horas, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and prg_tc.id_gop_unidad_funcional = ?4\n";

            return this.em.createNativeQuery("SELECT\n"
                    + "prg_tc.fecha,\n"
                    + "empleado.codigo_tm,\n"
                    + "empleado.identificacion,\n"
                    + "empleado.nombres,\n"
                    + "empleado.apellidos,\n"
                    + "prg_tc.amplitude,\n"
                    + "count(vehiculo_tipo.nombre_tipo_vehiculo) as veces\n"
                    + "FROM\n"
                    + "prg_tc\n"
                    + "INNER JOIN empleado ON prg_tc.id_empleado = empleado.id_empleado\n"
                    + "INNER JOIN vehiculo_tipo ON prg_tc.id_vehiculo_tipo = vehiculo_tipo.id_vehiculo_tipo\n"
                    + "where prg_tc.amplitude>=?3\n"
                    + "and prg_tc.fecha between ?1 and ?2\n"
                    + sql_unida_func
                    + "GROUP BY 1,2,3,4,5,6", "InformeAmplitudMapping")
                    .setParameter(1, Util.toDate(Util.dateFormat(desde)))
                    .setParameter(2, Util.toDate(Util.dateFormat(hasta)))
                    .setParameter(3, horas)
                    .setParameter(4, idGopUnidadFuncional).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void liquidarPorRangoFecha(Date fromDate, Date toDate, String userName, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?7\n";

            this.em.createNativeQuery("UPDATE prg_sercon "
                    + "SET "
                    + "    liquidado = 1, "
                    + "    user_liquida = ?3, "
                    + "    fecha_liquida = ?4, "
                    + "    modificado = ?5, "
                    + "    username = ?6 "
                    + "WHERE "
                    + "    fecha BETWEEN ?1 AND ?2 "
                    + sql_unida_func
                    + "AND liquidado <> 1 ;").setParameter(1, Util.dateFormat(fromDate))
                    .setParameter(2, Util.dateFormat(toDate))
                    .setParameter(3, userName)
                    .setParameter(4, new Date())
                    .setParameter(5, new Date())
                    .setParameter(6, userName)
                    .setParameter(7, idGopUnidadFuncional)
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
        }
    }

    @Override
    public long validarPeriodoLiquidado(Date fromDate, Date toDate) {
        try {
            return (long) this.em.createNativeQuery("SELECT \n"
                    + "    COUNT(ps.id_prg_sercon)\n"
                    + "FROM\n"
                    + "    prg_sercon ps\n"
                    + "WHERE\n"
                    + "    ps.liquidado = 1\n"
                    + "        AND ps.fecha BETWEEN ?1 AND ?2;").setParameter(1, Util.dateFormat(fromDate))
                    .setParameter(2, Util.dateFormat(toDate)).getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
            return 0;
        }
    }

    @Override
    public long validarPeriodoLiquidadoEmpleado(Date fromDate, Date toDate, int idEmpleado) {
        try {
            return (long) this.em.createNativeQuery("SELECT \n"
                    + "    COUNT(ps.id_prg_sercon)\n"
                    + "FROM\n"
                    + "    prg_sercon ps\n"
                    + "WHERE\n"
                    + "    ps.liquidado = 1\n"
                    + "        AND ps.fecha BETWEEN ?1 AND ?2 AND ps.id_empleado=?3;").setParameter(1, Util.dateFormat(fromDate))
                    .setParameter(2, Util.dateFormat(toDate)).setParameter(3, idEmpleado).getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
            return 0;
        }
    }

    @Override
    public int liquidarPorId(int id, String userName) {
        try {
            return this.em.createNativeQuery("UPDATE prg_sercon "
                    + "SET "
                    + "    liquidado = 1, "
                    + "    user_liquida = ?3, "
                    + "    fecha_liquida = ?4, "
                    + "    modificado = ?4, "
                    + "    username = ?3 "
                    + "WHERE "
                    + "    id_prg_sercon = ?2;").setParameter(2, id)
                    .setParameter(3, userName)
                    .setParameter(4, MovilidadUtil.fechaCompletaHoy())
                    .executeUpdate();
        } catch (Exception e) {
            System.out.println("Error en update nomina borrada");
            return 0;
        }
    }

    @Override
    public PrgSercon validarEmplSinJornadaByUnindadFuncional(int idEmpleado, Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? ""
                    : " and ps.id_gop_unidad_funcional = ?3\n";
            List<PrgSercon> results = this.em.createNativeQuery("SELECT \n"
                    + "    ps.*\n"
                    + "FROM\n"
                    + "    prg_sercon ps\n"
                    + "WHERE\n"
                    + "    ps.id_empleado = ?1\n"
                    + sql_unida_func
                    + "        AND ps.fecha = ?2\n"
                    + "        AND ps.estado_reg = 0", PrgSercon.class)
                    .setParameter(1, idEmpleado)
                    .setParameter(2, Util.dateFormat(fecha))
                    .setParameter(3, idGopUnidadFuncional)
                    .getResultList();

            if (results.isEmpty()) {
                return null;
            } else {
                return results.get(0); // Como se espera un solo resultado, se obtiene el primero de la lista
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PrgSercon getPrgSerconByCodigoTMAndUnidadFuncional(String codigoTM, Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and p.id_gop_unidad_funcional = ?3\n";
            return (PrgSercon) this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_sercon p\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON e.id_empleado = p.id_empleado\n"
                    + "WHERE\n"
                    + "    p.fecha = ?2\n"
                    + sql_unida_func
                    + "        AND e.codigo_tm = ?1;", PrgSercon.class)
                    .setParameter(1, codigoTM).setParameter(2, Util.dateFormat(fecha)).setParameter(3, idGopUnidadFuncional).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void cambioEmpleado(PrgSercon prgSercon1, PrgSercon prgSercon2) {
        Query q = em.createNativeQuery("UPDATE prg_sercon p "
                + "SET "
                + "p.observaciones = ?1, "
                + "p.prg_modificada = 1, "
                + "p.autorizado = 1, "
                + "p.user_autorizado = ?2, "
                + "p.user_genera = ?3, "
                + "p.fecha_genera = ?4, "
                + "p.fecha_autoriza = ?5, "
                + "p.id_empleado = ?6, "
                + "p.id_prg_sercon_motivo = ?7, "
                + "p.modificado = ?10, "
                + "p.username = ?11 "
                + "WHERE p.id_prg_sercon=?8");
        q.setParameter(1, prgSercon1.getObservaciones());
        q.setParameter(2, prgSercon1.getUserAutorizado());
        q.setParameter(3, prgSercon1.getUserGenera());
        q.setParameter(4, prgSercon1.getFechaGenera());
        q.setParameter(5, prgSercon1.getFechaAutoriza());
        q.setParameter(6, prgSercon1.getIdEmpleado().getIdEmpleado());
        q.setParameter(7, prgSercon1.getIdPrgSerconMotivo().getIdPrgSerconMotivo());
        q.setParameter(8, prgSercon1.getIdPrgSercon());
        q.setParameter(10, new Date());
        q.setParameter(11, prgSercon1.getUsername());
        q.executeUpdate();

        Query qq = em.createNativeQuery("UPDATE prg_sercon p "
                + "SET "
                + "p.observaciones = ?1, "
                + "p.prg_modificada = 1, "
                + "p.autorizado = 1, "
                + "p.user_autorizado = ?2, "
                + "p.user_genera = ?3, "
                + "p.fecha_genera = ?4, "
                + "p.fecha_autoriza = ?5, "
                + "p.id_empleado = ?6, "
                + "p.id_prg_sercon_motivo = ?7, "
                + "p.modificado = ?10, "
                + "p.username = ?11 "
                + "WHERE p.id_prg_sercon=?8");
        qq.setParameter(1, prgSercon2.getObservaciones());
        qq.setParameter(2, prgSercon2.getUserAutorizado());
        qq.setParameter(3, prgSercon2.getUserGenera());
        qq.setParameter(4, prgSercon2.getFechaGenera());
        qq.setParameter(5, prgSercon2.getFechaAutoriza());
        qq.setParameter(6, prgSercon2.getIdEmpleado().getIdEmpleado());
        qq.setParameter(7, prgSercon2.getIdPrgSerconMotivo().getIdPrgSerconMotivo());
        qq.setParameter(8, prgSercon2.getIdPrgSercon());
        qq.setParameter(10, new Date());
        qq.setParameter(11, prgSercon2.getUsername());

        qq.executeUpdate();
    }

    @Override
    public void borradoMasivo(int idEmpleado, int idMotivo, Date desde, Date hasta, String observacion, String username, int valorBorrado) {
        Query q = em.createNativeQuery("UPDATE prg_sercon p "
                + "SET "
                + "p.observaciones = ?1, "
                + "p.prg_modificada = 1, "
                + "p.autorizado = 1, "
                + "p.user_autorizado = ?2, "
                + "p.user_genera = ?3, "
                + "p.fecha_genera = ?4, "
                + "p.fecha_autoriza = ?5, "
                + "p.nomina_borrada = ?6, "
                + "p.id_prg_sercon_motivo = ?7, "
                + "p.modificado = ?10, "
                + "p.username = ?11 "
                + "WHERE p.id_empleado=?8 and p.fecha between ?12 and ?13 and p.liquidado<>1");
        q.setParameter(1, observacion);
        q.setParameter(2, username);
        q.setParameter(3, username);
        q.setParameter(4, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(5, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(6, valorBorrado);
        q.setParameter(7, idMotivo);
        q.setParameter(8, idEmpleado);
        q.setParameter(10, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(11, username);
        q.setParameter(12, Util.dateFormat(desde));
        q.setParameter(13, Util.dateFormat(hasta));
        q.executeUpdate();
    }

    @Override
    public int removeByDate(Date d) {
        try {
            String sql = "delete from prg_sercon where fecha= ?1";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int cambiarNomina(Integer idEmpleado, Integer idPrgSercon) {
        try {
            String sql = "update\n"
                    + "	prg_sercon\n"
                    + "set\n"
                    + "	id_empleado = ?1\n"
                    + "where\n"
                    + "	id_prg_sercon =?2;";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idEmpleado);
            query.setParameter(2, idPrgSercon);

            return query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<PrgSercon> getByDate(Date desde, Date hasta) {
        try {
            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_sercon p\n"
                    + "WHERE\n"
                    + "    (p.fecha BETWEEN ?1 AND ?2)\n"
                    + "        AND p.estado_reg = 0", PrgSercon.class)
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(2, Util.dateFormat(hasta)).getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgSercon> getByDateSinLiquidar(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?3\n";

            return this.em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    prg_sercon p\n"
                    + "WHERE\n"
                    + "    (p.fecha BETWEEN ?1 AND ?2)\n"
                    + sql_unida_func
                    + "        AND p.estado_reg = 0 AND p.liquidado <> 1 ; ", PrgSercon.class)
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(2, Util.dateFormat(hasta))
                    .setParameter(3, idGopUnidadFuncional)
                    .getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Permite consultar las proximas presentaciones de los operadores segun la
     * programación y por rango de horas parametrizadas.
     *
     * @param fecha
     * @param tiempoOrgura
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public List<PrgSerconDTO> findSerconSinPresentacionByUnidadFunc(Date fecha, int tiempoOrgura, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";
        Query q = this.em.createNativeQuery("SELECT \n"
                + "  ps.id_prg_sercon, e.codigo_tm, ps.time_origin, ps.time_destiny\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "        INNER JOIN\n"
                + "    empleado e ON ps.id_empleado = e.id_empleado\n"
                + "WHERE\n"
                + "    ps.visto IS NULL\n"
                + sql_unida_func
                + "        AND ps.id_from_stop IS NOT NULL\n"
                + "        AND ps.id_to_stop IS NOT NULL\n"
                + "        AND ps.id_my_app_sercon_confirm IS NULL\n"
                + "        AND (TIME_TO_SEC(ps.time_origin) BETWEEN (TIME_TO_SEC(NOW()) - ?2) AND TIME_TO_SEC(NOW())\n"
                + "        AND ps.real_time_origin IS NULL\n"
                + "        AND ps.fecha = ?1)\n"
                + "        OR (TIME_TO_SEC(ps.real_time_origin) BETWEEN (TIME_TO_SEC(NOW()) - ?2) AND TIME_TO_SEC(NOW())\n"
                + "        AND ps.real_time_origin IS NOT NULL\n"
                + "        AND ps.fecha = ?1)\n"
                + "        AND ps.fecha = ?1\n"
                + "ORDER BY TIME(ps.time_origin) ASC;", "prgSerconDTOMapping");
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, tiempoOrgura);
        q.setParameter(3, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgSercon> findAllSerconSinPresentacionByUnidadFuncional(Date fecha,
            int tiempoOrgura, int idGopUnidadFuncional, int idNovedadTipo) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?2\n";
        String sql_unida_func_nov = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?2\n";
        Query q = this.em.createNativeQuery("SELECT \n"
                + "    ps.*\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "WHERE\n"
                + "    ps.fecha = ?1\n"
                + "        AND ps.id_from_stop IS NOT NULL\n"
                + "        AND ps.id_to_stop IS NOT NULL\n"
                + "        AND ps.id_my_app_sercon_confirm IS NULL\n"
                + "        AND (((TIME_TO_SEC(ps.time_origin) - ?3) <= TIME_TO_SEC(NOW())\n"
                + "        AND ps.real_time_origin IS NULL\n"
                + "        AND ps.fecha = ?1)\n"
                + "        OR (((TIME_TO_SEC(ps.real_time_origin) - ?3) <= TIME_TO_SEC(NOW()))\n"
                + "        AND ps.real_time_origin <> '00:00:00')\n"
                + "        AND ps.fecha = ?1)\n"
                + "        AND ps.id_empleado NOT IN (SELECT \n"
                + "            n.id_empleado\n"
                + "        FROM\n"
                + "            novedad n\n"
                + "        WHERE\n"
                + "            n.id_novedad_tipo = ?4\n"
                + "                AND ?1 BETWEEN n.desde AND n.hasta\n"
                + sql_unida_func_nov
                + "                AND n.id_empleado IS NOT NULL)\n"
                + sql_unida_func
                + "ORDER BY TIME(ps.time_origin) ASC;", PrgSercon.class);
        q.setParameter(1, Util.dateFormat(fecha));
        q.setParameter(2, idGopUnidadFuncional);
        q.setParameter(3, tiempoOrgura);
        q.setParameter(4, idNovedadTipo);
        return q.getResultList();
    }

    @Override
    public List<PrgSercon> findAllRangoFechasWithConfirmation(Date desde, Date hasta, int idGopUf) {
        String uf = idGopUf == 0 ? " " : " AND ps.id_gop_unidad_funcional = " + idGopUf + " ";
        String sql = "SELECT \n"
                + "    ps.*\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "WHERE\n"
                + "    ps.fecha BETWEEN ?1 AND ?2\n"
                + uf
                + "        AND ps.estado_reg = 0\n"
                + "ORDER BY ps.time_origin ASC;";
        return this.em.createNativeQuery(sql, PrgSercon.class)
                .setParameter(1, Util.dateFormat(desde))
                .setParameter(2, Util.dateFormat(hasta))
                .getResultList();
    }

    @Override
    public List<ConsolidadoLiquidacion> obtenerDatosConsolidado(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

        try {
            String sql = "select\n"
                    + "	ps.fecha,\n"
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
                    + "	prg_sercon ps\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = ps.id_empleado\n"
                    + "where\n"
                    + "	ps.fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "group by\n"
                    + "ps.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqMapping");
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
    public List<ConsolidadoLiquidacion> obtenerDatosCargados(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

        try {
            String sql = "select\n"
                    + "	ps.fecha,\n"
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
                    + "	prg_sercon_inicial ps\n"
                    + "inner join empleado e on\n"
                    + "	e.id_empleado = ps.id_empleado\n"
                    + "where\n"
                    + "	ps.fecha BETWEEN ?1 and ?2\n"
                    + " and ps.status = 0\n"
                    + sql_unida_func
                    + "group by\n"
                    + "ps.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqMapping");
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
    public List<ConsolidadoLiquidacionDetallado> obtenerDatosConsolidadoDetallado(Date desde, Date hasta,
            int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT \n"
                    + "    ps.fecha,\n"
                    + "    e.identificacion,\n"
                    + "    UCASE(CONCAT(e.nombres, ' ', e.apellidos)) AS nombre,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(diurnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS diurnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(nocturnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS nocturnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_diurna)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS extra_diurna,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_diurno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_diurno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_nocturno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_nocturno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_diurno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_extra_diurno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_nocturno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_extra_nocturno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_nocturna)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS extra_nocturna,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_diurnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_nocturnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurna_extra)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_diurna_extra,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturna_extra)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_nocturna_extra,\n"
                    + "    psm.descripcion AS motivo,\n"
                    + "    ps.observaciones\n"
                    + "FROM\n"
                    + "    prg_sercon ps\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON e.id_empleado = ps.id_empleado\n"
                    + "        LEFT JOIN\n"
                    + "    prg_sercon_motivo psm ON ps.id_prg_sercon_motivo = psm.id_prg_sercon_motivo\n"
                    + "WHERE\n"
                    + "    ps.fecha BETWEEN ?1 AND ?2\n"
                    + sql_unida_func
                    + "ORDER BY 1 ASC , 3 ASC;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqDetalladoMapping");
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
    public List<ConsolidadoLiquidacionDetallado> obtenerDatosCargadosDetallado(Date desde, Date hasta,
            int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

            String sql = "SELECT \n"
                    + "    ps.fecha,\n"
                    + "    e.identificacion,\n"
                    + "    UCASE(CONCAT(e.nombres, ' ', e.apellidos)) AS nombre,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(diurnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS diurnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(nocturnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS nocturnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_diurna)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS extra_diurna,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_diurno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_diurno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_nocturno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_nocturno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_diurno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_extra_diurno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_nocturno)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS festivo_extra_nocturno,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_nocturna)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS extra_nocturna,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_diurnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturnas)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_nocturnas,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurna_extra)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_diurna_extra,\n"
                    + "    IFNULL(CASE\n"
                    + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturna_extra)\n"
                    + "                ELSE 0\n"
                    + "            END / 3600,\n"
                    + "            0) AS dominical_comp_nocturna_extra,\n"
                    + "    psm.descripcion AS motivo,\n"
                    + "    ps.observaciones\n"
                    + "FROM\n"
                    + "    prg_sercon_inicial ps\n"
                    + "        INNER JOIN\n"
                    + "    empleado e ON e.id_empleado = ps.id_empleado\n"
                    + "        LEFT JOIN\n"
                    + "    prg_sercon_motivo psm ON ps.id_prg_sercon_motivo = psm.id_prg_sercon_motivo\n"
                    + "WHERE\n"
                    + "    ps.fecha BETWEEN ?1 AND ?2\n"
                    + "    and ps.status = 0\n"
                    + sql_unida_func
                    + "ORDER BY 1 ASC , 3 ASC;";

            Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqDetalladoMapping");
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
    public PrgSercon getByIdEmpleadoAndFecha(Integer idEmpleado, Date fecha) {
        try {
            String sql = "SELECT "
                    + "    * "
                    + "FROM "
                    + "    prg_sercon "
                    + "WHERE "
                    + "    fecha = ?2 "
                    + "        AND id_empleado = ?1 "
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, PrgSercon.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fecha));
            return (PrgSercon) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Date> validarDiasLiquidadosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT distinct fecha FROM prg_sercon \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "and liquidado = 0 and estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Date totalHorasExtrasByRangoFechaAndIdEmpleado(Integer idEmpleado, Date fecha, Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    SEC_TO_TIME(SUM(CASE\n"
                    + "                WHEN nomina_borrada = 1 THEN TIME_TO_SEC('00:00:00')\n"
                    + "                ELSE CASE\n"
                    + "                    WHEN\n"
                    + "                        ((prg_modificada IS NULL\n"
                    + "                            OR prg_modificada = 0)\n"
                    + "                            OR (prg_modificada = 1 AND autorizado = 0))\n"
                    + "                    THEN\n"
                    + "                        (IFNULL(TIME_TO_SEC(extra_diurna), 0) + IFNULL(TIME_TO_SEC(extra_nocturna), 0) + IFNULL(TIME_TO_SEC(festivo_extra_diurno), 0) + IFNULL(TIME_TO_SEC(festivo_extra_nocturno), 0))\n"
                    + "                    ELSE CASE\n"
                    + "                        WHEN\n"
                    + "                            prg_modificada IS NOT NULL\n"
                    + "                                AND (prg_modificada = 1 AND autorizado <> 0)\n"
                    + "                                AND ((IFNULL(TIME_TO_SEC(real_time_destiny), 0) - IFNULL(TIME_TO_SEC(real_time_origin), 0)) + (IFNULL(TIME_TO_SEC(real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(real_hini_turno2), 0)) + (IFNULL(TIME_TO_SEC(real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(real_hini_turno3), 0))) > TIME_TO_SEC(?3)\n"
                    + "                        THEN\n"
                    + "                            ((IFNULL(TIME_TO_SEC(real_time_destiny), 0) - IFNULL(TIME_TO_SEC(real_time_origin), 0)) + (IFNULL(TIME_TO_SEC(real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(real_hini_turno2), 0)) + (IFNULL(TIME_TO_SEC(real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(real_hini_turno3), 0))) - TIME_TO_SEC(?3)\n"
                    + "                        ELSE TIME_TO_SEC('00:00:00')\n"
                    + "                    END\n"
                    + "                END\n"
                    + "            END)) AS extra\n"
                    + "FROM\n"
                    + "    prg_sercon\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN DATE(?4) AND DATE(?5)\n"
                    + "        AND fecha <> DATE(?2)\n"
                    + "        AND id_empleado = ?1\n"
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, UtilJornada.getTotalHrsLaborales());
            q.setParameter(4, Util.dateFormat(desde));
            q.setParameter(5, Util.dateFormat(hasta));
            return (Date) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void ajustarJornadaCero(Integer idEmpleado, Integer idMotivo, Date desde, Date hasta, String observacion, String username) {

        String sql_nomina_borrada = "UPDATE prg_sercon \n"
                + "SET \n"
                + "    nomina_borrada = 1,\n"
                + "    modificado = ?3,\n"
                + "    username = ?2\n"
                + "WHERE\n"
                + "    id_empleado = ?5\n"
                + "        AND fecha BETWEEN ?6 AND ?7\n"
                + "        AND liquidado <> 1";

        String sql_modifica_borrar_jornada = "UPDATE prg_sercon p \n"
                + "SET \n"
                + "    real_time_origin = ?8,\n"
                + "    real_time_destiny = ?8,\n"
                + "    real_hini_turno2 = ?8,\n"
                + "    real_hfin_turno2 = ?8,\n"
                + "    real_hini_turno3 = ?8,\n"
                + "    real_hfin_turno3 = ?8,\n"
                + "    real_hfin_turno3 = ?8,\n"
                + "    p.observaciones = ?1,\n"
                + "    p.prg_modificada = 1,\n"
                + "    p.autorizado = -1,\n"
                + "    p.user_autorizado = '',\n"
                + "    p.user_genera = ?2,\n"
                + "    p.fecha_genera = ?3,\n"
                + "    p.fecha_autoriza = NULL,\n"
                + "    p.id_prg_sercon_motivo = ?4,\n"
                + "    p.modificado = ?3,\n"
                + "    p.username = ?2\n"
                + "WHERE\n"
                + "    p.id_empleado = ?5\n"
                + "        AND p.fecha BETWEEN ?6 AND ?7\n"
                + "        AND p.liquidado <> 1";
        Query q = em.createNativeQuery(sql_nomina_borrada);
        q.setParameter(1, observacion);
        q.setParameter(2, username);
        q.setParameter(3, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(4, idMotivo);
        q.setParameter(5, idEmpleado);
        q.setParameter(6, Util.dateFormat(desde));
        q.setParameter(7, Util.dateFormat(hasta));
        q.setParameter(8, Jornada.hr_cero);
        q.executeUpdate();
    }

    @Override
    public int updatePrgSerconMySerconConfirm(Integer idPrgSercon,
            Integer idMySerconConfirm, String username) {
        Query q = em.createNativeQuery("UPDATE prg_sercon p \n"
                + "SET \n"
                + "    id_my_app_sercon_confirm = ?2,\n"
                + "    p.modificado = ?3,\n"
                + "    p.username = ?4\n"
                + "WHERE\n"
                + "    p.id_prg_sercon = ?1\n");
        q.setParameter(1, idPrgSercon);
        q.setParameter(2, idMySerconConfirm);
        q.setParameter(3, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(4, username);
        return q.executeUpdate();
    }

    @Override
    public List<Date> validarDiasLiquidadosByFechasAndUnidadFuncionalAndOperador(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT distinct fecha FROM prg_sercon \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "and id_empleado = ?4 "
                    + "and liquidado = 0 and estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConsolidadoNominaDetallado> obtenerDatosDetalladoNomina(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

            String sql = "select\n"
                    + "uf.nombre as empresa,\n"
                    + "e.identificacion,  \n"
                    + "UCASE(CONCAT(e.nombres,' ',e.apellidos)) as nombre,\n"
                    + "ps.fecha,\n"
                    + "ps.time_origin,\n"
                    + "ps.time_destiny,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(diurnas) ELSE 0 END/ 3600,0) as diurnas,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(nocturnas) ELSE 0 END / 3600,0) as nocturnas,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_diurna) ELSE 0 END/ 3600,0) as extra_diurna,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_diurno) ELSE 0 END/ 3600,0) as festivo_diurno,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_nocturno) ELSE 0 END/ 3600,0) as festivo_nocturno ,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_diurno) ELSE 0 END/ 3600,0) as festivo_extra_diurno,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(festivo_extra_nocturno) ELSE 0 END/ 3600,0) as festivo_extra_nocturno,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(extra_nocturna) ELSE 0 END/ 3600,0) as extra_nocturna,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurnas) ELSE 0 END/ 3600,0) as dominical_comp_diurnas,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturnas) ELSE 0 END/ 3600,0) as dominical_comp_nocturnas,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_diurna_extra) ELSE 0 END/ 3600,0) as dominical_comp_diurna_extra,\n"
                    + "ifnull(CASE WHEN nomina_borrada <> 1 then TIME_TO_SEC(dominical_comp_nocturna_extra) ELSE 0 END/ 3600,0) as dominical_comp_nocturna_extra\n"
                    + "from\n"
                    + "   prg_sercon ps\n"
                    + "inner join empleado e on\n"
                    + "   e.id_empleado = ps.id_empleado\n"
                    + "inner join gop_unidad_funcional uf on\n"
                    + "   uf.id_gop_unidad_funcional = e.id_gop_unidad_funcional\n"
                    + "where\n"
                    + "   fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "order by ps.id_empleado;";

            Query query = em.createNativeQuery(sql, "ReporteDetalladoNominaMapping");
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
    public List<HoraPrgEjecDTO> getHoraPrgEjec(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    MIN(ps.fecha) AS fecha_inicio,\n"
                + "    MAX(ps.fecha) AS fecha_fin,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    e.identificacion as identificacion,\n"
                + "    e.codigo_tm as codigo_tm,\n"
                + "    e.nombres as nombres,\n"
                + "    e.apellidos as apellidos,\n"
                + "    CAST(SEC_TO_TIME((SUM((IFNULL(TIME_TO_SEC(ps.time_destiny), 0) "
                + "     - IFNULL(TIME_TO_SEC(ps.time_origin), 0)) + (IFNULL(TIME_TO_SEC(ps.hfin_turno2), 0) "
                + "     - IFNULL(TIME_TO_SEC(ps.hini_turno2), 0)) + (IFNULL(TIME_TO_SEC(ps.hfin_turno3), 0) "
                + "     - IFNULL(TIME_TO_SEC(ps.hini_turno3), 0))))) AS NCHAR) AS horas_programadas,\n"
                + "    CAST(SEC_TO_TIME(SUM(CASE\n"
                + "                WHEN ps.nomina_borrada = 1 THEN TIME_TO_SEC('00:00:00')\n"
                + "                ELSE CASE\n"
                + "                    WHEN\n"
                + "                        ((ps.prg_modificada IS NULL\n"
                + "                            OR ps.prg_modificada = 0)\n"
                + "                            OR (ps.prg_modificada = 1\n"
                + "                            AND ps.autorizado = 0))\n"
                + "                    THEN\n"
                + "                        (IFNULL(TIME_TO_SEC(ps.dominical_comp_diurnas),\n"
                + "                                0) + IFNULL(TIME_TO_SEC(ps.dominical_comp_nocturnas),\n"
                + "                                0) + IFNULL(TIME_TO_SEC(ps.festivo_nocturno), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.festivo_diurno), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.nocturnas), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.diurnas), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.extra_diurna), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.extra_nocturna), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.festivo_extra_diurno), 0) "
                + "                                   + IFNULL(TIME_TO_SEC(ps.festivo_extra_nocturno),\n"
                + "                                0))\n"
                + "                    ELSE CASE\n"
                + "                        WHEN\n"
                + "                            ps.prg_modificada IS NOT NULL\n"
                + "                                AND (ps.prg_modificada = 1\n"
                + "                                AND ps.autorizado <> 0)\n"
                + "                        THEN\n"
                + "                            ((IFNULL(TIME_TO_SEC(ps.real_time_destiny), 0) - IFNULL(TIME_TO_SEC(ps.real_time_origin), 0)) "
                + "                              + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno2), 0)) "
                + "                              + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno3), 0)))\n"
                + "                        ELSE TIME_TO_SEC('00:00:00')\n"
                + "                    END\n"
                + "                END\n"
                + "            END)) AS NCHAR)  AS horas_reales,\n"
                + "    SUM(((CASE\n"
                + "            WHEN ps.nomina_borrada = 1 THEN 1\n"
                + "            ELSE CASE\n"
                + "                WHEN\n"
                + "                    (((ps.prg_modificada IS NULL\n"
                + "                        OR ps.prg_modificada = 0)\n"
                + "                        OR (ps.prg_modificada = 1\n"
                + "                        AND ps.autorizado = 0))\n"
                + "                        AND ((IFNULL(TIME_TO_SEC(ps.dominical_comp_diurnas),\n"
                + "                            0) + IFNULL(TIME_TO_SEC(ps.dominical_comp_nocturnas),\n"
                + "                            0) + IFNULL(TIME_TO_SEC(ps.festivo_nocturno), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.festivo_diurno), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.nocturnas), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.diurnas), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.extra_diurna), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.extra_nocturna), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.festivo_extra_diurno), 0) "
                + "                               + IFNULL(TIME_TO_SEC(ps.festivo_extra_nocturno),\n"
                + "                            0))) = 0)\n"
                + "                THEN\n"
                + "                    1\n"
                + "                ELSE CASE\n"
                + "                    WHEN\n"
                + "                        ps.prg_modificada IS NOT NULL\n"
                + "                            AND (ps.prg_modificada = 1\n"
                + "                            AND ps.autorizado <> 0\n"
                + "                            AND (((IFNULL(TIME_TO_SEC(ps.real_time_destiny), 0) - IFNULL(TIME_TO_SEC(ps.real_time_origin), 0)) "
                + "                             + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno2), 0)) "
                + "                             + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno3), 0))) = 0))\n"
                + "                    THEN\n"
                + "                        1\n"
                + "                    ELSE CASE\n"
                + "                        WHEN (((IFNULL(TIME_TO_SEC(ps.time_destiny), 0) - IFNULL(TIME_TO_SEC(ps.time_origin), 0)) "
                + "                         + (IFNULL(TIME_TO_SEC(ps.hfin_turno2), 0) - IFNULL(TIME_TO_SEC(ps.hini_turno2), 0)) "
                + "                         + (IFNULL(TIME_TO_SEC(ps.hfin_turno3), 0) - IFNULL(TIME_TO_SEC(ps.hini_turno3), 0))) = 0) THEN 1\n"
                + "                        ELSE 0\n"
                + "                    END\n"
                + "                END\n"
                + "            END\n"
                + "        END) = 1)) AS dias_sin_operar\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "        INNER JOIN\n"
                + "    empleado e ON ps.id_empleado = e.id_empleado\n"
                + "     inner join gop_unidad_funcional guf on\n"
                + "   ps.id_gop_unidad_funcional = guf.id_gop_unidad_funcional\n"
                + "WHERE\n"
                + "    ps.fecha BETWEEN DATE(?1) AND DATE(?2)\n"
                + "        AND ps.estado_reg = 0\n"
                + sql_unida_func
                + "GROUP BY ps.id_empleado\n"
                + "ORDER BY 4 ASC;";
        Query q = em.createNativeQuery(sql, "HoraPrgEjecMapping");
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUnidadFuncional);
        return q.getResultList();

    }

    @Override
    public List<ConsolidadoLiquidacionGMO> obtenerDatosConsolidadoQuincenal(Date desde, Date hasta,
            int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND gj.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    MIN(fecha) AS desde,\n"
                + "    MAX(fecha) AS hasta,\n"
                + "    CONCAT(YEAR(fecha),\n"
                + "            '/',\n"
                + "            MONTH(fecha),\n"
                + "            '-',\n"
                + "            CASE\n"
                + "                WHEN DAY(fecha) < 16 THEN 1\n"
                + "                ELSE 2\n"
                + "            END) AS quincena,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(nocturnas)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS nocturnas,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_diurna)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS extra_diurna,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_nocturna)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS extra_nocturna,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_diurno)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS festivo_diurno,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_nocturno)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS festivo_nocturno,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_diurno)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS festivo_extra_diurno,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_nocturno)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS festivo_extra_nocturno,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurnas)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS dominical_comp_diurnas,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturnas)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS dominical_comp_nocturnas,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurna_extra)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS dominical_comp_diurna_extra,\n"
                + "    IFNULL(SUM(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturna_extra)\n"
                + "                ELSE 0\n"
                + "            END) / 3600,\n"
                + "            0) AS dominical_comp_nocturna_extra\n"
                + "FROM\n"
                + "    prg_sercon gj\n"
                + "        INNER JOIN\n"
                + "    empleado e ON e.id_empleado = gj.id_empleado\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_cargo etc ON e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                + "        INNER JOIN\n"
                + "    empleado_cargo_costo ecc ON e.id_empleado_cargo = ecc.id_empleado_tipo_cargo\n"
                + "WHERE\n"
                + "    fecha BETWEEN ?1 AND ?2\n"
                + "        AND ecc.desde <= ?1\n"
                + "        AND ecc.hasta >= ?2\n"
                + sql_unida_func
                + "GROUP BY quincena;";

        Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqQuincenalMapping");
        query.setParameter(1, Util.dateFormat(desde));
        query.setParameter(2, Util.dateFormat(hasta));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<ConsolidadoLiquidacionGMO> obtenerDatosConsolidadoDetalle(Date desde, Date hasta,
            int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    ps.fecha,\n"
                + "    CONCAT(YEAR(fecha),\n"
                + "            '/',\n"
                + "            MONTH(fecha),\n"
                + "            '-',\n"
                + "            CASE\n"
                + "                WHEN DAY(fecha) < 16 THEN 1\n"
                + "                ELSE 2\n"
                + "            END) AS quincena,\n"
                + "    ecc.costo as salario,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(nocturnas)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS nocturnas,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_diurna)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS extra_diurna,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_diurno)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS festivo_diurno,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_nocturno)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS festivo_nocturno,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_diurno)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS festivo_extra_diurno,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(festivo_extra_nocturno)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS festivo_extra_nocturno,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(extra_nocturna)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS extra_nocturna,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurnas)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS dominical_comp_diurnas,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturnas)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS dominical_comp_nocturnas,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_diurna_extra)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS dominical_comp_diurna_extra,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN nomina_borrada <> 1 THEN TIME_TO_SEC(dominical_comp_nocturna_extra)\n"
                + "                ELSE 0\n"
                + "            END / 3600,\n"
                + "            0) AS dominical_comp_nocturna_extra\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "        INNER JOIN\n"
                + "    empleado e ON e.id_empleado = ps.id_empleado\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_cargo etc ON e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                + "        INNER JOIN\n"
                + "    empleado_cargo_costo ecc ON e.id_empleado_cargo = ecc.id_empleado_tipo_cargo\n"
                + "WHERE\n"
                + "    ps.fecha BETWEEN ?1 AND ?2\n"
                + sql_unida_func
                + "ORDER BY 1 ASC , 3 ASC;";

        Query query = em.createNativeQuery(sql, "ReporteConsolidadoLiqDetallelMapping");
        query.setParameter(1, Util.dateFormat(desde));
        query.setParameter(2, Util.dateFormat(hasta));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<EntradaSalidaJornadaDTO> findEntradaSalidasJornadas(Date desde, Date hasta,
            int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT \n"
                + "    ps.fecha AS fecha,\n"
                + "    guf.nombre AS nombre_uf,\n"
                + "    e.identificacion AS identificacion,\n"
                + "    e.codigo_tm AS codigo_tm,\n"
                + "    CONCAT(e.nombres, ' ', e.apellidos) AS nombre_operador,\n"
                + "    ps.time_origin AS hora_ingreso_prg,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN ps.id_my_app_sercon_confirm IS NOT NULL THEN 1\n"
                + "            END,\n"
                + "            0) AS presentacion_mymovil,\n"
                + "    TIME(mc.fecha) AS hora_ingreso_presentacion,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN ps.hfin_turno3 IS NOT NULL THEN ps.hfin_turno3\n"
                + "                ELSE CASE\n"
                + "                    WHEN ps.hfin_turno2 IS NOT NULL THEN ps.hfin_turno2\n"
                + "                    ELSE ps.time_destiny\n"
                + "                END\n"
                + "            END,\n"
                + "            NULL) AS hora_salida_prg,\n"
                + "    IFNULL(CASE\n"
                + "                WHEN ps.id_my_app_sercon_confirm_last IS NOT NULL THEN 1\n"
                + "            END,\n"
                + "            0) AS salida_mymovil,\n"
                + "    TIME(mcl.fecha) AS hora_salida_registrada,\n"
                + "    ps.prg_modificada,\n"
                + "    ps.nomina_borrada,\n"
                + "   CAST( SEC_TO_TIME((((IFNULL(TIME_TO_SEC(ps.time_destiny), 0) - IFNULL(TIME_TO_SEC(ps.time_origin), 0)) \n"
                + "    + (IFNULL(TIME_TO_SEC(ps.hfin_turno2), 0) - IFNULL(TIME_TO_SEC(ps.hini_turno2), 0)) \n"
                + "    + (IFNULL(TIME_TO_SEC(ps.hfin_turno3), 0) - IFNULL(TIME_TO_SEC(ps.hini_turno3), 0))))) AS NCHAR) AS total_horas_programdas,\n"
                + "    CAST(IFNULL(CASE\n"
                + "                WHEN ps.nomina_borrada = 1 THEN '00:00:00'\n"
                + "                ELSE CASE\n"
                + "                    WHEN\n"
                + "                        ps.autorizado = 1\n"
                + "                            AND ps.prg_modificada = 1\n"
                + "                    THEN\n"
                + "                        SEC_TO_TIME((((IFNULL(TIME_TO_SEC(ps.real_time_destiny), 0) - IFNULL(TIME_TO_SEC(ps.real_time_origin), 0)) \n"
                + "                        + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno2), 0)) \n"
                + "                        + (IFNULL(TIME_TO_SEC(ps.real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(ps.real_hini_turno3), 0)))))\n"
                + "                END\n"
                + "            END,\n"
                + "            '00:00:00') AS NCHAR) AS total_horas_reales,\n"
                + "    (SELECT \n"
                + "            CONCAT(pt.tarea, ' - ', tc.tabla)\n"
                + "        FROM\n"
                + "            prg_tc tc\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                x.*\n"
                + "            FROM\n"
                + "                prg_tarea x\n"
                + "            WHERE\n"
                + "                x.sum_distancia = 1 AND x.comercial = 1) pt ON tc.id_task_type = pt.id_prg_tarea\n"
                + "                INNER JOIN\n"
                + "            (SELECT \n"
                + "                psp.*\n"
                + "            FROM\n"
                + "                prg_stop_point psp\n"
                + "            WHERE\n"
                + "                psp.is_depot IS NULL OR psp.is_depot = 0) pspx ON tc.to_stop = pspx.id_prg_stoppoint\n"
                + "        WHERE\n"
                + "            tc.fecha = ps.fecha\n"
                + "                AND tc.id_empleado = ps.id_empleado\n"
                + "                AND tc.id_task_type IS NOT NULL\n"
                + "        ORDER BY tc.time_destiny DESC\n"
                + "        LIMIT 1) AS ultima_ruta\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "        INNER JOIN\n"
                + "    empleado e ON ps.id_empleado = e.id_empleado\n"
                + "        INNER JOIN\n"
                + "    gop_unidad_funcional guf ON ps.id_gop_unidad_funcional = guf.id_gop_unidad_funcional\n"
                + "        LEFT JOIN\n"
                + "    my_app_sercon_confirm mc ON ps.id_my_app_sercon_confirm = mc.id_my_app_sercon_confirm\n"
                + "        LEFT JOIN\n"
                + "    my_app_sercon_confirm_last mcl ON ps.id_my_app_sercon_confirm_last = mcl.id_my_app_sercon_confirm_last\n"
                + "WHERE\n"
                + "    ps.fecha BETWEEN ?1 AND ?2\n"
                + sql_unida_func
                + "ORDER BY ps.fecha ASC;";

        Query query = em.createNativeQuery(sql, "EntradaSalidaJornadaMapping");
        query.setParameter(1, Util.dateFormat(desde));
        query.setParameter(2, Util.dateFormat(hasta));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<PrgSercon> findJornadasProductivasByFecha(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND ps.id_gop_unidad_funcional = ?2\n";
        String sql_unida_func_x = idGopUnidadFuncional == 0 ? "" : "       AND x.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    ps.*\n"
                + "FROM\n"
                + "    prg_sercon ps\n"
                + "        INNER JOIN\n"
                + "    (SELECT \n"
                + "        psx.*\n"
                + "    FROM\n"
                + "        (SELECT \n"
                + "        x.id_empleado,\n"
                + "            (SUM(CASE\n"
                + "                WHEN x.nomina_borrada = 1 THEN TIME_TO_SEC('00:00:00')\n"
                + "                ELSE CASE\n"
                + "                    WHEN\n"
                + "                        ((x.prg_modificada IS NULL\n"
                + "                            OR x.prg_modificada = 0)\n"
                + "                            OR (x.prg_modificada = 1\n"
                + "                            AND x.autorizado <= 0))\n"
                + "                    THEN\n"
                + "                        (IFNULL(TIME_TO_SEC(x.diurnas), 0) + IFNULL(TIME_TO_SEC(x.nocturnas), 0) + IFNULL(TIME_TO_SEC(x.extra_diurna), 0) + IFNULL(TIME_TO_SEC(x.extra_nocturna), 0) + IFNULL(TIME_TO_SEC(x.festivo_diurno), 0) + IFNULL(TIME_TO_SEC(x.festivo_nocturno), 0) + IFNULL(TIME_TO_SEC(x.festivo_extra_diurno), 0) + IFNULL(TIME_TO_SEC(x.festivo_extra_nocturno), 0))\n"
                + "                    ELSE CASE\n"
                + "                        WHEN\n"
                + "                            x.prg_modificada IS NOT NULL\n"
                + "                                AND (x.prg_modificada = 1\n"
                + "                                AND x.autorizado = 0)\n"
                + "                        THEN\n"
                + "                            ((IFNULL(TIME_TO_SEC(x.real_time_destiny), 0) - IFNULL(TIME_TO_SEC(x.real_time_origin), 0)) + (IFNULL(TIME_TO_SEC(x.real_hfin_turno2), 0) - IFNULL(TIME_TO_SEC(x.real_hini_turno2), 0)) + (IFNULL(TIME_TO_SEC(x.real_hfin_turno3), 0) - IFNULL(TIME_TO_SEC(x.real_hini_turno3), 0)))\n"
                + "                        ELSE TIME_TO_SEC('00:00:00')\n"
                + "                    END\n"
                + "                END\n"
                + "            END)) AS total\n"
                + "    FROM\n"
                + "        prg_sercon x\n"
                + "    WHERE\n"
                + "        x.fecha = ?1\n"
                + sql_unida_func_x
                + "    GROUP BY 1) psx) b ON b.id_empleado = ps.id_empleado\n"
                + "WHERE\n"
                + "    ps.fecha = ?1 AND b.total > 0\n"
                + sql_unida_func
                + "ORDER BY ps.id_empleado DESC;";

        Query query = em.createNativeQuery(sql, PrgSercon.class);
        query.setParameter(1, Util.dateFormat(fecha));
        query.setParameter(2, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public List<EmpleadoDescansoDTO> findDescansosByRangeDate(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND c.id_gop_unidad_funcional = ?3\n";

        String sql = "SELECT c.id_empleado,\n"
                + "    SUM(CASE\n"
                + "        WHEN\n"
                + "            c.sercon = 'Descanso'\n"
                + "                AND (c.real_time_origin IS NULL\n"
                + "                OR (TIME_TO_SEC(c.real_time_origin) = 0\n"
                + "                AND c.autorizado = 1)\n"
                + "                OR (TIME_TO_SEC(c.real_time_origin) > 0\n"
                + "                AND c.autorizado < 1))\n"
                + "        THEN\n"
                + "            1\n"
                + "        ELSE 0\n"
                + "    END) AS total_dias_descanso\n"
                + "FROM\n"
                + "    prg_sercon c\n"
                + "WHERE\n"
                + "    c.fecha BETWEEN ?1 AND ?2\n"
                + sql_unida_func
                + "        AND c.fecha NOT IN (SELECT \n"
                + "            pf.fecha\n"
                + "        FROM\n"
                + "            param_feriado pf\n"
                + "        WHERE\n"
                + "            pf.fecha BETWEEN ?1 AND ?2) \n"
                + "            group by c.id_empleado ";
        Query query = em.createNativeQuery(sql, "EmpleadoDescansoMapping");
        query.setParameter(1, Util.dateFormat(desde));
        query.setParameter(2, Util.dateFormat(hasta));
        query.setParameter(3, idGopUnidadFuncional);
        return query.getResultList();
    }

    @Override
    public void updatePrgSerconFromList(List<PrgSerconLiqUtil> sercones, int opc) {
        String consulta = "";
        String parte = "";
        Query q = null;
        if (opc == 1) {
            parte = " AND (ps.autorizado <> 1 OR ps.autorizado is NULL);";
        }
        try {
            for (PrgSerconLiqUtil s : sercones) {
                consulta = "UPDATE prg_sercon ps SET "
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
    public void updatePrgSerconFromListWithoutProductionTime(List<PrgSerconLiqUtil> sercones) {
        Query q = null;
        try {
            for (PrgSerconLiqUtil s : sercones) {
                q = this.em.createNativeQuery("UPDATE prg_sercon ps SET "
                        + "ps.diurnas = '" + getData(s.getDiurnas()) + "', "
                        + "ps.nocturnas = '" + getData(s.getNocturnas()) + "', "
                        + "ps.extra_diurna = '" + getData(s.getExtraDiurna()) + "', "
                        + "ps.extra_nocturna = '" + getData(s.getExtraNocturna()) + "', "
                        + "ps.festivo_diurno = '" + getData(s.getFestivoDiurno()) + "', "
                        + "ps.festivo_nocturno = '" + getData(s.getFestivoNocturno()) + "', "
                        + "ps.festivo_extra_diurno = '" + getData(s.getFestivoExtraDiurno()) + "', "
                        + "ps.festivo_extra_nocturno = '" + getData(s.getFestivoExtraNocturno()) + "', "
                        + "ps.dominical_comp_diurnas = '" + getData(s.getDominicalCompDiurnas()) + "', "
                        + "ps.dominical_comp_nocturnas = '" + getData(s.getDominicalCompNocturnas()) + "' "
                        + "WHERE ps.fecha = '" + Util.dateFormat(s.getFecha()) + "' "
                        + "AND ps.id_empleado = " + s.getIdEmpleado().getIdEmpleado() + " "
                        + "AND ps.estado_reg = 0 ");
                q.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PrgSercon> obtenerRegistrosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT * FROM prg_sercon \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "AND id_empleado = ?4 \n"
                    + "AND liquidado <> 1 \n"
                    + " AND estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql, PrgSercon.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            query.setParameter(4, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Date> obtenerDiasLiquidadosByFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT distinct fecha FROM prg_sercon \n"
                    + "where fecha BETWEEN ?1 and ?2\n"
                    + sql_unida_func
                    + "and liquidado = 1 and estado_reg = 0;";
            Query query = this.em.createNativeQuery(sql);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void eliminarJornada(Integer idEmpleado, Date desde, Date hasta, String username) {
        String sql_nomina_borrada = "UPDATE prg_sercon \n"
                + "SET \n"
                + "    nomina_borrada = 1,\n"
                + "    autorizado = 1,\n"
                + "    user_autorizado = ?2,\n"
                + "    fecha_autoriza = ?6,\n"
                + "    modificado = ?1\n"
                + " WHERE\n"
                + "    id_empleado = ?3\n"
                + "        AND fecha BETWEEN ?4 AND ?5\n"
                + "        AND liquidado <> 1";

        Query q = em.createNativeQuery(sql_nomina_borrada);
        q.setParameter(1, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(2, username);
        q.setParameter(3, idEmpleado);
        q.setParameter(4, Util.dateFormat(desde));
        q.setParameter(5, Util.dateFormat(hasta));
        q.setParameter(6, MovilidadUtil.fechaCompletaHoy());
        q.executeUpdate();
    }

    @Override
    public List<PrgSercon> obtenerSerconesPorRangoFechasYEmpleado(Date desde, Date hasta, int idGopUnidadFuncional, int idEmpleado) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?3\n";

            return this.em.createNativeQuery("SELECT \n"
                    + "    p.* \n"
                    + "FROM \n"
                    + "    prg_sercon p \n"
                    + "WHERE \n"
                    + "    p.fecha BETWEEN ?1 AND ?2 \n"
                    + " AND p.id_empleado = ?4 \n"
                    + "        AND p.estado_reg = 0 AND p.liquidado <> 1 "
                    + sql_unida_func, PrgSercon.class)
                    .setParameter(1, Util.dateFormat(desde))
                    .setParameter(2, Util.dateFormat(hasta))
                    .setParameter(3, idGopUnidadFuncional)
                    .setParameter(4, idEmpleado)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void eliminarJornadaAusentismo(Integer idEmpleado, Date desde,
            Date hasta, String username, Integer idPrgSerconMotivo,
            String observacion) {
        String sql_nomina_borrada = "UPDATE prg_sercon \n"
                + "SET \n"
                + "    nomina_borrada = 1,\n"
                + "    autorizado = 1,\n"
                + "    prg_modificada = 1,\n"
                + "    user_autorizado = ?2,\n"
                + "    fecha_autoriza = ?6,\n"
                + "    id_prg_sercon_motivo = ?7,\n"
                + "    observaciones = ?8,\n"
                + "    modificado = ?1\n"
                + " WHERE\n"
                + "    id_empleado = ?3\n"
                + "        AND fecha BETWEEN ?4 AND ?5\n"
                + "        AND liquidado <> 1";

        Query q = em.createNativeQuery(sql_nomina_borrada);
        q.setParameter(1, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(2, username);
        q.setParameter(3, idEmpleado);
        q.setParameter(4, Util.dateFormat(desde));
        q.setParameter(5, Util.dateFormat(hasta));
        q.setParameter(6, MovilidadUtil.fechaCompletaHoy());
        q.setParameter(7, idPrgSerconMotivo);
        q.setParameter(8, observacion);
        q.executeUpdate();
    }

    @Override
    public List<PrgSerconInicial> findJornadasCargadas(int idGopUnidadFuncional, Date desde, Date hasta) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT p.* FROM prg_sercon_inicial p WHERE (p.fecha BETWEEN ?2 AND ?3) AND p.status = 0 AND p.estado_reg=0\n"
                    + sql_unida_func + ";";
            Query query = em.createNativeQuery(sql, PrgSerconInicial.class);
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, desde);
            query.setParameter(3, hasta);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<PrgSercon> findByDateAndLiquidado(Date desde, Date hasta, int idGopUnidadFuncional, Integer liquidado, int id) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " AND p.id_gop_unidad_funcional = ?1\n";
            String sql_id_emp = id == 0 ? "" : "       AND p.id_empleado = ?5\n";
            String sql = "SELECT "
                    + "p.* "
                    + "FROM "
                    + "prg_sercon p "
                    + "WHERE p.fecha BETWEEN ?2 AND ?3 "
                    + "AND p.liquidado = ?4" + sql_id_emp + sql_unida_func;
            Query q = em.createNativeQuery(sql, PrgSercon.class);
            q.setParameter(1, idGopUnidadFuncional);
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
    public PrgSercon findSerconProgramado(Date fecha, Integer id_empleado, String hiniPrgTurno1, String hfinPrgTurno1,
            String hiniPrgTurno2, String hfinPrgTurno2, String hiniPrgTurno3, String hfinPrgTurno3) {
        try {
            String sql = "SELECT "
                    + "p.* FROM "
                    + "prg_sercon p "
                    + "WHERE p.fecha = ?1 AND p.id_empleado = ?2 AND\n"
                    + "      p.time_origin = ?3 AND p.time_destiny = ?4 AND\n"
                    + "      (p.hini_turno2 = ?5 OR hini_turno2 is null) AND\n"
                    + "      (p.hfin_turno2 = ?6 OR hfin_turno2 is null) AND\n"
                    + "      (p.hini_turno3 = ?7 OR hini_turno3 is null) AND\n"
                    + "      (p.hfin_turno3 = ?8 OR hfin_turno3 is null) AND\n"
                    + "      p.liquidado = 0 AND p.estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgSercon.class);
            q.setParameter(1, fecha);
            q.setParameter(2, id_empleado);
            q.setParameter(3, hiniPrgTurno1);
            q.setParameter(4, hfinPrgTurno1);
            q.setParameter(5, hiniPrgTurno2);
            q.setParameter(6, hfinPrgTurno2);
            q.setParameter(7, hiniPrgTurno3);
            q.setParameter(8, hfinPrgTurno3);
            return (PrgSercon) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
