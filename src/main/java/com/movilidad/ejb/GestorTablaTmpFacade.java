/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorTablaTmp;
import com.movilidad.model.ParamFeriado;
import com.movilidad.utils.Util;
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
public class GestorTablaTmpFacade extends AbstractFacade<GestorTablaTmp> implements GestorTablaTmpFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorTablaTmpFacade() {
        super(GestorTablaTmp.class);
    }

    @Override
    public List<GestorTablaTmp> findAllByIdEmpleadoCargo(Integer idEmpleadoCargo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    gestor_tabla_tmp\n"
                    + "WHERE\n"
                    + "    id_empleado_tipo_cargo = ?1\n"
                    + sql_unida_func;
            Query q = em.createNativeQuery(sql, GestorTablaTmp.class);
            q.setParameter(1, idEmpleadoCargo);
            q.setParameter(2, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GestorTablaTmp> findAllByUf(Integer idEmpleadoCargo, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND id_gop_unidad_funcional = ?2\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    gestor_tabla_tmp\n"
                    + "WHERE\n"
                    + "    id_empleado_tipo_cargo <> ?1\n"
                    + sql_unida_func;
            Query q = em.createNativeQuery(sql, GestorTablaTmp.class);
            q.setParameter(1, idEmpleadoCargo);
            q.setParameter(2, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
