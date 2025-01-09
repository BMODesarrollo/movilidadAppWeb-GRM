/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTcDet;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class PrgTcDetFacade extends AbstractFacade<PrgTcDet> implements PrgTcDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTcDetFacade() {
        super(PrgTcDet.class);
    }

    @Override
    public List<PrgTcDet> findByIdPrgTc(int id) {
        try {
            Query q = em.createNativeQuery("SELECT prgd.* FROM prg_tc_det prgd "
                    + "WHERE prgd.id_prg_tc= ?1;", PrgTcDet.class);
            q.setParameter(1, id);
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public int removeByDate(Date d) {
        try {
            String sql = "delete from prg_tc_det where id_prg_tc in (select id_prg_tc from prg_tc where fecha= ?1)";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

}
