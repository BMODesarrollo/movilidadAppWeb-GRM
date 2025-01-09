/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgCompania;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edgar Turizo
 */
@Stateless
public class PrgCompaniaFacade extends AbstractFacade<PrgCompania> implements PrgCompaniaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgCompaniaFacade() {
        super(PrgCompania.class);
    }

    @Override
    public List<PrgCompania> findallEst() {
        try {
            Query q = em.createQuery("SELECT p FROM PrgCompania p WHERE p.estadoReg = :estadoReg", PrgCompania.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgCompania> findCampo(String campo, String value, int id) {
        try {
            Query q = em.createNativeQuery("select * from prg_compania  WHERE " + campo + " = '" + value + "' AND NOT id_prg_compania = " + id + "");
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en Prg Compañía");
            return null;
        }
    }

}
