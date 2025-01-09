/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaParam;
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
public class GenericaJornadaParamFacade extends AbstractFacade<GenericaJornadaParam> implements GenericaJornadaParamFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaParamFacade() {
        super(GenericaJornadaParam.class);
    }

    @Override
    public GenericaJornadaParam getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT g.* "
                    + "FROM "
                    + "generica_jornada_param g "
                    + "WHERE "
                    + "g.id_param_area= ?1", GenericaJornadaParam.class);
            q.setParameter(1, idArea);
            return (GenericaJornadaParam) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<GenericaJornadaParam> findAllByEstadoReg() {
        try {
            String sql = "select * from generica_jornada_param where estado_reg = 0";

            Query query = em.createNativeQuery(sql, GenericaJornadaParam.class);
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
