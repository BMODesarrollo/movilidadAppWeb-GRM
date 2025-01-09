/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteCalificacion;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class AccidenteCalificacionFacade extends AbstractFacade<AccidenteCalificacion> implements AccidenteCalificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteCalificacionFacade() {
        super(AccidenteCalificacion.class);
    }

    @Override
    public boolean validateByPin(Date d, Integer iPin) {
        try {
            String dt = Util.dateFormat(d);
            String sql = "SELECT * "
                    + "FROM accidente_calificacion "
                    + "WHERE fecha_calificacion = ?1 "
                    + "AND pin_reunion = ?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, AccidenteCalificacion.class);
            q.setParameter(1, dt);
            q.setParameter(2, iPin);
            List resultList = q.getResultList();
            return !(resultList != null && resultList.isEmpty());
        } catch (Exception e) {
            System.out.println("Error en Facade AccidenteCalificacion " + e.getMessage());
            return true;
        }
    }

    @Override
    public boolean validateByAccidente(Integer idAccidente) {
        try {
            String sql = "SELECT * "
                    + "FROM accidente_calificacion "
                    + "WHERE id_accidente = ?1 "
                    + "AND calificado <> 0 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, AccidenteCalificacion.class);
            q.setParameter(1, idAccidente);
            List resultList = q.getResultList();
            return !(resultList != null && resultList.isEmpty());
        } catch (Exception e) {
            System.out.println("Error en Facade AccidenteCalificacion " + e.getMessage());
            return true;
        }
    }

    /**
     *
     * @param d fecha de validez del pin
     * @param iPin pin asignado a la reunion
     * @param op 1 para consultar todos los accidentes de la reunion, 2 para
     * consultar solo los accidentes que aun no han sido procesados, 3 para consultar los que 
     * casos que ya fueron procesadas sus causalidades
     * @return lista con los accidentes segun parametros de la consulta, null si
     * hay error.
     */
    @Override
    public List<AccidenteCalificacion> findByPin(Date d, Integer iPin, int op) {
        try {
            String dt = Util.dateFormat(d);
            String sql = "";
            if (op == 1) {
                sql = "SELECT * "
                        + "FROM accidente_calificacion "
                        + "WHERE fecha_calificacion = ?1 "
                        + "AND pin_reunion = ?2 "
                        + "AND estado_reg = 0";
            }
            if (op == 2) {
                sql = "SELECT * "
                        + "FROM accidente_calificacion "
                        + "WHERE fecha_calificacion = ?1 "
                        + "AND pin_reunion = ?2 "
                        + "AND calificado = 0 "
                        + "AND estado_reg = 0";
            }
            if (op == 3) {
                sql = "SELECT * "
                        + "FROM accidente_calificacion "
                        + "WHERE fecha_calificacion = ?1 "
                        + "AND pin_reunion = ?2 "
                        + "AND calificado = 2 "
                        + "AND estado_reg = 0";
            }
            Query q = em.createNativeQuery(sql, AccidenteCalificacion.class);
            q.setParameter(1, dt);
            q.setParameter(2, iPin);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Facade AccidenteCalificacion " + e.getMessage());
            return null;
        }
    }

}
