/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkDiarioDet;
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
public class ChkDiarioDetFacade extends AbstractFacade<ChkDiarioDet> implements ChkDiarioDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChkDiarioDetFacade() {
        super(ChkDiarioDet.class);
    }

    @Override
    public List<ChkDiarioDet> findByIdChkDiario(Integer idChkDiario) {
        try {
            String sql = "SELECT * FROM chk_diario_det where id_chk_diario = ?1 order by id_chk_componente;";

            Query query = em.createNativeQuery(sql, ChkDiarioDet.class);
            query.setParameter(1, idChkDiario);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
