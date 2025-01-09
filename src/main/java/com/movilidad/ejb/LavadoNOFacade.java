/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoNO;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar Beltran
 */
@Stateless
public class LavadoNOFacade extends AbstractFacade<LavadoNO> implements LavadoNOFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoNOFacade() {
        super(LavadoNO.class);
    }

    @Override
    public List<LavadoNO> findAll() {
        try {
            String sql = "SELECT * FROM lavado_no WHERE estado_reg = 0 ORDER BY fecha_hora DESC;";

            Query query = em.createNativeQuery(sql, LavadoNO.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<LavadoNO> findByDateRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT * FROM lavado_no WHERE estado_reg = 0 AND fecha_hora between ?1 AND ?2 ORDER BY fecha_hora DESC;";
            Query query = em.createNativeQuery(sql, LavadoNO.class);
            query.setParameter(1, desde);
            query.setParameter(2, hasta);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
