/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoContratista;
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
public class LavadoContratistaFacade extends AbstractFacade<LavadoContratista> implements LavadoContratistaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoContratistaFacade() {
        super(LavadoContratista.class);
    }

    @Override
    public LavadoContratista findByNombre(Integer idRegistro, String nombre, Date fechaDesde, Date fechaHasta) {
        try {
            String sql = "SELECT * FROM lavado_contratista "
                    + "where id_lavado_contratista <> ?1 "
                    + "and nombre = ?2 "
                    + "and ((?3 between desde and hasta\n"
                    + "        OR ?4 between desde and hasta)\n"
                    + "        OR (desde between ?3 and ?4\n"
                    + "        OR hasta between ?3 and ?4)) "
                    + "and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, LavadoContratista.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);
            query.setParameter(3, Util.dateFormat(fechaDesde));
            query.setParameter(4, Util.dateFormat(fechaHasta));

            return (LavadoContratista) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<LavadoContratista> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("LavadoContratista.findByEstadoReg", LavadoContratista.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<LavadoContratista> findAllActivos() {
        try {
            Query query = em.createNamedQuery("LavadoContratista.findByActivo", LavadoContratista.class);
            query.setParameter("activo", 1);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
