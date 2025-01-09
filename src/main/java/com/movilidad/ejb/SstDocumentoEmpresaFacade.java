/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstDocumentoEmpresa;
import com.movilidad.model.SstDocumentoTercero;
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
public class SstDocumentoEmpresaFacade extends AbstractFacade<SstDocumentoEmpresa> implements SstDocumentoEmpresaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstDocumentoEmpresaFacade() {
        super(SstDocumentoEmpresa.class);
    }

    @Override
    public List<SstDocumentoEmpresa> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstDocumentoEmpresa.findByEstadoReg", SstDocumentoEmpresa.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstDocumentoEmpresa findByTipoDoc(String tipo) {
        try {
            Query query = em.createNamedQuery("SstDocumentoEmpresa.findByTipoDocumento", SstDocumentoEmpresa.class);
            query.setParameter("tipoDocumento", tipo);

            return (SstDocumentoEmpresa) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstDocumentoEmpresa> findAllTiposVigentes() {
        try {
            String sql = "select * from sst_documento_empresa where estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstDocumentoEmpresa.class);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
