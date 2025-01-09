/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabinaTipo;
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
public class AseoCabinaTipoFacade extends AbstractFacade<AseoCabinaTipo> implements AseoCabinaTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoCabinaTipoFacade() {
        super(AseoCabinaTipo.class);
    }

    @Override
    public AseoCabinaTipo findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM aseo_cabina_tipo where id_aseo_cabina_tipo <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AseoCabinaTipo.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (AseoCabinaTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AseoCabinaTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AseoCabinaTipo.findByEstadoReg", AseoCabinaTipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
