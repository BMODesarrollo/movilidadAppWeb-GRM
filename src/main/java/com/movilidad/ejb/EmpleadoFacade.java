/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.PmGrupo;
import com.movilidad.util.beans.InformeInterventoria;
import com.movilidad.util.beans.InformeLocalidadEmpleado;
import com.movilidad.util.beans.InformeOperadores;
import com.movilidad.util.beans.PlantaObz;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoFacade extends AbstractFacade<Empleado> implements EmpleadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoFacade() {
        super(Empleado.class);
    }

    @Override
    public Empleado findCampo(String campo, String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<Empleado> query = em.createQuery(
                        "SELECT e FROM Empleado e WHERE e." + campo + "= ?1 AND e.idEmpleadoEstado.idEmpleadoEstado=1", Empleado.class);
                if (campo.equals("codigoTm") || campo.equals("codigoInterno")) {
                    return query.setParameter(1, Integer.parseInt(value)).getSingleResult();
                } else {
                    return query.setParameter(1, value).getSingleResult();
                }
            }
            TypedQuery<Empleado> query = em.createQuery(
                    "SELECT e FROM Empleado e WHERE e." + campo + "= ?1 AND e.idEmpleado<>?2 AND e.idEmpleadoEstado.idEmpleadoEstado=1", Empleado.class);
            if (campo.equals("codigoTm") || campo.equals("codigoInterno")) {
                query.setParameter(1, Integer.parseInt(value));
                query.setParameter(2, id);
                return query.getSingleResult();
            } else {
                query.setParameter(1, value);
                query.setParameter(1, id);
                return query.getSingleResult();
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Empleado> findEmpleadosSinGrupo(int id, int id2) {

        Query q = null;
        if (id2 == 0) {
            q = em.createNativeQuery("SELECT e.* FROM empleado e "
                    + "WHERE NOT EXISTS (SELECT pm.* FROM pm_grupo pm WHERE e.id_empleado=pm.id_empleado) "
                    + "AND  NOT EXISTS (SELECT pmd.* FROM pm_grupo_detalle pmd WHERE e.id_empleado=pmd.id_empleado) "
                    + "AND e.id_empleado_cargo= ?1 AND e.id_empleado_estado=1;", Empleado.class);
            q.setParameter(1, id);
        } else {
            q = em.createNativeQuery("SELECT e.* FROM empleado e "
                    + "WHERE NOT EXISTS (SELECT pm.* FROM pm_grupo pm WHERE e.id_empleado=pm.id_empleado) "
                    + "AND  NOT EXISTS (SELECT pmd.* FROM pm_grupo_detalle pmd WHERE e.id_empleado=pmd.id_empleado) "
                    + "AND e.id_empleado_cargo in (?1,?2) AND e.id_empleado_estado=1;", Empleado.class);
            q.setParameter(1, id);
            q.setParameter(2, id2);
        }

        return q.getResultList();
    }

    @Override
    public List<Empleado> findEmpleadosSinGrupoGenerica(int idCargo, int idArea) {

        try {
            Query q = null;
            if (idArea == 0) {
                q = em.createNativeQuery("SELECT \n"
                        + "    e.*\n"
                        + "FROM\n"
                        + "    empleado e\n"
                        + "WHERE\n"
                        + "    NOT EXISTS( SELECT \n"
                        + "            pm.*\n"
                        + "        FROM\n"
                        + "            generica_pm_grupo pm\n"
                        + "        WHERE\n"
                        + "            e.id_empleado = pm.id_empleado)\n"
                        + "        AND NOT EXISTS( SELECT \n"
                        + "            pmd.*\n"
                        + "        FROM\n"
                        + "            generica_pm_grupo_detalle pmd\n"
                        + "        WHERE\n"
                        + "            e.id_empleado = pmd.id_empleado)\n"
                        + "        AND e.id_empleado_cargo = ?1\n"
                        + "        AND e.id_empleado_estado = 1;", Empleado.class);
                q.setParameter(1, idCargo);
            } else {
                q = em.createNativeQuery("SELECT \n"
                        + "    e.*\n"
                        + "FROM\n"
                        + "    empleado e\n"
                        + "        INNER JOIN\n"
                        + "    param_area_cargo c ON e.id_empleado_cargo = c.id_empleado_tipo_cargo\n"
                        + "WHERE\n"
                        + "    NOT EXISTS( SELECT \n"
                        + "            pm.*\n"
                        + "        FROM\n"
                        + "            generica_pm_grupo pm\n"
                        + "        WHERE\n"
                        + "            e.id_empleado = pm.id_empleado)\n"
                        + "        AND NOT EXISTS( SELECT \n"
                        + "            pmd.*\n"
                        + "        FROM\n"
                        + "            generica_pm_grupo_detalle pmd\n"
                        + "        WHERE\n"
                        + "            e.id_empleado = pmd.id_empleado) AND e.id_empleado_cargo<>?1\n"
                        + "        AND e.id_empleado_estado = 1 AND e.id_empleado_estado = 1\n"
                        + "        AND c.id_param_area = ?2;", Empleado.class);
                q.setParameter(1, idCargo);
                q.setParameter(2, idArea);
            }

            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Empleado getEmpleado(int codigo) {
        Query q = em.createNamedQuery("Empleado.findByCodigoTm", Empleado.class);
        q.setParameter("codigoTm", codigo);
        try {
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado getEmpleadoCodigoTM(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM empleado "
                    + "WHERE codigo_tm = ? AND estado_reg = 0", Empleado.class)
                    .setParameter(1, i);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado getEmpleadoActivoCodigoTM(int i) {
        try {
            Query q = em.createNativeQuery("SELECT e.* FROM empleado e "
                    + "WHERE e.codigo_tm = ?1 AND e.id_empleado_estado = 1 AND e.estado_reg = 0;", Empleado.class)
                    .setParameter(1, i);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public Empleado getEmpleadoCodigoInterno(int i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM empleado "
                    + "WHERE codigo_interno = ? AND estado_reg = 0", Empleado.class)
                    .setParameter(1, i);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado guardarEmpleado(Empleado empl) {
        empl.setPathFoto("/");
        try {
            return em.merge(empl);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Empleado> getColaboradores(Integer idGopUnidadFuncional, boolean flag) {
        try {
            String sql_unida_func = (idGopUnidadFuncional == null || idGopUnidadFuncional == 0) ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            String sql_codigo_tm = !flag ? "" : "        AND codigo_tm is not null\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    empleado\n"
                    + "WHERE\n"
                    + "        id_empleado_cargo IN (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_PANEL_PRINCIPAL) + ")\n"
                    + sql_unida_func
                    + sql_codigo_tm
                    + "        AND estado_reg = 0;";

            Query q = em.createNativeQuery(sql, Empleado.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PmGrupo findbyCodigoOperador(int codigo) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM empleado "
                    + "WHERE codigo_tm = ? AND estado_reg = 0", PmGrupo.class)
                    .setParameter(1, codigo);
            return (PmGrupo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve un ojbeto de Empleado por medio del criterio de busqueda codigo,
     * ademas el empleado debe estar activo.
     *
     * @param codigo
     * @param idGopUnidadFunc
     * @return
     */
    @Override
    public Empleado findbyCodigoTmAndIdGopUnidadFuncActivo(int codigo, int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?2\n";

        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    e.codigo_tm = ?1\n"
                    + "        AND e.id_empleado_estado = 1\n"
                    + sql_unida_func
                    + "        AND e.estado_reg = 0;", Empleado.class)
                    .setParameter(1, codigo).setParameter(2, idGopUnidadFunc);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Empleado getEmpleadoByUsername(String username) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    users u\n"
                    + "        RIGHT JOIN\n"
                    + "    empleado e ON u.id_empleado = e.id_empleado\n"
                    + "WHERE\n"
                    + "    u.username = ?1 ;", Empleado.class)
                    .setParameter(1, username);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int getEmpleadoByFullname(String username) {
        try {
            Query q = em.createNativeQuery("SELECT e.id_empleado "
                    + "FROM empleado e "
                    + "WHERE concat(e.nombres,' ',e.apellidos) = ?1;")
                    .setParameter(1, username);
            return (int) q.getSingleResult();
        } catch (Exception e) {
            return -1;
        }
    }

    @Override
    public int certificar(int idEmpleado, int valor, String userName) {
        try {
            Query q = em.createNativeQuery("UPDATE empleado e "
                    + "SET "
                    + "e.certificado = ?1, "
                    + "e.modificado = ?2, "
                    + "e.username = ?3 "
                    + "WHERE "
                    + "e.id_empleado = ?4;");
            q.setParameter(1, valor);
            q.setParameter(2, MovilidadUtil.fechaHoy());
            q.setParameter(3, userName);
            q.setParameter(4, idEmpleado);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<InformeInterventoria> getInformeInterventoria(Date fecha_ini, Date fecha_fin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND empleado.id_gop_unidad_funcional = ?3\n";
            Query q = em.createNativeQuery("SET lc_time_names = 'es_CO';");
            q.executeUpdate();
            String sql = "SELECT\n"
                    + "DATE_FORMAT(prg_tc.fecha,'%M-%y') fecha_registro,\n"
                    + "empleado.codigo_tm,\n"
                    + "empleado_tipo_cargo.nombre_cargo,\n"
                    + "sum(TIME_TO_SEC(prg_tc.task_duration))/3600 tiempoTotal,\n"
                    + "sum(if(prg_tarea.comercial=1,TIME_TO_SEC(prg_tc.task_duration),0))/3600 comercial,\n"
                    + "sum(if(prg_tarea.comercial=0,TIME_TO_SEC(prg_tc.task_duration),0))/3600 vacio,\n"
                    + "(select count(id_novedad) from novedad where fecha BETWEEN ?1 and ?2 and novedad.id_novedad_tipo=8 and novedad.id_novedad_tipo_detalle=91 and id_empleado = empleado.id_empleado) quejas,\n"
                    + "(select count(id_multa) from multa where fecha BETWEEN ?1 and ?2 and id_empleado = empleado.id_empleado ) multas\n"
                    + "FROM\n"
                    + "empleado\n"
                    + "INNER JOIN empleado_tipo_cargo ON empleado.id_empleado_cargo = empleado_tipo_cargo.id_empleado_tipo_cargo\n"
                    + "INNER JOIN prg_tc ON prg_tc.id_empleado = empleado.id_empleado\n"
                    + "INNER JOIN prg_tarea ON prg_tc.id_task_type = prg_tarea.id_prg_tarea\n"
                    + "where empleado_tipo_cargo.id_empleado_tipo_cargo in (28,29,30)\n"
                    + "and empleado.fecha_ingreso <=?2\n"
                    + sql_unida_func
                    + "and prg_tc.fecha BETWEEN ?1 and ?2 group by 2,3;";

            Query query = em.createNativeQuery(sql, "InformeInterventoriaMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha_ini)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fecha_fin)));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<InformeOperadores> getInformeOperadoresEnCargo(Date fecha_ini, Date fecha_fin, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND prg_tc.id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    prg_tc.fecha,\n"
                    + "    COUNT(prg_tc.fecha) AS c_fecha,\n"
                    + "    empleado.codigo_tm,\n"
                    + "    empleado.nombres,\n"
                    + "    empleado.apellidos,\n"
                    + "    empleado_tipo_cargo.nombre_cargo,\n"
                    + "    empleado.certificado,\n"
                    + "    vehiculo_tipo.nombre_tipo_vehiculo,\n"
                    + "    COUNT(prg_tc.id_prg_tc) AS total_viajes,\n"
                    + "    sum(prg_tc.distance)/1000 AS km_realizados\n"
                    + "FROM\n"
                    + "    prg_tc\n"
                    + "        INNER JOIN\n"
                    + "    empleado ON prg_tc.id_empleado = empleado.id_empleado\n"
                    + "        INNER JOIN\n"
                    + "    prg_tarea ON prg_tc.id_task_type = prg_tarea.id_prg_tarea\n"
                    + "        INNER JOIN\n"
                    + "    vehiculo_tipo ON prg_tc.id_vehiculo_tipo = vehiculo_tipo.id_vehiculo_tipo\n"
                    + "        INNER JOIN\n"
                    + "    empleado_tipo_cargo ON empleado.id_empleado_cargo = empleado_tipo_cargo.id_empleado_tipo_cargo\n"
                    + "WHERE\n"
                    + "    fecha BETWEEN ?1 AND ?2\n"
                    + "        AND prg_tarea.sum_distancia = 1\n"
                    + "        AND empleado.certificado = 1\n"
                    + "        AND empleado.id_empleado_cargo = 28\n"
                    + "        AND vehiculo_tipo.id_vehiculo_tipo = 2\n"
                    + sql_unida_func
                    + "        AND prg_tc.estado_operacion not in (5,8,99)\n"
                    + "GROUP BY 1 , 3 , 4 , 5 , 6 , 7 , 8";

            Query query = em.createNativeQuery(sql, "InformeOperadoresMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha_ini)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fecha_fin)));
            query.setParameter(3, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<Empleado> findEmpleadosOperadores(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    e.id_empleado_estado = 1\n"
                    + sql_unida_func
                    + "        AND e.id_empleado_cargo IN (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_OPERADORES) + ")\n"
                    + "ORDER BY codigo_tm ASC;", Empleado.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Empleado> getEmpledosByIdArea(int idArea, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         e.id_gop_unidad_funcional = ?2 AND\n";
        String sql_area = idArea == 0 ? "" : "  c.id_param_area = ?1 AND\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    e.*\n"
                + "FROM\n"
                + "    empleado e\n"
                + "        INNER JOIN\n"
                + "    param_area_cargo c ON e.id_empleado_cargo = c.id_empleado_tipo_cargo\n"
                + "WHERE\n"
                + sql_unida_func
                + sql_area
                + "    e.id_empleado_estado = 1 AND\n"
                + "         e.estado_reg = 0", Empleado.class);
        q.setParameter(1, idArea);
        q.setParameter(2, idGopUnidadFuncional);
        return q.getResultList();

    }

    @Override
    public Empleado findByIdentificacion(String cIdentificacion) {
        try {
            String sql = "SELECT e.* "
                    + "FROM empleado e "
                    + "WHERE e.identificacion = ?1 "
                    + "AND e.id_empleado_estado = 1 "
                    + "AND e.estado_reg = 0";
            Query q = em.createNativeQuery(sql, Empleado.class);
            q.setParameter(1, cIdentificacion);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *
     * @param idCargo id cargo por el que se realizara la búsqueda
     * @param op 0 si es descendente y 1 si es ascendente
     * @return lista de empleados
     */
    @Override
    public List<Empleado> getEmpledosByIdCargoActivos(int idCargo, int op) {
        try {
            String order = op == 0 ? "DESC" : "ASC";
            String sql = "SELECT *\n"
                    + "FROM empleado \n"
                    + "WHERE id_empleado_cargo = ?1\n"
                    + "AND id_empleado_estado = 1\n"
                    + "AND estado_reg = 0 "
                    + "ORDER BY id_empleado " + order + ";";
            Query q = em.createNativeQuery(sql, Empleado.class);
            q.setParameter(1, idCargo);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna empleados con id_empleado_activo = 1 y estado_reg=0
     *
     * @param idGopUF
     * @return Objeto List Empleado, null en caso de error
     */
    @Override
    public List<Empleado> findAllEmpleadosActivos(int idGopUF) {
        try {
            String uf = idGopUF != 0 ? "AND id_gop_unidad_funcional = " + idGopUF + " " : " ";
            String sql = "SELECT * "
                    + "FROM empleado "
                    + "WHERE id_empleado_estado = 1 " + uf
                    + "AND estado_reg = 0 ORDER BY codigo_tm desc";
            Query q = em.createNativeQuery(sql, Empleado.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna empleados con id_empleado_activo = 1 y estado_reg=0
     *
     * @param idGopUnidadFunc
     * @param flag
     * @return Objeto List Empleado, null en caso de error
     */
    @Override
    public List<Empleado> findAllEmpleadosActivosByUnidadFunc(int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";

        try {
            String sql = "SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    e.id_empleado_estado = 1\n"
                    + "        AND e.estado_reg = 0\n"
                    + sql_unida_func
                    + "ORDER BY e.codigo_tm DESC";
            Query q = em.createNativeQuery(sql, Empleado.class);
            q.setParameter(1, idGopUnidadFunc);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna empleados
     *
     * @param codigoTM
     * @return Objeto Empleado, null en caso de error
     */
    @Override
    public Empleado findByCodigoTM(int codigoTM) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    empleado\n"
                    + "WHERE\n"
                    + "    codigo_tm = ?1 AND estado_reg = 0\n"
                    + "LIMIT 1;";
            Query q = em.createNativeQuery(sql, Empleado.class);
            q.setParameter(1, codigoTM);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int actualizarEstadoEmpleado(int idEmplado, int idEstadoEmpleado) {
        Query q = em.createNativeQuery("UPDATE empleado e set e.id_empleado_estado=?2 WHERE e.id_empleado=?1");
        q.setParameter(1, idEmplado);
        q.setParameter(2, idEstadoEmpleado);
        return q.executeUpdate();
    }

    /**
     * Retorna empleados todos los empleado con estado_reg=1 y filtrados por id
     * de cargo
     *
     * @param idCargos
     * @return Objeto List Empleado, null en caso de error
     */
    @Override
    public List<Empleado> findAllEmpleadosByCargos(String idCargos) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    empleado\n"
                    + "WHERE\n"
                    + "    id_empleado_cargo IN (" + idCargos + ")\n"
                    + "        AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, Empleado.class);
//            q.setParameter(1, idCargos);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Empleado> findEmpleadosByIdGopUnidadFuncional(String idCargos, int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            String sql_cargos = idCargos.isEmpty() ? "" : "        AND e.id_empleado_cargo IN (" + idCargos + ")\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    e.id_empleado_estado = 1\n"
                    + sql_unida_func
                    + sql_cargos
                    + "ORDER BY codigo_tm ASC;", Empleado.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<InformeLocalidadEmpleado> findInformeLocalidadEmpleado(Integer idEmpleadoCargo, int idGopUF) {
        try {
            String gopUF = idGopUF != 0 ? "AND id_gop_unidad_funcional = " + idGopUF + " " : " ";
            String cargo = idEmpleadoCargo != null ? "AND id_empleado_cargo = " + idEmpleadoCargo + " " : " ";
            String sql = "SELECT "
                    + "    IFNULL(localidad, 'Sin localidad') AS 'localidad', "
                    + "    COUNT(*) AS 'total' "
                    + "FROM "
                    + "    empleado "
                    + "WHERE "
                    + "    id_empleado_estado = 1 ";
            sql = sql + gopUF;
            sql = sql + cargo;
            sql = sql + "GROUP BY localidad "
                    + "ORDER BY 2 DESC";
            Query q = em.createNativeQuery(sql, "InformeLocalidadEmpleado");
            q.setParameter(1, sql);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Empleado> findAllByUnidadFuncacional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    e.*\n"
                + "FROM\n"
                + "    empleado e\n"
                + "WHERE\n"
                + "    e.id_empleado_estado = 1\n"
                + sql_unida_func
                + "ORDER BY apellidos ASC;", Empleado.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<Empleado> findAllConRetiradosByUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    e.*\n"
                + "FROM\n"
                + "    empleado e\n"
                + "WHERE\n"
                + "    e.id_empleado_estado in (1,2,3)\n"
                + sql_unida_func
                + "ORDER BY apellidos ASC;", Empleado.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public Empleado getEmpleadoCodigoTmAndUnidadFuncional(int i, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?2\n";
            Query q = em.createNativeQuery("SELECT * FROM empleado "
                    + "WHERE codigo_tm = ?1 "
                    + sql_unida_func
                    + "AND estado_reg = 0", Empleado.class)
                    .setParameter(1, i)
                    .setParameter(2, idGopUnidadFuncional);
            return (Empleado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Empleado> findEmpleadosSinGrupoByUnidadFuncional(int id, int id2, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?3\n";
        Query q = null;
        if (id2 == 0) {
            q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    NOT EXISTS( SELECT \n"
                    + "            pm.*\n"
                    + "        FROM\n"
                    + "            pm_grupo pm\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = pm.id_empleado)\n"
                    + "        AND NOT EXISTS( SELECT \n"
                    + "            pmd.*\n"
                    + "        FROM\n"
                    + "            pm_grupo_detalle pmd\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = pmd.id_empleado)\n"
                    + "        AND e.id_empleado_cargo = ?1\n"
                    + "        AND e.id_empleado_estado = 1"
                    + sql_unida_func, Empleado.class);
        } else {
            q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    empleado e\n"
                    + "WHERE\n"
                    + "    NOT EXISTS( SELECT \n"
                    + "            pm.*\n"
                    + "        FROM\n"
                    + "            pm_grupo pm\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = pm.id_empleado)\n"
                    + "        AND NOT EXISTS( SELECT \n"
                    + "            pmd.*\n"
                    + "        FROM\n"
                    + "            pm_grupo_detalle pmd\n"
                    + "        WHERE\n"
                    + "            e.id_empleado = pmd.id_empleado)\n"
                    + "        AND e.id_empleado_cargo IN (?1 , ?2)\n"
                    + "        AND e.id_empleado_estado = 1"
                    + sql_unida_func, Empleado.class
            );
        }
        q.setParameter(1, id);
        q.setParameter(2, id2);
        q.setParameter(3, idGopUnidadFuncional);

        return q.getResultList();
    }

    @Override
    public Long obtenerCantidadOperadoresByUfAndCargo(Integer idGopUnidadFuncional, Integer idEmpleadoTipoCargo, boolean flag) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            String sql_codigo_tm = !flag ? "" : "        AND codigo_tm is not null\n";
            String sql = "SELECT \n"
                    + "    count(*)\n"
                    + "FROM\n"
                    + "    empleado\n"
                    + "WHERE\n"
                    + "    id_empleado_cargo = ?2\n"
                    + "        AND id_empleado_estado = 1\n"
                    + sql_unida_func
                    + sql_codigo_tm
                    + "        AND estado_reg = 0;";

            Query q = em.createNativeQuery(sql);
            q.setParameter(1, idGopUnidadFuncional);
            q.setParameter(2, idEmpleadoTipoCargo);
            return (Long) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return Long.valueOf(0);
        }
    }

    @Override
    public List<PlantaObz> obtenerInformePlantaObz(int idGopUnidadFuncional, Date desde, Date hasta) {
        Boolean filter = MovilidadUtil.fechaHoy().compareTo(desde) == 0 && MovilidadUtil.fechaHoy().compareTo(hasta) == 0 ? false : true;

        String sql_date_range1 = filter ? " AND fecha_acc between ?3 and ?4\n" : "";
        String sql_date_range2 = filter ? " AND fecha between ?3 and ?4\n" : "";
        String sql_date_range3 = filter ? " AND creado between ?3 and ?4\n" : "";

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";

        String sql = "SELECT \n"
                + "    e.id_empleado,\n"
                + "    uf.codigo AS codigo_empresa,\n"
                + "    e.identificacion,\n"
                + "    e.nombres,\n"
                + "    e.apellidos,\n"
                + " CONCAT(e.nombres,' ',e.apellidos) as nombrecompleto,\n"
                + "    DATE_FORMAT(e.fecha_ingreso, '%Y-%m-%d') AS fecha_contrato,\n"
                + "    etc.nombre_cargo AS descripcion_cargo,\n"
                + "    e.codigo_tm,\n"
                + "    (SELECT \n"
                + "            COUNT(acc.id_accidente)\n"
                + "        FROM\n"
                + "            accidente acc\n"
                + "        WHERE\n"
                + "            id_empleado = e.id_empleado\n"
                + "                AND estado_reg = 0"
                + sql_date_range1
                + ") AS accidentes,\n"
                + "    (SELECT \n"
                + "            IFNULL(ROUND(((km.comercial + km.hlp_prg + km.adicionales + km.vaccom + km.vac) / 1000),\n"
                + "                                0),\n"
                + "                        0)\n"
                + "        FROM\n"
                + "            (SELECT \n"
                + "                SUM(IF((estado_operacion = 0\n"
                + "                        OR estado_operacion = 1\n"
                + "                        OR estado_operacion = 2)\n"
                + "                        AND prg_tarea.id_prg_tarea <> 4, distance, 0)) AS comercial,\n"
                + "                    SUM(IF(estado_operacion = 0\n"
                + "                        AND prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_prg,\n"
                + "                    SUM(IF(estado_operacion = 3\n"
                + "                        OR estado_operacion = 4, distance, 0)) AS adicionales,\n"
                + "                    SUM(IF(estado_operacion = 6, distance, 0)) AS vaccom,\n"
                + "                    SUM(IF(estado_operacion = 5\n"
                + "                        AND prg_tarea.id_prg_tarea <> 4, distance, 0)) AS comercial_Eliminados,\n"
                + "                    SUM(IF(estado_operacion = 5\n"
                + "                        AND prg_tarea.id_prg_tarea = 4, distance, 0)) AS hlp_Eliminados,\n"
                + "                    SUM(IF(estado_operacion = 7, distance, 0)) AS vac\n"
                + "            FROM\n"
                + "                prg_tc\n"
                + "            INNER JOIN empleado ON prg_tc.id_empleado = empleado.id_empleado\n"
                + "            INNER JOIN prg_tarea ON prg_tc.id_task_type = prg_tarea.id_prg_tarea\n"
                + "            WHERE\n"
                + "                prg_tc.id_empleado = e.id_empleado\n"
                + sql_date_range2
                + "            GROUP BY empleado.codigo_tm , empleado.nombres , empleado.apellidos\n"
                + "            ORDER BY empleado.nombres ASC) km) AS km_recorrido,\n"
                + "    (SELECT \n"
                + "    IFNULL((\n"
                + "        SELECT \n"
                + "            COALESCE(CASE \n"
                + "                WHEN fecha_radicado IS NULL THEN 0 \n"
                + "                WHEN DATE_ADD(fecha_radicado, INTERVAL 1 YEAR) < ?3 THEN 0 \n"
                + "                WHEN YEAR(DATE_ADD(fecha_radicado, INTERVAL 1 YEAR)) = YEAR(?3) AND MONTH(DATE_ADD(fecha_radicado, INTERVAL 1 YEAR)) = MONTH(?3) THEN 0 \n"
                + "                ELSE 1 \n"
                + "            END, 0)\n"
                + "        FROM (\n"
                + "            SELECT MAX(fecha_radicado) AS fecha_radicado\n"
                + "            FROM empleado_documentos\n"
                + "            WHERE id_empleado = e.id_empleado\n"
                + "            AND id_empleado_tipo_documento IN (8, 9)\n"
                + "            AND activo = 1\n"
                + "            AND estado_reg = 0\n"
                + "        ) AS max_fecha\n"
                + "        WHERE max_fecha.fecha_radicado IS NOT NULL\n"
                + "    ), 2)) as capacitacion,\n"
                + "(SELECT count(*) from novedad where estado_reg = 0  and e.id_empleado = id_empleado and id_novedad_tipo_detalle in (99,100,101)\n"
                + sql_date_range2
                + ") as infracciones, \n"
                + "(SELECT count(*) from novedad where id_pqr >0 and e.id_empleado = id_empleado "
                + sql_date_range2
                + ") as pqr, '' as horas_programadas, '' as horas_reales \n"
                + "FROM\n"
                + "    empleado e\n"
                + "        INNER JOIN\n"
                + "    gop_unidad_funcional uf ON e.id_gop_unidad_funcional = uf.id_gop_unidad_funcional\n"
                + "        INNER JOIN\n"
                + "    empleado_tipo_cargo etc ON e.id_empleado_cargo = etc.id_empleado_tipo_cargo\n"
                + "WHERE\n"
                + "    etc.id_empleado_tipo_cargo IN (" + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CARGOS_OPERADORES) + ")"
                + "        AND e.id_empleado_estado = 1 \n"
                + sql_unida_func
                + "ORDER BY uf.codigo , e.apellidos\n";

        Query query = em.createNativeQuery(sql, "PlantaObzMapping");
        query.setParameter(1, idGopUnidadFuncional);
        query.setParameter(2, SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_CERTIFICADO_CAPACITACION));
        query.setParameter(3, Util.dateFormat(desde));
        query.setParameter(4, Util.dateFormat(hasta));
        return query.getResultList();
    }

    @Override
    public List<Empleado> findEmpleadosRelacionadosPd(int idMaestro) {

        String sql = "select * from empleado where estado_reg = 0 and id_empleado_estado = 1 and id_empleado in (SELECT distinct id_empleado\n"
                + "FROM novedad where id_novedad in(\n"
                + "select distinct id_novedad from pd_maestro_detalle where id_pd_maestro= ?1) \n"
                + "and estado_reg=0)";

        Query query = em.createNativeQuery(sql, Empleado.class);
        query.setParameter(1, idMaestro);
        return query.getResultList();
    }

    @Override
    public List<Empleado> findEmpleadosCapacitadores() {

        String sql = "SELECT * FROM empleado \n"
                + "WHERE id_empleado_cargo IN (13, 22, 1044, 1062,  93, 96, 1070) \n"
                + "AND id_empleado_estado = 1;";

        Query query = em.createNativeQuery(sql, Empleado.class);
        return query.getResultList();
    }
}
