/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaComponente;
import com.movilidad.model.DanoFlotaParamSeveridad;
import com.movilidad.model.DanoFlotaSolucionValor;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévalo
 */
@Stateless
public class DanoFlotaParamSeveridadFacade extends AbstractFacade<DanoFlotaParamSeveridad> implements DanoFlotaParamSeveridadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DanoFlotaParamSeveridadFacade() {
        super(DanoFlotaParamSeveridad.class);
    }

    @Override
    public List<DanoFlotaParamSeveridad> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    dano_flota_param_severidad p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", DanoFlotaParamSeveridad.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
