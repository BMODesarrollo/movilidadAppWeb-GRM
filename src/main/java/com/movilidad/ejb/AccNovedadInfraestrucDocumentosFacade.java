/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucDocumentos;
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
public class AccNovedadInfraestrucDocumentosFacade extends AbstractFacade<AccNovedadInfraestrucDocumentos> implements AccNovedadInfraestrucDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadInfraestrucDocumentosFacade() {
        super(AccNovedadInfraestrucDocumentos.class);
    }

    @Override
    public List<AccNovedadInfraestrucDocumentos> findByNovedad(Integer idNovedad) {
        try {
            String sql = "SELECT * FROM acc_novedad_infraestruc_documentos where id_acc_novedad_infraestruc = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccNovedadInfraestrucDocumentos.class);
            query.setParameter(1, idNovedad);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
