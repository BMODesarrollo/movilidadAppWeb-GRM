/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadClasificacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
public class NovedadClasificacionFacade extends AbstractFacade<NovedadClasificacion> implements NovedadClasificacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadClasificacionFacade() {
        super(NovedadClasificacion.class);
    }

    @Override
    public List<NovedadClasificacion> findAllEstadoReg() {
        Query q = em.createNativeQuery("SELECT * FROM novedad_clasificacion WHERE estado_reg=0;",
                NovedadClasificacion.class);
        return q.getResultList();
    }

}
