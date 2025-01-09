package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.ActividadCol;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar Beltrán
 */
@Stateless
public class ActividadColFacade extends AbstractFacade<ActividadCol> implements ActividadColFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ActividadColFacade(){
        super(ActividadCol.class);
    }

    @Override
    public List<ActividadCol> findAll() {
        try {
            String sql = "SELECT * FROM actividad_col WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadCol> findByFecha(int idNovedad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<ActividadCol> findAllByDateRangeAndArea(Date desde, Date hasta, int idInfraccionesParamArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "    DATE(fecha) BETWEEN ?1 AND ?2\n"
                    + "        AND id_param_area = ?3"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idInfraccionesParamArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadCol> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "    DATE(fecha_identificacion) BETWEEN ?1 AND ?2\n"
                    + "        AND estado_reg = ?3 ;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, estado);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadCol> findAllByFechaRange(Date desde, Date hasta) {
       try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_ini) AND DATE(fecha_ini) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2) OR\n"
                    + "     (?1 <= DATE(fecha_fin) AND DATE(fecha_fin) > ?2)) AND \n"
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ActividadCol findActivity(ActividadCol actividad) {
        StringBuilder consulta = new StringBuilder("SELECT * FROM actividad_col WHERE estado_reg = 0");

        if (actividad.getFechaIni() != null && actividad.getFechaFin() != null) {
            consulta.append(" AND Date(fecha) between'").append(new java.sql.Timestamp(actividad.getFechaIni().getTime())).
                    append("' AND '").append(new java.sql.Timestamp(actividad.getFechaFin().getTime())).append("'");
        }

//        if (actividad.getHoraIni() != null && !actividad.getHoraIni().isEmpty()) {
//            consulta.append(" AND hora = '").append(actividad.getHora()).append("'");
//        }

        if (actividad.getDescripcion() != null && !actividad.getDescripcion().isEmpty()) {
            consulta.append(" AND descripcion LIKE '%").append(actividad.getDescripcion()).append("%'");
        }

        if (actividad.getEstado() != null && !actividad.getEstado().isEmpty()) {
            consulta.append(" AND estado = '").append(actividad.getEstado()).append("'");
        }

        try {
            Query query = em.createNativeQuery(consulta.toString(), ActividadCol.class);
            return (ActividadCol) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<ActividadCol> findActivityEmp(Date fechaIni, Date fechaFin, Integer idEmpleado) {
        try {
        String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "    DATE(fecha_ini) >= ?1 AND "
                    + "    DATE(fecha_fin) <= ?2 "
                    + "        AND id_empleado = ?3 ";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idEmpleado);

            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public ActividadCol findActivity(Date fechaIni, Date fechaFin, String horaIni, String horaFin, String descripcion) {
        try {
        String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "    DATE(fecha_ini) = ?1 AND "
                    + "    DATE(fecha_fin) = ?2 "
                    + "        AND hora_ini = ?3 "
                    + "        AND hora_fin = ?4 "
                    + "        AND descripcion = ?5;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, horaIni);
            query.setParameter(4, horaFin);
            query.setParameter(5, descripcion);

            return (ActividadCol)query.getSingleResult();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ActividadCol> findByEmpleado(int idEmpleado) {
        try {
        String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "        id_empleado = ?1 "
                    + "        AND estado_reg = 0 ;";
            Query query = em.createNativeQuery(sql, ActividadCol.class);
            query.setParameter(1, idEmpleado);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<ActividadCol> findAllByGropUnidadFun(int idGopUnidadFuncional) {
        try {
            
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery( "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    actividad_col\n"
                    + "WHERE\n"
                    + "        estado_reg = 0 "
                    + sql_unida_func, ActividadCol.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

}
