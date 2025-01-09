/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdTipoProceso;
import com.movilidad.model.PdTipoProceso;
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
public class PdTipoProcesoFacade extends AbstractFacade<PdTipoProceso> implements PdTipoProcesoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdTipoProcesoFacade() {
        super(PdTipoProceso.class);
    }

    @Override
    public PdTipoProceso findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM pd_tipo_proceso where id_pd_tipo_proceso <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PdTipoProceso.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (PdTipoProceso) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PdTipoProceso> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PdTipoProceso.findByEstadoReg", PdTipoProceso.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
