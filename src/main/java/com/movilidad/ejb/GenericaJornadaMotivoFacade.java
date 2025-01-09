/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaMotivo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class GenericaJornadaMotivoFacade extends AbstractFacade<GenericaJornadaMotivo> implements GenericaJornadaMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaMotivoFacade() {
        super(GenericaJornadaMotivo.class);
    }

    @Override
    public List<GenericaJornadaMotivo> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_motivo g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaJornadaMotivo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public GenericaJornadaMotivo findByName(String name, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_motivo g\n"
                    + "WHERE\n"
                    + "    g.descripcion = ?1 AND"
                    + "    g.id_param_area = ?2 AND estado_reg = 0;", GenericaJornadaMotivo.class);
            q.setParameter(1, name);
            q.setParameter(2, idArea);
            return (GenericaJornadaMotivo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
