/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEqui;
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
public class SstMatEquiFacade extends AbstractFacade<SstMatEqui> implements SstMatEquiFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstMatEquiFacade() {
        super(SstMatEqui.class);
    }

    /**
     * Permite obtener la lista de objetos donde su atributo estado_reg sea
     * igual a 0 y identificador SstEmpresa corresponda
     *
     * @param idSstEmpresa identidicador unico objeto SstEmpresa
     * @return Lista de objetos SstMatEqui, null en caso de error
     */
    @Override
    public List<SstMatEqui> findAllEstadoReg(Integer idSstEmpresa) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_mat_equi "
                    + "WHERE id_sst_empresa = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, SstMatEqui.class);
            q.setParameter(1, idSstEmpresa);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<SstMatEqui> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstMatEqui.findByEstadoReg", SstMatEqui.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstMatEqui findByNombre(String nombreMaterial, Integer idSstEmpresa) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	sst_mat_equi \n"
                    + "where\n"
                    + "	id_sst_empresa = ?1\n"
                    + "	and nombre = ?2\n"
                    + "	and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, SstMatEqui.class);
            query.setParameter(1, idSstEmpresa);
            query.setParameter(2, nombreMaterial);

            return (SstMatEqui) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
