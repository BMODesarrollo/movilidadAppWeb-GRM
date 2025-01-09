/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucSeguimiento;
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
public class AccNovedadInfraestrucSeguimientoFacade extends AbstractFacade<AccNovedadInfraestrucSeguimiento> implements AccNovedadInfraestrucSeguimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadInfraestrucSeguimientoFacade() {
        super(AccNovedadInfraestrucSeguimiento.class);
    }

    @Override
    public List<AccNovedadInfraestrucSeguimiento> findByNovedad(Integer idNovedad) {
        try {
            String sql = "SELECT * FROM acc_novedad_infraestruc_seguimiento where id_acc_novedad_infraestruc = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccNovedadInfraestrucSeguimiento.class);
            query.setParameter(1, idNovedad);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
