/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppNovedadParam;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class MyAppNovedadParamFacade extends AbstractFacade<MyAppNovedadParam> implements MyAppNovedadParamFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAppNovedadParamFacade() {
        super(MyAppNovedadParam.class);
    }

    /**
     * Retorna los registros donde estado_reg = 0
     *
     * @return List<MyAppNovedadParam> estado_reg = 0
     */
    @Override
    public List<MyAppNovedadParam> findAllEstadoReg() {
        Query q = em.createNativeQuery("SELECT * "
                + "FROM my_app_novedad_param "
                + "WHERE estado_reg = 0", MyAppNovedadParam.class);
        return q.getResultList();
    }

    @Override
    public MyAppNovedadParam findByCodigoProceso(String codigo) {
        try {
            String sql = "SELECT * "
                    + "FROM my_app_novedad_param "
                    + "WHERE codigo_proceso = ?1 "
                    + "AND estado_reg = 0 "
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, MyAppNovedadParam.class);
            q.setParameter(1, codigo);
            return (MyAppNovedadParam) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
