/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadMttoTipoDet;
import com.movilidad.model.NovedadTipoDetallesInfrastruc;
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
public class NovedadMttoTipoDetFacade extends AbstractFacade<NovedadMttoTipoDet> implements NovedadMttoTipoDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadMttoTipoDetFacade() {
        super(NovedadMttoTipoDet.class);
    }

    @Override
    public NovedadMttoTipoDet findByNombre(String nombre, Integer idRegistro,
            Integer idNovedadMttoTipo) {
        try {
            String sql = "SELECT * FROM novedad_mtto_tipo_det where nombre = ?1 "
                    + "and id_novedad_mtto_tipo_det <> ?2 and "
                    + "id_novedad_mtto_tipo = ?3 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, NovedadMttoTipoDet.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);
            query.setParameter(3, idNovedadMttoTipo);
            return (NovedadMttoTipoDet) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<NovedadMttoTipoDet> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("NovedadMttoTipoDet.findByEstadoReg", NovedadMttoTipoDet.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {

            return new ArrayList<>();
        }
    }

}
