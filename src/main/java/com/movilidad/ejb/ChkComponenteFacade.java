/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkComponente;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class ChkComponenteFacade extends AbstractFacade<ChkComponente> implements ChkComponenteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChkComponenteFacade() {
        super(ChkComponente.class);
    }

    @Override
    public ChkComponente findByNombre(Integer idChkComponente, String nombre) {
        try {
            String sql = "SELECT * FROM chk_componente where nombre = ?1 and id_chk_componente <> ?2 and estado_reg = 0;";
            
            Query query = em.createNativeQuery(sql, ChkComponente.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idChkComponente);
            return (ChkComponente) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ChkComponente> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM chk_componente where estado_reg = 0;";
            
            Query query = em.createNativeQuery(sql, ChkComponente.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
