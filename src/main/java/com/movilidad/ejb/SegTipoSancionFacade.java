/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoSancion;
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
public class SegTipoSancionFacade extends AbstractFacade<SegTipoSancion> implements SegTipoSancionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegTipoSancionFacade() {
        super(SegTipoSancion.class);
    }
    @Override
    public List<SegTipoSancion> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_sancion\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegTipoSancion.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegTipoSancion findByNombre(String nombre, int idSegTipoSancion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_sancion\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_tipo_sancion<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegTipoSancion.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegTipoSancion);
            return (SegTipoSancion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
