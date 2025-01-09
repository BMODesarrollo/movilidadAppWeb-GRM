/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GmoNovedadInfrastruc;
import java.util.ArrayList;
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
public class GmoNovedadInfrastrucFacade extends AbstractFacade<GmoNovedadInfrastruc> implements GmoNovedadInfrastrucFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GmoNovedadInfrastrucFacade() {
        super(GmoNovedadInfrastruc.class);
    }

    @Override
    public List<GmoNovedadInfrastruc> findRanfoFechaEstadoReg(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND n.id_gop_unidad_funcional = ?3\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    gmo_novedad_infrastruc n\n"
                    + "WHERE\n"
                    + "   DATE(n.fecha_hora_nov) BETWEEN ?1 AND ?2\n"
                    + sql_unida_func
                    + "        AND estado_reg = 0;", GmoNovedadInfrastruc.class);
            q.setParameter(1, desde);
            q.setParameter(2, hasta);
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();

        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
