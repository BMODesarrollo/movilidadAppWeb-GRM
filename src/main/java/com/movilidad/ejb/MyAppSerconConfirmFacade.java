/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppSerconConfirm;
import com.movilidad.utils.Util;
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
public class MyAppSerconConfirmFacade extends AbstractFacade<MyAppSerconConfirm> implements MyAppSerconConfirmFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAppSerconConfirmFacade() {
        super(MyAppSerconConfirm.class);
    }

    @Override
    public List<MyAppSerconConfirm> findAllRangoFechas(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    sc.*\n"
                + "FROM\n"
                + "    my_app_sercon_confirm sc\n"
                + "WHERE\n"
                + "    DATE(sc.fecha) BETWEEN DATE(?1) AND DATE(?2)\n"
                + "        AND estado_reg = 0;", MyAppSerconConfirm.class);

        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    @Override
    public MyAppSerconConfirm findByIdEmpledao(Integer idEmpleado, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    m.*\n"
                    + "FROM\n"
                    + "    my_app_sercon_confirm m\n"
                    + "WHERE\n"
                    + "    m.id_empleado = ?1\n"
                    + "        AND m.estado_reg = 0\n"
                    + "        AND DATE(m.fecha) = DATE(?2) LIMIT 1", MyAppSerconConfirm.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fecha));
            return (MyAppSerconConfirm) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
