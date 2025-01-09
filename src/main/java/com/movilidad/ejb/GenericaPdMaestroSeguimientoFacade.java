/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPdMaestroSeguimiento;
import com.movilidad.model.PdMaestroSeguimiento;
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
public class GenericaPdMaestroSeguimientoFacade extends AbstractFacade<GenericaPdMaestroSeguimiento> implements GenericaPdMaestroSeguimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPdMaestroSeguimientoFacade() {
        super(GenericaPdMaestroSeguimiento.class);
    }

    @Override
    public List<GenericaPdMaestroSeguimiento> findByIdProceso(Integer idProceso) {
        try {
            String sql = "SELECT * FROM generica_pd_maestro_seguimiento where id_generica_pd_maestro = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, GenericaPdMaestroSeguimiento.class);
            query.setParameter(1, idProceso);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
