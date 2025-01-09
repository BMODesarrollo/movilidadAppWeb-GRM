/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmGrupoDetalle;
import com.movilidad.model.PmGrupoDetalle;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class GenericaPmGrupoDetalleFacade extends AbstractFacade<GenericaPmGrupoDetalle> implements GenericaPmGrupoDetalleFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmGrupoDetalleFacade() {
        super(GenericaPmGrupoDetalle.class);
    }

    @Override
    public GenericaPmGrupoDetalle findByIdEmpleadoAndIdGrupoPm(int idGrupo, int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    generica_pm_grupo_detalle p\n"
                    + "WHERE\n"
                    + "    p.id_generica_pm_grupo <> ?1 AND p.id_empleado = ?2;", GenericaPmGrupoDetalle.class);
            q.setParameter(1, idGrupo);
            q.setParameter(2, idEmpleado);
            return (GenericaPmGrupoDetalle) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
