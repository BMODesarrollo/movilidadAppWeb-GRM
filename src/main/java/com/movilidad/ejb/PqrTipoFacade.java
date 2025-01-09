/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrResponsable;
import com.movilidad.model.PqrTipo;
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
public class PqrTipoFacade extends AbstractFacade<PqrTipo> implements PqrTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrTipoFacade() {
        super(PqrTipo.class);
    }

    @Override
    public PqrTipo verificarRegistro(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM pqr_tipo where id_pqr_tipo <> ?1 and nombre = ?2 and estado_reg = 0 ORDER BY NOMBRE LIMIT 1;";

            Query query = em.createNativeQuery(sql, PqrTipo.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (PqrTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PqrTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PqrTipo.findByEstadoReg", PqrTipo.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
