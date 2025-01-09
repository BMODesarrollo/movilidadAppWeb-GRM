/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovDetSemana;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class GestorNovDetSemanaFacade extends AbstractFacade<GestorNovDetSemana> implements GestorNovDetSemanaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovDetSemanaFacade() {
        super(GestorNovDetSemana.class);
    }

    @Override
    public List<GestorNovDetSemana> findAllByUfAndIdCargo(int idGopUnidadFuncional, Integer idEmpleadoTipoCargo, Integer idGestorNovedad) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT * FROM gestor_nov_det_semana where id_empleado_tipo_cargo = ?1\n"
                    + sql_unida_func
                    + "and id_gestor_novedad = ?2;";
            Query q = em.createNativeQuery(sql, GestorNovDetSemana.class);
            q.setParameter(1, idEmpleadoTipoCargo);
            q.setParameter(2, idGestorNovedad);
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
