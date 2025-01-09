/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoTipo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USUARIO
 */
@Stateless
public class VehiculoTipoFacade extends AbstractFacade<VehiculoTipo> implements VehiculoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoTipoFacade() {
        super(VehiculoTipo.class);
    }

    @Override
    public VehiculoTipo find(String field, String value) {
        try {
            Query q = getEntityManager().createNativeQuery("select * from vehiculo_tipo e"
                    + " where " + field + "=?1 limit 1", VehiculoTipo.class);
            q.setParameter(1, value);
            return (VehiculoTipo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VehiculoTipo> findAllEstadoR() {
        try {
            Query q = em.createQuery("SELECT v FROM VehiculoTipo v WHERE v.estadoReg = :estadoReg", VehiculoTipo.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
