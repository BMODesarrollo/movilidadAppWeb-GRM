/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuVacaciones;
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
 * @author Omar.beltran
 */
@Stateless
public class PlaRecuVacacionesFacade extends AbstractFacade<PlaRecuVacaciones> implements PlaRecuVacacionesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuVacacionesFacade() {
        super(PlaRecuVacaciones.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PlaRecuVacaciones> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_vacaciones\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2)) AND\n"
                    + "        estado_reg = ?3;";
            Query query = em.createNativeQuery(sql, PlaRecuVacaciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, estadoReg);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuVacaciones> findAllByFechaRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_vacaciones\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2)) AND\n"
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuVacaciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public PlaRecuVacaciones findVacaciones(Date fechaIni, Date fechaFin, Integer idEmpleado) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_vacaciones\n"
                    + "WHERE\n"
                    + "    ((?1 BETWEEN DATE(fecha_inicio) AND DATE(fecha_fin)) OR\n"
                    + "     (?2 BETWEEN DATE(fecha_inicio) AND DATE(fecha_fin))) \n"
                    + "        AND id_empleado = ?3 \n"
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuVacaciones.class);
            query.setParameter(1, Util.dateFormat(fechaIni));
            query.setParameter(2, Util.dateFormat(fechaFin));
            query.setParameter(3, idEmpleado);

            // Usar getResultList para evitar excepciones si no hay resultados
            List<PlaRecuVacaciones> results = query.getResultList();
            if (results.isEmpty()) {
                System.out.println("No se encontraron registros que coincidan con los criterios especificados.");
                return null;
            }
            // Devuelve el primer resultado si es necesario uno solo
            return results.get(0);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PlaRecuVacaciones findByEmpleado(int idEmpleado) {
        try {
        String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_vacaciones\n"
                    + "WHERE\n"
                    + "     id_empleado = ?1 \n"
                    + "     AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuVacaciones.class);
            query.setParameter(1, idEmpleado);
            return query.getResultList().isEmpty() ? null : (PlaRecuVacaciones)query.getResultList().get(0);    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuVacaciones> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    pla_recu_vacaciones e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuVacaciones.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
