/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopParamTiempoCierre;
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
public class GopParamTiempoCierreFacade extends AbstractFacade<GopParamTiempoCierre> implements GopParamTiempoCierreFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GopParamTiempoCierreFacade() {
        super(GopParamTiempoCierre.class);
    }

    @Override
    public List<GopParamTiempoCierre> findAllEstadoRegByUnidadFuncional(int idGopUnidaFunc) {
        String sql_unida_func = idGopUnidaFunc == 0 ? "" : "     g.id_gop_unidad_funcional = ?1 AND\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    g.*\n"
                + "FROM\n"
                + "    gop_param_tiempo_cierre g\n"
                + "WHERE\n"
                + sql_unida_func
                + "    g.estado_reg = 0;", GopParamTiempoCierre.class);
        q.setParameter(1, idGopUnidaFunc);
        return q.getResultList();

    }

    @Override
    public GopParamTiempoCierre findParam() {
        try {
            Query q = em.createNativeQuery("SELECT g.* FROM gop_param_tiempo_cierre g\n"
                    + "WHERE g.estado_reg = 0 LIMIT 1;", GopParamTiempoCierre.class);
            return (GopParamTiempoCierre) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
