/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaServerParamDet;
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
public class NominaServerParamDetFacade extends AbstractFacade<NominaServerParamDet> implements NominaServerParamDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NominaServerParamDetFacade() {
        super(NominaServerParamDet.class);
    }

    @Override
    public List<NominaServerParamDet> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NominaServerParamDet.findByEstadoReg", NominaServerParamDet.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public NominaServerParamDet findByIdNovedadTipoDetalle(Integer idNovedadTipoDetalle) {
        try {
            String sql = "SELECT * FROM nomina_server_param_det where id_novedad_tipo_detalle = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NominaServerParamDet.class);
            query.setParameter(1, idNovedadTipoDetalle);

            return (NominaServerParamDet) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
