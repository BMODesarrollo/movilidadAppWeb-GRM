/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppCambioTarea;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class MyAppCambioTareaFacade extends AbstractFacade<MyAppCambioTarea> implements MyAppCambioTareaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAppCambioTareaFacade() {
        super(MyAppCambioTarea.class);
    }

    @Override
    public List<MyAppCambioTarea> findAllEstadoReg() {
        String sql = "SELECT * FROM my_app_cambio_tarea WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, MyAppCambioTarea.class);
        return q.getResultList();
    }

    @Override
    public List<MyAppCambioTarea> findAllByEstadoRegAndIdGopUFAndFechas(Date desde, Date hasta, Integer idGopUF) {
        String sql = "SELECT "
                + "    mact.* "
                + "FROM "
                + "    my_app_cambio_tarea mact "
                + "        INNER JOIN "
                + "    empleado e ON mact.id_empleado = e.id_empleado "
                + "WHERE "
                + "    e.id_gop_unidad_funcional = ?3 "
                + "        AND DATE(mact.fecha_hora) BETWEEN DATE(?1) AND DATE(?2) "
                + "        AND mact.estado_reg = 0";
        Query q = em.createNativeQuery(sql, MyAppCambioTarea.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        q.setParameter(3, idGopUF);
        return q.getResultList();
    }

}
