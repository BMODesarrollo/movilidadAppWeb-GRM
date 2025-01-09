/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Tanqueo;
import com.movilidad.model.Users;
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
 * @author HP
 */
@Stateless
public class TanqueoFacade extends AbstractFacade<Tanqueo> implements TanqueoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TanqueoFacade() {
        super(Tanqueo.class);
    }

    @Override
    public List<Tanqueo> findTanqueoDates(Date ini, Date fin) {
        try {
            Query q = em.createNativeQuery("select * from tanqueo where tanqueo.fecha "
                    + "between ?1 and ?2 "
                    + "and tanqueo.estado_reg = 0 order by tanqueo.id_vehiculo asc;", Tanqueo.class);
            q.setParameter(1, Util.dateFormat(ini));
            q.setParameter(2, Util.dateFormat(fin));
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en --> tanqueo Facade finTanqueoDates");
            return null;
        }
    }

    @Override
    public Tanqueo findUltimoTanqueo(Integer i) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM tanqueo "
                    + "WHERE km_final = (SELECT MAX(km_final) FROM tanqueo WHERE id_vehiculo = ?1 AND estado_reg = 0) AND id_vehiculo = ?1 "
                    + "AND estado_reg = 0", Tanqueo.class);
            q.setParameter(1, i);
            return (Tanqueo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Tanqueo> findTanqueoIngresado(Date ingresado, Integer i) {
        try {
            Query q = em.createNativeQuery("select * from tanqueo where tanqueo.fecha = ?1 and id_vehiculo = ?2 and estado_reg = 0", Tanqueo.class);
            q.setParameter(1, Util.dateFormat(ingresado));
            q.setParameter(2, i);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
