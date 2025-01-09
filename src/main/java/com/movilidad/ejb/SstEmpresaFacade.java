/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstEmpresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class SstEmpresaFacade extends AbstractFacade<SstEmpresa> implements SstEmpresaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstEmpresaFacade() {
        super(SstEmpresa.class);
    }

    @Override
    public List<SstEmpresa> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstEmpresa.findByEstadoReg", SstEmpresa.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public SstEmpresa findByRazonSocial(String razon) {
        try {
            Query query = em.createNamedQuery("SstEmpresa.findByRazonSocial", SstEmpresa.class);
            query.setParameter("razonSocial", razon);

            return (SstEmpresa) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstEmpresa findByNitCedula(String nit) {
        try {
            Query query = em.createNamedQuery("SstEmpresa.findByNitCedula", SstEmpresa.class);
            query.setParameter("nitCedula", nit);

            return (SstEmpresa) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstEmpresa login(String emailEmpresa, String usrSst) {
        try {
            String sql = "select * from sst_empresa where email_responsable = ?1 and usr_nombre = ?2 and estado_reg=0;";
            Query query = em.createNativeQuery(sql, SstEmpresa.class);
            query.setParameter(1, emailEmpresa);
            query.setParameter(2, usrSst);

            return (SstEmpresa) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstEmpresa> findAllBySeguridad() {
        try {

            String sql = "select * from sst_empresa e\n"
                    + "inner join sst_empresa_tipo et on e.id_sst_empresa_tipo = et.id_sst_empresa_tipo\n"
                    + "where et.nombre like '%seguridad%';";
            Query query = em.createNativeQuery(sql, SstEmpresa.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
