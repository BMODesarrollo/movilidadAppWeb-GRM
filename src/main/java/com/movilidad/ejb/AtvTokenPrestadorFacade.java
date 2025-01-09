/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTokenPrestador;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AtvTokenPrestadorFacade extends AbstractFacade<AtvTokenPrestador> implements AtvTokenPrestadorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvTokenPrestadorFacade() {
        super(AtvTokenPrestador.class);
    }

    @Override
    public AtvTokenPrestador findByTokenAndActivo(String token) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_token_prestador\n"
                    + "WHERE\n"
                    + "    token = ?1 AND activo = 1\n"
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, AtvTokenPrestador.class);
            q.setParameter(1, token.trim());
            return (AtvTokenPrestador) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
