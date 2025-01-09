/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaTipo;
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

public class GenericaJornadaTipoFacade extends AbstractFacade<GenericaJornadaTipo> implements GenericaJornadaTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaTipoFacade() {
        super(GenericaJornadaTipo.class);
    }

    @Override
    public List<GenericaJornadaTipo> findAllByArea(int idArea) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_tipo g\n"
                    + "WHERE\n"
                    + "    g.estado_reg = 0 AND g.id_param_area=?1;", GenericaJornadaTipo.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaJornadaTipo findByDescripcion(String valor, int idJornadaT, int idArea) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_tipo g\n"
                    + "WHERE\n"
                    + "g.id_generica_jornada_tipo<>?2 AND\n"
                    + "g.id_param_area=?3 AND\n"
                    + "    g.estado_reg = 0 AND g.descripcion=?1;", GenericaJornadaTipo.class);
            q.setParameter(1, valor);
            q.setParameter(2, idJornadaT);
            q.setParameter(3, idArea);
            return (GenericaJornadaTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public GenericaJornadaTipo findBySercon(String valor, int idArea) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_tipo g\n"
                    + "WHERE\n"
                    + "g.id_param_area=?2 AND\n"
                    + "    g.estado_reg = 0 AND g.descripcion=?1;", GenericaJornadaTipo.class);
            q.setParameter(1, valor);
            q.setParameter(2, idArea);
            return (GenericaJornadaTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public GenericaJornadaTipo findByHIniAndHFin(String horaInicio, String horaFin, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_jornada_tipo g\n"
                    + "WHERE\n"
                    + "g.hini_t1=?1 AND\n"
                    + "g.hfin_t1=?2 AND\n"
                    + "g.id_param_area=?3 AND\n"
                    + "g.estado_reg = 0 LIMIT 1;", GenericaJornadaTipo.class);
            q.setParameter(1, horaInicio);
            q.setParameter(2, horaFin);
            q.setParameter(3, idArea);
            return (GenericaJornadaTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
