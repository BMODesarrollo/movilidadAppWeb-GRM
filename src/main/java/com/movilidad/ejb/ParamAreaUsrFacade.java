/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamAreaUsr;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class ParamAreaUsrFacade extends AbstractFacade<ParamAreaUsr> implements ParamAreaUsrFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamAreaUsrFacade() {
        super(ParamAreaUsr.class);
    }

    @Override
    public ParamAreaUsr getByIdUser(String username) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    param_area_usr p\n"
                    + "WHERE\n"
                    + "    p.id_param_usr = (SELECT \n"
                    + "            u.user_id\n"
                    + "        FROM\n"
                    + "            users u\n"
                    + "        WHERE\n"
                    + "            u.username = ?1)\n"
                    + "LIMIT 1;", ParamAreaUsr.class);
            q.setParameter(1, username);
            return (ParamAreaUsr) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }

    @Override
    public List<ParamAreaUsr> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM param_area_usr WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, ParamAreaUsr.class
            );
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

}
