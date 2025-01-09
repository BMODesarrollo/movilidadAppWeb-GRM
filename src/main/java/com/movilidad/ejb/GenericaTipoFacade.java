/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTipo;
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
public class GenericaTipoFacade extends AbstractFacade<GenericaTipo> implements GenericaTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTipoFacade() {
        super(GenericaTipo.class);
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
    public List<GenericaTipo> findAllByArea(int usArea) {
        try {
            String sql = "select * from generica_tipo where id_param_area = ?1;";

            Query query = em.createNativeQuery(sql, GenericaTipo.class);
            query.setParameter(1, usArea);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaTipo> obtenerTipos() {
        Query query = em.createNativeQuery("SELECT * FROM generica_tipo where id_generica_tipo not in(3)", GenericaTipo.class);
        return query.getResultList();
    }

    @Override
    public List<GenericaTipo> findAllByAreaAfectaPm(int usArea, int opc) {
        String byFecha = "";
        if (opc == 1) {
            byFecha = "AND gtd.fechas=1 ";
        }
        try {
            String sql = "SELECT DISTINCT\n"
                    + "    (gt.id_generica_tipo),\n"
                    + "    gt.nombre_tipo_novedad,\n"
                    + "    gt.descripcion_tipo_novedad,\n"
                    + "    gt.username,\n"
                    + "    gt.estado_reg\n"
                    + "FROM\n"
                    + "    generica_tipo gt\n"
                    + "        INNER JOIN\n"
                    + "    generica_tipo_detalles gtd ON gt.id_generica_tipo = gtd.id_generica_tipo\n"
                    + "WHERE\n"
                    + "    gtd.afecta_pm = 1 " + byFecha + "\n"
                    + "        AND gt.id_param_area = ?1";

            Query query = em.createNativeQuery(sql, GenericaTipo.class);
            query.setParameter(1, usArea);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
