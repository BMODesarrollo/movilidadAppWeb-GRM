/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadDocumentos;
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
public class GmoNovedadDocumentosFacade extends AbstractFacade<GmoNovedadDocumentos> implements GmoNovedadDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmoNovedadDocumentosFacade() {
        super(GmoNovedadDocumentos.class);
    }

    @Override
    public List<GmoNovedadDocumentos> findAllByIdNovedad(Integer idGmoNovedadInfra) {
        try {
            String sql = "SELECT * FROM gmo_novedad_documentos where id_gmo_novedad_infrastruc = ?1 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, GmoNovedadDocumentos.class);
            query.setParameter(1, idGmoNovedadInfra);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
