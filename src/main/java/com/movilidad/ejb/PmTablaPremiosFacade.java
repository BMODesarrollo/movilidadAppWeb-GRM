/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

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
public class PmTablaPremiosFacade extends AbstractFacade<PmTablaPremios> implements PmTablaPremiosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmTablaPremiosFacade() {
        super(PmTablaPremios.class);
    }

    @Override
    public PmTablaPremios verificarFecha(Date fecha, Integer idRegistro, Integer puntoMin, Integer puntoMax) {
        String sql = "SELECT * FROM pm_tabla_premios "
                + "where id_pm_tabla_premios <> ?1 and "
                + "?2 between desde and hasta "
                + "and punto_min = ?3 and punto_max = ?4 "
                + "and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, PmTablaPremios.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, puntoMin);
            query.setParameter(4, puntoMax);

            return (PmTablaPremios) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PmTablaPremios> findAllByEstadoReg() {
        String sql = "SELECT * FROM pm_tabla_premios where estado_reg = 0;";

        try {
            Query query = em.createNativeQuery(sql, PmTablaPremios.class);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public PmTablaPremios verificarPosicion(Date fecha, Integer idRegistro, Integer pocision) {
        String sql = "SELECT * FROM pm_tabla_premios "
                + "where id_pm_tabla_premios <> ?1 and "
                + "?2 between desde and hasta "
                + "and posicion = ?3 "
                + "and estado_reg = 0 LIMIT 1;";

        try {
            Query query = em.createNativeQuery(sql, PmTablaPremios.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, pocision);

            return (PmTablaPremios) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
