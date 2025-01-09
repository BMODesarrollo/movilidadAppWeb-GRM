/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovReqSemana;
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
public class GestorNovReqSemanaFacade extends AbstractFacade<GestorNovReqSemana> implements GestorNovReqSemanaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovReqSemanaFacade() {
        super(GestorNovReqSemana.class);
    }

    @Override
    public List<GestorNovReqSemana> findAllByIdCargo(Integer idEmpleadoTipoCargo, Integer idGestorNovedad) {
        try {
            String sql = "SELECT * FROM gestor_nov_req_semana where id_empleado_tipo_cargo = ?1\n"
                    + "and id_gestor_novedad = ?2;";
            Query q = em.createNativeQuery(sql, GestorNovReqSemana.class);
            q.setParameter(1, idEmpleadoTipoCargo);
            q.setParameter(2, idGestorNovedad);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
}
