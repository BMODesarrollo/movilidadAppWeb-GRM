/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmVrbonoGrupal;
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
public class GenericaPmVrbonoGrupalFacade extends AbstractFacade<GenericaPmVrbonoGrupal> implements GenericaPmVrbonoGrupalFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmVrbonoGrupalFacade() {
        super(GenericaPmVrbonoGrupal.class);
    }

    @Override
    public GenericaPmVrbonoGrupal verificarFecha(Date fecha, Integer idRegistro, Integer idArea) {
        String sql = "SELECT * FROM generica_pm_vrbono_grupal where id_generica_pm_vrbono_grupal <> ?1 and ?2 between desde and hasta and id_param_area = ?3 and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmVrbonoGrupal.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, idArea);

            return (GenericaPmVrbonoGrupal) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaPmVrbonoGrupal> findAllByEstadoRegAndArea(Integer idArea) {
        String sql = "SELECT * FROM generica_pm_vrbono_grupal where estado_reg = 0 and id_param_area = ?1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmVrbonoGrupal.class);
            query.setParameter(1, idArea);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
