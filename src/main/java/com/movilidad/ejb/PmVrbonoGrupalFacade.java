/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmVrbonoGrupal;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class PmVrbonoGrupalFacade extends AbstractFacade<PmVrbonoGrupal> implements PmVrbonoGrupalFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmVrbonoGrupalFacade() {
        super(PmVrbonoGrupal.class);
    }

    @Override
    public PmVrbonoGrupal verificarFecha(Date fecha, Integer idRegistro) {
        String sql = "SELECT * FROM pm_vrbono_grupal where id_pm_vrbono_grupal <> ?1 and ?2 between desde and hasta and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, PmVrbonoGrupal.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));

            return (PmVrbonoGrupal) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PmVrbonoGrupal> findAllByEstadoReg() {
        String sql = "SELECT * FROM pm_vrbono_grupal where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, PmVrbonoGrupal.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
