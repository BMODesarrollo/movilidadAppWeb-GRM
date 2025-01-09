/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresaVisitante;
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
public class SstEmpresaVisitanteFacade extends AbstractFacade<SstEmpresaVisitante> implements SstEmpresaVisitanteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEmpresaVisitanteFacade() {
        super(SstEmpresaVisitante.class);
    }

    @Override
    public List<SstEmpresaVisitante> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstEmpresaVisitante.findByEstadoReg", SstEmpresaVisitante.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SstEmpresaVisitante findByNumDocumento(String numDocumento) {
        try {
            Query query = em.createNamedQuery("SstEmpresaVisitante.findByNumeroDocumento", SstEmpresaVisitante.class);
            query.setParameter("numeroDocumento", numDocumento);

            return (SstEmpresaVisitante) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstEmpresaVisitante> findAllAprobados() {
        try {
            Query query = em.createNamedQuery("SstEmpresaVisitante.findByVisitanteAprobado", SstEmpresaVisitante.class);
            query.setParameter("visitanteAprobado", 1);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SstEmpresaVisitante> findAllByEmpresa(Integer idEmpresa) {
        try {
            String sql = "SELECT * FROM sst_empresa_visitante where id_sst_empresa = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstEmpresaVisitante.class);
            query.setParameter(1, idEmpresa);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<SstEmpresaVisitante> findAllAprobadosByEmpresa(Integer idSstEmpresa) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_empresa_visitante "
                    + "WHERE visitante_aprobado = 1 "
                    + "AND id_sst_empresa = ?1 "
                    + "AND estado_reg = 0";
            Query query = em.createNativeQuery(sql, SstEmpresaVisitante.class);
            query.setParameter(1, idSstEmpresa);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SstEmpresaVisitante findByHashString(String hashString) {
        try {
            String sql = "SELECT \n"
                    + "    sev.*\n"
                    + "FROM\n"
                    + "    sst_empresa_visitante sev\n"
                    + "WHERE\n"
                    + "    sev.hash_string = ?1\n"
                    + "    AND sev.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstEmpresaVisitante.class);
            query.setParameter(1, hashString);
            return (SstEmpresaVisitante) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
