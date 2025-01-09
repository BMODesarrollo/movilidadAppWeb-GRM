/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.Generica;
import com.movilidad.model.Novedad;
import com.movilidad.model.ParamAreaCargo;
import com.movilidad.model.ParamAreaUsr;
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
public class GenericaFacade extends AbstractFacade<Generica> implements GenericaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaFacade() {
        super(Generica.class);
    }

    @Override
    public List<Generica> findAll(Date fecha) {
        Query query = em.createQuery("SELECT gn from Generica gn "
                + "where gn.fecha = :fecha")
                .setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public List<Generica> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND gn.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
            Query query = em.createQuery("SELECT gn from Generica gn where gn.fecha "
                    + "BETWEEN :fechaIni AND :fechaFin "
                    + sql_unida_func
                    + "ORDER BY gn.fecha DESC");
            query.setParameter("fechaIni", Util.toDate(Util.dateFormat(fechaIni)));
            query.setParameter("fechaFin", Util.toDate(Util.dateFormat(fechaFin)));
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Generica validarNovedadConFechas(int empleado, Date desde, Date hasta) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM generica n "
                    + "WHERE ((n.desde BETWEEN ?2 AND  ?3) "
                    + "OR (n.hasta BETWEEN ?2 AND  ?3)) AND n.id_empleado= ?1 LIMIT 1;", Generica.class);
            query.setParameter(1, empleado);
            query.setParameter(2, Util.dateFormat(desde));
            query.setParameter(3, Util.dateFormat(hasta));
            return (Generica) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Generica verificarNovedadPMSinFechas(Date fecha, int idEmpleado, int idNovedadTipoDetalle) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM generica n WHERE n.id_empleado = ?1 "
                    + "and id_novedad_tipo_detalle =?2  and n.fecha = ?3 limit 1;", Novedad.class);
            query.setParameter(1, idEmpleado);
            query.setParameter(2, idNovedadTipoDetalle);
            query.setParameter(3, Util.dateFormat(fecha));
            return (Generica) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParamAreaUsr findByUsername(String username) {
        try {
            String sql = "select usr.* \n"
                    + "from param_area_usr usr\n"
                    + "inner join users u on usr.id_param_usr = u.user_id \n"
                    + "where u.username = ?1;";

            Query query = em.createNativeQuery(sql, ParamAreaUsr.class);
            query.setParameter(1, username);
            return (ParamAreaUsr) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Integer> obtenerCargos(int area) {
        try {
            String sql = "select\n"
                    + "	id_empleado_tipo_cargo\n"
                    + "from\n"
                    + "	param_area_cargo\n"
                    + "where\n"
                    + "	id_param_area = ?1";

            Query query = em.createNativeQuery(sql);
            query.setParameter(1, area);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Empleado> obtenerEmpleadosByCargo(List<Integer> cargos) {
        try {
            String sql = "SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    e.id_empleado_cargo IN (?1);";

            Query query = em.createNativeQuery(sql, Empleado.class);
            query.setParameter(1, cargos.toString().replace("[", "").replace("]", ""));
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Generica> findAllByArea(Date fecha, int area, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "select * from generica where fecha = ?1 and id_param_area=?2"
                    + sql_unida_func
                    + ";";

            Query query = em.createNativeQuery(sql, Generica.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, area);
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Generica> findByDateRangeAndArea(Date fechaIni, Date fechaFin, int area, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?4\n";
            String sql = "select * from generica where fecha between ?1 and ?2 and id_param_area=?3"
                    + sql_unida_func
                    + "";

            Query query = em.createNativeQuery(sql, Generica.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaIni)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));
            query.setParameter(3, area);
            query.setParameter(4, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Generica> findByDateRangeAndIdEmpleado(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            Query query = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica g\n"
                    + "        INNER JOIN\n"
                    + "    generica_tipo_detalles gtd ON g.id_generica_tipo_detalle = gtd.id_generica_tipo_detalle\n"
                    + "        AND gtd.afecta_pm = 1\n"
                    + "        AND g.fecha BETWEEN ?1 AND ?2\n"
                    + "        AND g.id_empleado = ?3;", Generica.class)
                    .setParameter(1, Util.dateFormat(fechaIni))
                    .setParameter(2, Util.dateFormat(fechaFin))
                    .setParameter(3, idEmpleado);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoSeguim(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ns.id_generica\n"
                    + "FROM\n"
                    + "    generica_seguimiento ns\n"
                    + "WHERE\n"
                    + "    ns.id_generica IN (SELECT \n"
                    + "            n.id_generica\n"
                    + "        FROM\n"
                    + "            generica n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND n.id_generica_tipo_detalle IN (SELECT \n"
                    + "                    ntd.id_generica_tipo_detalle\n"
                    + "                FROM\n"
                    + "                    generica_tipo_detalles ntd\n"
                    + "                WHERE\n"
                    + "                    ntd.afecta_pm = 1)\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, Util.dateFormat(fechaIni));
            q.setParameter(2, Util.dateFormat(fechaFin));
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Integer> findByDateRangeAndIdEmpleadoDocu(Date fechaIni, Date fechaFin, int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    nd.id_generica\n"
                    + "FROM\n"
                    + "    generica_documentos nd\n"
                    + "WHERE\n"
                    + "    nd.id_generica IN (SELECT \n"
                    + "            n.id_generica\n"
                    + "        FROM\n"
                    + "            generica n\n"
                    + "        WHERE\n"
                    + "            n.id_empleado = ?3\n"
                    + "                AND n.id_generica_tipo_detalle IN (SELECT \n"
                    + "                    ntd.id_generica_tipo_detalle\n"
                    + "                FROM\n"
                    + "                    generica_tipo_detalles ntd\n"
                    + "                WHERE\n"
                    + "                    ntd.afecta_pm = 1)\n"
                    + "                AND n.fecha BETWEEN ?1 AND ?2)\n"
                    + "GROUP BY 1;");
            q.setParameter(1, Util.dateFormat(fechaIni));
            q.setParameter(2, Util.dateFormat(fechaFin));
            q.setParameter(3, idEmpleado);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
