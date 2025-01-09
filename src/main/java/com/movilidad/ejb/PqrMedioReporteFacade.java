/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMedioReporte;
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
public class PqrMedioReporteFacade extends AbstractFacade<PqrMedioReporte> implements PqrMedioReporteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrMedioReporteFacade() {
        super(PqrMedioReporte.class);
    }

    @Override
    public PqrMedioReporte verificarRegistro(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM pqr_medio_reporte where id_pqr_medio_reporte <> ?1 and nombre = ?2 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, PqrMedioReporte.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (PqrMedioReporte) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PqrMedioReporte> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PqrMedioReporte.findByEstadoReg", PqrMedioReporte.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
