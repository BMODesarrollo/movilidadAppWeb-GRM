/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTipoDocumentos;
import com.movilidad.model.ParamAreaUsr;
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
public class GenericaTipoDocumentosFacade extends AbstractFacade<GenericaTipoDocumentos> implements GenericaTipoDocumentosFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTipoDocumentosFacade() {
        super(GenericaTipoDocumentos.class);
    }

    @Override
    public List<GenericaTipoDocumentos> findByArea(int area) {
        try {
            String sql = "select * from generica_tipo_documentos where id_param_area = ?1;";
            
            Query query = em.createNativeQuery(sql, GenericaTipoDocumentos.class);
            query.setParameter(1, area);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ParamAreaUsr findByUsername(String username) {
        try {
            String sql = "select usr.* \n"
                    + "from param_area_usr usr\n"
                    + "inner join users u on usr.id_param_usr = u.user_id \n"
                    + "where u.username = ?1;";

            Query query = em.createNativeQuery(sql, ParamAreaUsr.class);
            query.setParameter(1, username);
            return (ParamAreaUsr) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
