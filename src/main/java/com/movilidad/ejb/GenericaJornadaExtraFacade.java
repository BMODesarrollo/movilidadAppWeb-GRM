/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornadaExtra;
import com.movilidad.utils.Util;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class GenericaJornadaExtraFacade extends AbstractFacade<GenericaJornadaExtra> implements GenericaJornadaExtraFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaJornadaExtraFacade() {
        super(GenericaJornadaExtra.class);
    }

    @Override
    public GenericaJornadaExtra getByEmpleadoAndFecha(Integer id, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    generica_jornada_extra gje\n"
                    + "WHERE\n"
                    + "    gje.id_empleado = ?1\n"
                    + "        AND ?2 BETWEEN gje.desde AND gje.hasta;", GenericaJornadaExtra.class);
            q.setParameter(1, id);
            q.setParameter(2, Util.dateFormat(fecha));
            return (GenericaJornadaExtra) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

}
