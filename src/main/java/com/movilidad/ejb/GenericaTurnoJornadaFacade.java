/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoJornada;
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
public class GenericaTurnoJornadaFacade extends AbstractFacade<GenericaTurnoJornada> implements GenericaTurnoJornadaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTurnoJornadaFacade() {
        super(GenericaTurnoJornada.class);
    }

    @Override
    public List<GenericaTurnoJornada> findEstadoReg(Integer idParamArea, Integer idGenericaTurno) {
        try {
            String sql = "select gt.* \n"
                    + "from generica_turno_jornada gt \n"
                    + "inner join generica_jornada_tipo gjt \n"
                    + "on gt.id_generica_jornada_tipo = gjt.id_generica_jornada_tipo \n"
                    + "where gt.estado_reg = 0 \n"
                    + "and gjt.id_param_area = ?1\n"
                    + "and gt.id_generica_turno_jornada <> ?2\n"
                    + "and gjt.estado_reg = 0 order by gt.prioridad ASC";
            Query q = em.createNativeQuery(sql, GenericaTurnoJornada.class);
            q.setParameter(1, idParamArea);
            q.setParameter(2, idGenericaTurno);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
