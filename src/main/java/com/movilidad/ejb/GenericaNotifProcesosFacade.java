/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empleado;
import com.movilidad.model.GenericaNotifProcesos;
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
public class GenericaNotifProcesosFacade extends AbstractFacade<GenericaNotifProcesos> implements GenericaNotifProcesosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaNotifProcesosFacade() {
        super(GenericaNotifProcesos.class);
    }

    @Override
    public List<Empleado> getEmployeesEmail(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       WHERE id_gop_unidad_funcional = ?1\n";
            String sql = "select * from empleado"
                    + sql_unida_func
                    + ";";

            Query query = em.createNativeQuery(sql, Empleado.class);
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Empleado findEmpleadoByEmail(String email) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM empleado "
                    //                    + "WHERE email_corporativo = ?1 LIMIT 1;", Empleado.class)
                    + "where email_corporativo like '%" + email + "%' limit 1;", Empleado.class);
//                    .setParameter(1, email);
            return (Empleado) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaNotifProcesos> findAll(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?1\n";
            String sql = "select * from generica_notif_procesos where estado_reg = 0"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, GenericaNotifProcesos.class);
            query.setParameter(1, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
