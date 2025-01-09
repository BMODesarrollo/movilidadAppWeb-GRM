/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmVrbonos;
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
 * @author solucionesit
 */
@Stateless
public class GenericaPmVrbonosFacade extends AbstractFacade<GenericaPmVrbonos> implements GenericaPmVrbonosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmVrbonosFacade() {
        super(GenericaPmVrbonos.class);
    }

    @Override
    public List<GenericaPmVrbonos> getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_vrbonos g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaPmVrbonos.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaPmVrbonos findByIdAreaAndFecha(int idArea, Date fecha, int idGenericaPmVrBono, int idEmpeladoTipoCargo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_vrbonos g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0\n"
                    + "        AND ?2 BETWEEN g.desde AND g.hasta\n"
                    + "        AND g.id_generica_pm_vrbonos <> ?3\n"
                    + "        AND g.id_empleado_tipo_cargo = ?4;", GenericaPmVrbonos.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGenericaPmVrBono);
            q.setParameter(4, idEmpeladoTipoCargo);
            return (GenericaPmVrbonos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
