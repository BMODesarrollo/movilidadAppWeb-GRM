/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccidenteLugarDemar;
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
public class AccidenteLugarDemarFacade extends AbstractFacade<AccidenteLugarDemar> implements AccidenteLugarDemarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccidenteLugarDemarFacade() {
        super(AccidenteLugarDemar.class);
    }

    @Override
    public List<AccidenteLugarDemar> objetosSelect(int i_id_Accidente) {
        try {
            Query q = em.createNativeQuery("SELECT * FROM accidente_lugar_demar "
                    + "WHERE id_accidente_lugar = " + i_id_Accidente + " ;", AccidenteLugarDemar.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en objetosSelect AccidenteLugarDemar");
            return null;
        }
    }

    @Override
    public AccidenteLugarDemar findByAccLugarAndViaDemar(int i_idAccidenteLugar, int i_idAccViaDemarcacion) {
        try {
            String sql = "SELECT * FROM accidente_lugar_demar "
                    + "WHERE id_accidente_lugar = ?1 "
                    + "AND id_acc_via_demarcacion = ?2";
            Query q = em.createNativeQuery(sql, AccidenteLugarDemar.class);
            q.setParameter(1, i_idAccidenteLugar);
            q.setParameter(2, i_idAccViaDemarcacion);
            List resultList = q.getResultList();
            if (resultList != null && !resultList.isEmpty()) {
                return (AccidenteLugarDemar) resultList.get(0);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error en eliminar AccidenteLugarDemar Facade");
            return null;
        }
    }

    @Override
    public List<AccidenteLugarDemar> getAccidenteLugarDemarInformeOpe(int idAccInformeOpe) {
        try {
            Query q = em.createNativeQuery("select * from accidente_lugar_demar where id_acc_informe_ope = ?1", AccidenteLugarDemar.class);
            q.setParameter(1, idAccInformeOpe);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
