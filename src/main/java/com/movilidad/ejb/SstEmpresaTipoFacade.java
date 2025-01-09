/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaTipo;
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
public class SstEmpresaTipoFacade extends AbstractFacade<SstEmpresaTipo> implements SstEmpresaTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEmpresaTipoFacade() {
        super(SstEmpresaTipo.class);
    }

    @Override
    public List<SstEmpresaTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstEmpresaTipo.findByEstadoReg", SstEmpresaTipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public SstEmpresaTipo findByNombre(String nombre, Integer idRegistro) {
         try {
            String sql = "SELECT * FROM sst_empresa_tipo where id_sst_empresa_tipo <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstEmpresaTipo.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (SstEmpresaTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
