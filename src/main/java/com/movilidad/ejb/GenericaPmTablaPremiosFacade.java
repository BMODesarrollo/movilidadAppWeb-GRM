/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmTablaPremios;
import com.movilidad.model.PmTablaPremios;
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
 * @author Carlos Ballestas
 */
@Stateless
public class GenericaPmTablaPremiosFacade extends AbstractFacade<GenericaPmTablaPremios> implements GenericaPmTablaPremiosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmTablaPremiosFacade() {
        super(GenericaPmTablaPremios.class);
    }

    @Override
    public GenericaPmTablaPremios verificarFecha(Date fecha, Integer idRegistro, Integer idArea, Integer puntoMin, Integer puntoMax) {
        String sql = "SELECT * FROM generica_pm_tabla_premios where id_generica_pm_tabla_premios <> ?1 "
                + "and ?2 between desde and hasta and id_param_area = ?3 "
                + "and punto_min = ?4 and punto_max = ?5 "
                + "and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmTablaPremios.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, idArea);
            query.setParameter(4, puntoMin);
            query.setParameter(5, puntoMax);

            return (GenericaPmTablaPremios) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaPmTablaPremios> findAllByEstadoReg(Integer idArea) {
        String sql = "SELECT * FROM generica_pm_tabla_premios where estado_reg = 0 and id_param_area = ?1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmTablaPremios.class);
            query.setParameter(1, idArea);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaPmTablaPremios verificarPosicion(Date fecha, Integer idRegistro, Integer idArea, Integer pocision) {
         String sql = "SELECT * FROM generica_pm_tabla_premios where id_generica_pm_tabla_premios <> ?1 "
                + "and ?2 between desde and hasta and id_param_area = ?3 "
                + "and posicion = ?4 "
                + "and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, GenericaPmTablaPremios.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, idArea);
            query.setParameter(4, pocision);

            return (GenericaPmTablaPremios) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
