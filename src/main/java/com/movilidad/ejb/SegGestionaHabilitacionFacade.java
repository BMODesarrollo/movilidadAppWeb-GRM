/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegGestionaHabilitacion;
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
public class SegGestionaHabilitacionFacade extends AbstractFacade<SegGestionaHabilitacion> implements SegGestionaHabilitacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegGestionaHabilitacionFacade() {
        super(SegGestionaHabilitacion.class);
    }

    @Override
    public List<SegGestionaHabilitacion> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_gestiona_habilitacion\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegGestionaHabilitacion.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegGestionaHabilitacion findByNombre(String nombre, int idSegGestionaHabilitacion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_gestiona_habilitacion\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_gestiona_habilitacion<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegGestionaHabilitacion.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegGestionaHabilitacion);
            return (SegGestionaHabilitacion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
