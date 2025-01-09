/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoDiasFs;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class MttoDiasFsFacade extends AbstractFacade<MttoDiasFs> implements MttoDiasFsFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoDiasFsFacade() {
        super(MttoDiasFs.class);
    }

    /**
     * Obtener objeto List de MttoDiasFs por estadoReg = 0
     *
     * @return List MttoDiasFs
     */
    @Override
    public List<MttoDiasFs> findAllEstadoReg() {
        String sql = "SELECT * FROM mtto_dias_fs WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, MttoDiasFs.class);
        return q.getResultList();
    }

}
