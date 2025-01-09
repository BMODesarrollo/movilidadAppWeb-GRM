/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrClasificacion;
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
public class PqrClasificacionFacade extends AbstractFacade<PqrClasificacion> implements PqrClasificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrClasificacionFacade() {
        super(PqrClasificacion.class);
    }

    @Override
    public PqrClasificacion verificarRegistro(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM pqr_clasificacion where id_pqr_clasificacion <> ?1 and nombre = ?2 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, PqrClasificacion.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (PqrClasificacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PqrClasificacion> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PqrClasificacion.findByEstadoReg", PqrClasificacion.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
