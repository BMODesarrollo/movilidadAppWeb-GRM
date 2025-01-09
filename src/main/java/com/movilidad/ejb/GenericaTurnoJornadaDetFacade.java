/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoJornadaDet;
import java.util.ArrayList;
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
public class GenericaTurnoJornadaDetFacade extends AbstractFacade<GenericaTurnoJornadaDet> implements GenericaTurnoJornadaDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTurnoJornadaDetFacade() {
        super(GenericaTurnoJornadaDet.class);
    }

    @Override
    public List<GenericaTurnoJornadaDet> getByIdGenericaTurnoJornada(int idGenericaTurnoJornada) {
        try {
            Query q = em.createNativeQuery("select * \n"
                    + "from generica_turno_jornada_det\n"
                    + "where id_generica_turno_jornada = ?1\n"
                    + "and estado_reg = 0;", GenericaTurnoJornadaDet.class);
            q.setParameter(1, idGenericaTurnoJornada);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Integer getSumCantidad(int op, int idCargo, int id) {
        try {
            String param;
            param = op == 1 ? "gtj.cantidad_habil" : "gtj.cantidad_feriada";
            Query q = em.createNativeQuery("select sum(" + param + ")\n"
                    + "from generica_turno_jornada_det gtj\n"
                    + "where id_empleado_tipo_cargo = ?1 \n"
                    + "and id_generica_turno_jornada_det <> ?2\n"
                    + "and estado_reg = 0");
            q.setParameter(1, idCargo);
            q.setParameter(2, id);
            Object obj = q.getSingleResult();
            if (obj != null) {
                Integer result = Integer.parseInt(obj.toString());
                return result;
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
