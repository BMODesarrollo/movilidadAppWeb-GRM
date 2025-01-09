/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovRequerimiento;
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
public class GestorNovRequerimientoFacade extends AbstractFacade<GestorNovRequerimiento> implements GestorNovRequerimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovRequerimientoFacade() {
        super(GestorNovRequerimiento.class);
    }

    @Override
    public List<GestorNovRequerimiento> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("GestorNovRequerimiento.findByEstadoReg", GestorNovRequerimiento.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GestorNovRequerimiento findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    gestor_nov_requerimiento\n"
                    + "WHERE\n"
                    + "    id_gestor_nov_requerimiento <> ?1\n"
                    + "        AND nombre = ?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, GestorNovRequerimiento.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (GestorNovRequerimiento) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
