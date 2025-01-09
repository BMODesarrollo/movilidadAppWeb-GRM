/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadInfrastrucRutaAfectada;
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
public class GmoNovedadInfrastrucRutaAfectadaFacade extends AbstractFacade<GmoNovedadInfrastrucRutaAfectada> implements GmoNovedadInfrastrucRutaAfectadaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmoNovedadInfrastrucRutaAfectadaFacade() {
        super(GmoNovedadInfrastrucRutaAfectada.class);
    }

    @Override
    public List<GmoNovedadInfrastrucRutaAfectada> findAllByIdNovedad(Integer idGmoNovedadInfra) {
        try {
            String sql = "SELECT * FROM gmo_novedad_infrastruc_ruta_afectada where id_gmo_novedad_infrastruc = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, GmoNovedadInfrastrucRutaAfectada.class);
            query.setParameter(1, idGmoNovedadInfra);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
