/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvEvidencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AtvEvidenciaFacade extends AbstractFacade<AtvEvidencia> implements AtvEvidenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvEvidenciaFacade() {
        super(AtvEvidencia.class);
    }

    @Override
    public List<AtvEvidencia> findByIdNovedad(Integer idNovedad) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_evidencia\n"
                + "WHERE\n"
                + "    id_novedad = ?1 AND estado_reg = 0\n"
                + "ORDER BY TIME(creado) ASC";
        Query q = em.createNativeQuery(sql, AtvEvidencia.class);
        q.setParameter(1, idNovedad);
        return q.getResultList();
    }

}
