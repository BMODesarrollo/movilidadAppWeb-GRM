/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaTurnoFijo;
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
public class GenericaTurnoFijoFacade extends AbstractFacade<GenericaTurnoFijo> implements GenericaTurnoFijoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaTurnoFijoFacade() {
        super(GenericaTurnoFijo.class);
    }

    @Override
    public List<GenericaTurnoFijo> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_turno_fijo g\n"
                    + "        INNER JOIN\n"
                    + "    generica_jornada_tipo gjt ON g.id_generica_jornada_tipo = gjt.id_generica_jornada_tipo\n"
                    + "WHERE\n"
                    + "    gjt.id_param_area = ?1\n"
                    + "        AND g.estado_reg = 0;", GenericaTurnoFijo.class);
            q.setParameter(1, idArea);
            return q.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaTurnoFijo findTurnoByIdEmpleado(int idEmpleado, int idRegistro) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_turno_fijo g\n"
                    + "WHERE\n"
                    + "    g.id_empleado = ?1 AND g.estado_reg = 0\n"
                    + "        AND g.id_generica_turno_fijo <> ?2;", GenericaTurnoFijo.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, idRegistro);
            return (GenericaTurnoFijo) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }

    }

}
