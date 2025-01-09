/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmPuntosEmpresa;
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
public class GenericaPmPuntosEmpresaFacade extends AbstractFacade<GenericaPmPuntosEmpresa> implements GenericaPmPuntosEmpresaFacadeLocal {
    
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public GenericaPmPuntosEmpresaFacade() {
        super(GenericaPmPuntosEmpresa.class);
    }
    
    @Override
    public List<GenericaPmPuntosEmpresa> getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_puntos_empresa g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaPmPuntosEmpresa.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public GenericaPmPuntosEmpresa findByIdAreaAndFecha(int idArea, Date fecha, int idGenericaPmPuntosEmpresa) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_puntos_empresa g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0\n"
                    + "        AND ?2 BETWEEN g.desde AND g.hasta AND g.id_generica_pm_puntos_empresa<> ?3;", GenericaPmPuntosEmpresa.class);
            q.setParameter(1, idArea);
            q.setParameter(2, Util.dateFormat(fecha));
            q.setParameter(3, idGenericaPmPuntosEmpresa);
            return (GenericaPmPuntosEmpresa) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
