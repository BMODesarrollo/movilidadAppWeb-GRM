/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegActividadHabilitacion;
import java.util.ArrayList;
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
public class SegActividadHabilitacionFacade extends AbstractFacade<SegActividadHabilitacion> implements SegActividadHabilitacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegActividadHabilitacionFacade() {
        super(SegActividadHabilitacion.class);
    }

    @Override
    public List<SegActividadHabilitacion> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_actividad_habilitacion\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegActividadHabilitacion.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegActividadHabilitacion findByNombre(String nombre, int idSegActividadHabilitacion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_actividad_habilitacion\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_actividad_habilitacion<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegActividadHabilitacion.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegActividadHabilitacion);
            return (SegActividadHabilitacion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
