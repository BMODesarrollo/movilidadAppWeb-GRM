/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoIncumplimiento;
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
public class SegTipoIncumplimientoFacade extends AbstractFacade<SegTipoIncumplimiento> implements SegTipoIncumplimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegTipoIncumplimientoFacade() {
        super(SegTipoIncumplimiento.class);
    }

    @Override
    public List<SegTipoIncumplimiento> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_incumplimiento\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegTipoIncumplimiento.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegTipoIncumplimiento findByNombre(String nombre, int idSegTipoIncumplimiento) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_incumplimiento\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_tipo_incumplimiento<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegTipoIncumplimiento.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegTipoIncumplimiento);
            return (SegTipoIncumplimiento) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
