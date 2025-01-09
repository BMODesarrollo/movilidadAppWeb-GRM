/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTipoDetalles;
import com.movilidad.model.ParamAreaUsr;
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
public class GenericaTipoDetallesFacade extends AbstractFacade<GenericaTipoDetalles> implements GenericaTipoDetallesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTipoDetallesFacade() {
        super(GenericaTipoDetalles.class);
    }

    @Override
    public ParamAreaUsr findByUsername(String username) {
        try {
            String sql = "select usr.* \n"
                    + "from param_area_usr usr\n"
                    + "inner join users u on usr.id_param_usr = u.user_id \n"
                    + "where u.username = ?1;";

            Query query = em.createNativeQuery(sql, ParamAreaUsr.class);
            query.setParameter(1, username);
            return (ParamAreaUsr) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaTipoDetalles> findAllByArea(int area) {
        try {
            String sql = "select * from generica_tipo_detalles where id_param_area = ?1;";

            Query query = em.createNativeQuery(sql, GenericaTipoDetalles.class);
            query.setParameter(1, area);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaTipoDetalles> findByTipo(int idTipo) {
        try {
            String sql = "SELECT \n"
                    + "    gtd.*\n"
                    + "FROM\n"
                    + "    generica_tipo_detalles gtd\n"
                    + "WHERE\n"
                    + "    gtd.id_generica_tipo= ?1\n"
                    + "        AND gtd.estado_reg = 0\n"
                    + "        AND gtd.afecta_programacion = 0;";
            Query query = em.createNativeQuery(sql, GenericaTipoDetalles.class);
            query.setParameter(1, idTipo);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<GenericaTipoDetalles> obtenerDetallesActuales(Integer idParamArea, String idsListaDetalles) {
        try {

            String sql_detalles_arr = idsListaDetalles == null ? ""
                    : "  AND ntd.id_generica_tipo_detalle not in (" + idsListaDetalles + ")\n";
            String sql = "SELECT ntd.* "
                    + "FROM generica_tipo_detalles ntd "
                    + "WHERE ntd.id_param_area = ?1 "
                    + sql_detalles_arr
                    + "AND ntd.estado_reg = 0";
            Query query = em.createNativeQuery(sql, GenericaTipoDetalles.class);
            query.setParameter(1, idParamArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
