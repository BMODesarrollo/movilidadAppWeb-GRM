/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoContratista;
import com.movilidad.model.LavadoCosto;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
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
public class LavadoCostoFacade extends AbstractFacade<LavadoCosto> implements LavadoCostoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoCostoFacade() {
        super(LavadoCosto.class);
    }

    @Override
    public LavadoCosto verificarRegistro(Date fechaDesde, Date fechaHasta, Integer idRegistro, Integer idTipoServicio, Integer idContratista) {
        try {
            String sql = "SELECT * FROM lavado_costo where id_lavado_costo <> ?1 "
                    + "and id_lavado_tipo_servicio = ?2 "
                    + "and id_lavado_contratista = ?3 "
                    + "and ( (?4 between desde and hasta or ?5 between desde and hasta )  "
                    + "or (desde between ?4 and ?5 or hasta between ?4 and ?5 ))  "
                    + "and estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, LavadoCosto.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, idTipoServicio);
            query.setParameter(3, idContratista);
            query.setParameter(4, Util.dateFormat(fechaDesde));
            query.setParameter(5, Util.dateFormat(fechaHasta));

            return (LavadoCosto) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LavadoCosto> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("LavadoCosto.findByEstadoReg", LavadoCosto.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
