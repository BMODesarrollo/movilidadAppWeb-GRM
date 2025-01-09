/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadVacaciones;
import com.movilidad.utils.Util;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class NovedadVacacionesFacade extends AbstractFacade<NovedadVacaciones> implements NovedadVacacionesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadVacacionesFacade() {
        super(NovedadVacaciones.class);
    }

    @Override
    public NovedadVacaciones findByDateRange(Date fechaDesde, Date fechaHasta, String ccColaborador) {
        try {
            String sql = "SELECT * FROM novedad_vacaciones where fecha_inicio = ?1 and fecha_fin = ?2 and cc_colaborador = ?3 and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, NovedadVacaciones.class);
            query.setParameter(1, Util.dateFormat(fechaDesde));
            query.setParameter(2, Util.dateFormat(fechaHasta));
            query.setParameter(3, ccColaborador);

            return (NovedadVacaciones) query.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
