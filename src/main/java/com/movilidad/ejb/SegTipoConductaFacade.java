/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SegTipoConducta;
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
public class SegTipoConductaFacade extends AbstractFacade<SegTipoConducta> implements SegTipoConductaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SegTipoConductaFacade() {
        super(SegTipoConducta.class);
    }

    @Override
    public List<SegTipoConducta> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_conducta\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre ASC;", SegTipoConducta.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SegTipoConducta findByNombre(String nombre, int idSegTipoConducta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    seg_tipo_conducta\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_seg_tipo_conducta<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", SegTipoConducta.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idSegTipoConducta);
            return (SegTipoConducta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
