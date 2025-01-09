/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.ParrillaDTO;
import com.movilidad.model.Vehiculo;
import com.movilidad.util.beans.ResEstadoActFlota;
import com.movilidad.util.beans.SumDistanciaEntradaPatioDTO;
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
public class VehiculoFacade extends AbstractFacade<Vehiculo> implements VehiculoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoFacade() {
        super(Vehiculo.class);
    }

    @Override
    public Vehiculo getVehiculo(String codigo, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " and id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT * FROM vehiculo WHERE codigo LIKE '%" + codigo + "'"
                    + sql_unida_func
                    + "AND estado_reg = 0 LIMIT 1;";

            Query q = em.createNativeQuery(sql, Vehiculo.class);
            q.setParameter(1, codigo);
            q.setParameter(2, idGopUnidadFuncional);
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Vehiculo getVehiculoCodigo(String s) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM vehiculo WHERE codigo = ?1 "
                    + "AND estado_reg = 0", Vehiculo.class)
                    .setParameter(1, "" + s + "");
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Vehiculo getVehiculoPlaca(String placa) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM vehiculo WHERE placa = ?1 "
                    + "AND estado_reg = 0", Vehiculo.class)
                    .setParameter(1, "" + placa + "");
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Vehiculo> getParametros(String campo, String valor, Integer id) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM vehiculo WHERE " + campo + " = '" + valor + "' "
                    + "AND id_vehiculo <> " + id + " AND estado_reg = 0", Vehiculo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("error en el facade");
            return null;
        }
    }

    @Override
    public List<Vehiculo> getDisponibles(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_unida_func_v = idGopUnidadFuncional == 0 ? "" : "      AND v.id_gop_unidad_funcional = ?2\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    v.*\n"
                + "FROM\n"
                + "    vehiculo v\n"
                + "        INNER JOIN\n"
                + "    vehiculo_tipo_estado vte ON v.id_vehiculo_tipo_estado = vte.id_vehiculo_tipo_estado\n"
                + "WHERE\n"
                + "    v.id_vehiculo NOT IN (SELECT DISTINCT\n"
                + "            tc.id_vehiculo\n"
                + "        FROM\n"
                + "            prg_tc tc\n"
                + "        WHERE\n"
                + "            tc.fecha = DATE(?1)\n"
                + sql_unida_func
                + "                AND TIME(tc.time_destiny) >= TIME(NOW())\n"
                + "                AND tc.id_vehiculo IS NOT NULL\n"
                + "                AND tc.estado_reg = 0\n"
                + "                AND tc.estado_operacion NOT IN (5 , 99, 8))\n"
                + "        AND vte.restriccion_operacion = 0"
                + sql_unida_func_v, Vehiculo.class);
        q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public int getVehiculosDisponibles(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?1\n";
            Query query = em.createNativeQuery("select count(*) from vehiculo where "
                    + "id_vehiculo_tipo_estado =1"
                    + sql_unida_func
                    + ";");
            query.setParameter(1, idGopUnidadFuncional);
            long res = (long) query.getSingleResult();
            return (int) res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public int getVehiculosOperando(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND p.id_gop_unidad_funcional = ?2\n";
            Query query = em.createNativeQuery("select count(DISTINCT p.id_vehiculo) from prg_tc p "
                    + "where p.fecha=?1 "
                    + sql_unida_func
                    + "and p.id_vehiculo is not null");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);
            long res = (long) query.getSingleResult();
            return (int) res;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<Vehiculo> getVehiclosByType(int tipo) {
        try {
            Query q = em.createNativeQuery("SELECT v.* FROM vehiculo v WHERE v.id_vehiculo_tipo=?1 AND v.estado_reg=0 order by v.codigo asc", Vehiculo.class);
            q.setParameter(1, tipo);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Vehiculo> getVehiclosActivo() {
        try {
            Query q = em.createNativeQuery("SELECT v.* FROM vehiculo v WHERE v.estado_reg=0 order by v.codigo asc", Vehiculo.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getVehiculos() {
        try {
            Query q = em.createNativeQuery("SELECT v.codigo FROM vehiculo v WHERE v.estado_reg=0 order by v.codigo asc");
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Vehiculo findVehiculoExist(String codigo, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND v.id_gop_unidad_funcional = ?2\n";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    v.*\n"
                    + "FROM\n"
                    + "    vehiculo v\n"
                    + "WHERE\n"
                    + "    v.codigo LIKE '%" + codigo + "'\n"
                    //                    + "        AND v.id_vehiculo_tipo_estado = 1\n"
                    + sql_unida_func
                    + "        AND v.estado_reg = 0;", Vehiculo.class);
//            q.setParameter(1, codigo);
            q.setParameter(2, idGopUnidadFuncional);
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
//            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String esVehiculoRequerido(int idVehiculo, Date fecha) {
        try {        
            Query query = em.createNativeQuery("CALL ValidarFechaHabilitacion(?2, ?1);");
            query.setParameter(1, idVehiculo);
            query.setParameter(2, Util.dateTimeFormat(fecha));
            return query.getSingleResult().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @Override
    public Vehiculo findVehiculoByCod(String codigo, Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND v.id_gop_unidad_funcional = ?3\n";

            Query q = em.createNativeQuery("SELECT \n"
                    + "    v.*\n"
                    + "FROM\n"
                    + "    vehiculo v\n"
                    + "WHERE\n"
                    + "    NOT EXISTS( SELECT \n"
                    + "            prg.*\n"
                    + "        FROM\n"
                    + "            prg_tc prg\n"
                    + "        WHERE\n"
                    + "            v.id_vehiculo = prg.id_vehiculo\n"
                    + "                AND prg.fecha = ?2)\n"
                    + "        AND v.codigo LIKE '%" + codigo + "'\n"
                    + sql_unida_func
                    + "        AND v.id_vehiculo_tipo_estado = 1;", Vehiculo.class);
//            q.setParameter(1, codigo);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGopUnidadFuncional);
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Vehiculo findByCodigo(String codigo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    vehiculo\n"
                    + "WHERE\n"
                    + "    codigo = ?1 AND estado_reg = 0;", Vehiculo.class);
            q.setParameter(1, codigo);
            return (Vehiculo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Vehiculo> findAllVehiculosByidGopUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         v.id_gop_unidad_funcional = ?1 AND\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    v.*\n"
                + "FROM\n"
                + "    vehiculo v\n"
                + "WHERE\n"
                + sql_unida_func
                + "    v.estado_reg = 0;", Vehiculo.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public int updateEstadoVehiculo(int idVehiculo, int idVehiculoTipoEstado) {
        Query q = em.createNativeQuery("update vehiculo set id_vehiculo_tipo_estado=?2 where id_vehiculo=?1");
        q.setParameter(1, idVehiculo);
        q.setParameter(2, idVehiculoTipoEstado);
        return q.executeUpdate();
    }

    @Override
    public List<ResEstadoActFlota> getResumenEstadoActualFlota(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : " WHERE drf.id_gop_unidad_funcional = ?1\n";

            String sql = "SELECT \n"
                    + "    drf.*\n"
                    + "FROM\n"
                    + "    disponibilidad_resumen_flota_rigel drf\n"
                    + sql_unida_func;
            Query query = em.createNativeQuery(sql, "ResEstadoActFlotaMapping");
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Vehiculo> findByidGopUnidadFuncAndTipo(int idGopUnidadFuncional, int idVehiculoTipo) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  v.id_gop_unidad_funcional = ?1 AND\n";
        String sql_tipo = idVehiculoTipo == 0 ? "" : "  v.id_vehiculo_tipo = ?2 AND\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    vehiculo v\n"
                + "WHERE\n"
                + sql_unida_func
                + sql_tipo
                + "         v.estado_reg = 0", Vehiculo.class);
        q.setParameter(1, idGopUnidadFuncional);
        q.setParameter(2, idVehiculoTipo);
        return q.getResultList();
    }

    @Override
    public Long totalDisponibles(Date fecha, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND tc.id_gop_unidad_funcional = ?2\n";
        String sql_unida_func_v = idGopUnidadFuncional == 0 ? "" : "      AND v.id_gop_unidad_funcional = ?2\n";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    count(v.id_vehiculo)\n"
                    + "FROM\n"
                    + "    vehiculo v\n"
                    + "        INNER JOIN\n"
                    + "    vehiculo_tipo_estado vte ON v.id_vehiculo_tipo_estado = vte.id_vehiculo_tipo_estado\n"
                    + "WHERE\n"
                    + "    v.id_vehiculo NOT IN (SELECT DISTINCT\n"
                    + "            tc.id_vehiculo\n"
                    + "        FROM\n"
                    + "            prg_tc tc\n"
                    + "        WHERE\n"
                    + "            tc.fecha = DATE(?1)\n"
                    + sql_unida_func
                    + "                AND TIME(tc.time_destiny) >= TIME(NOW())\n"
                    + "                AND tc.id_vehiculo IS NOT NULL\n"
                    + "                AND tc.estado_reg = 0\n"
                    + "                AND tc.estado_operacion NOT IN (5 , 99, 8))\n"
                    + "        AND vte.restriccion_operacion = 0"
                    + sql_unida_func_v);
            q.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            q.setParameter(2, idGopUnidadFuncional);
            return (Long) q.getSingleResult();

        } catch (Exception e) {
            return Long.valueOf(0);
        }
    }

    @Override
    public List<ParrillaDTO> findStatesVehicleServices(Integer idGopUF) {
        Query q = em.createNativeQuery("SELECT  "
                + "    * "
                + "FROM "
                + "    parrilla "
                + "WHERE "
                + "    uf = ?1", "ParrillaDTOMapping");
        q.setParameter(1, idGopUF);
        return q.getResultList();
    }

    @Override
    public List<SumDistanciaEntradaPatioDTO> findSumDistanciaForVehiculo(Integer idGopUF) {
        String uf = idGopUF != 0 ? "WHERE uf = " + idGopUF + " " : " ";
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    sum_distancia_vehiculo " + uf;
        Query q = em.createNativeQuery(sql, SumDistanciaEntradaPatioDTO.class);
        return q.getResultList();
    }
}
