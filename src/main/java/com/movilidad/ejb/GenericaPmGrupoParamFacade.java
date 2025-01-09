/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmGrupoParam;
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
public class GenericaPmGrupoParamFacade extends AbstractFacade<GenericaPmGrupoParam> implements GenericaPmGrupoParamFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmGrupoParamFacade() {
        super(GenericaPmGrupoParam.class);
    }

    @Override
    public List<GenericaPmGrupoParam> findAll(Integer idArea) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	generica_pm_grupo_param\n"
                    + "where\n"
                    + "	id_param_area = ?1\n"
                    + " AND estado_reg = 0\n";

            Query query = em.createNativeQuery(sql, GenericaPmGrupoParam.class);
            query.setParameter(1, idArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GenericaPmGrupoParam verificarRegistro(Integer idArea) {
        try {
            String sql = "select\n"
                    + "	*\n"
                    + "from\n"
                    + "	generica_pm_grupo_param\n"
                    + "where\n"
                    + "	id_param_area = ?1\n"
                    + "	and estado_reg = 0\n"
                    + "limit 1;";

            Query query = em.createNativeQuery(sql, GenericaPmGrupoParam.class);
            query.setParameter(1, idArea);
            return (GenericaPmGrupoParam) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<GenericaPmGrupoParam> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM generica_pm_grupo_param WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaPmGrupoParam.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    @Override
    public GenericaPmGrupoParam findByIdArea(int idArea) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_pm_grupo_param gpgp\n"
                    + "WHERE\n"
                    + "    gpgp.id_param_area = ?1\n"
                    + "        AND gpgp.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, GenericaPmGrupoParam.class);
            q.setParameter(1, idArea);
            return (GenericaPmGrupoParam) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
