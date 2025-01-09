/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmGrupoDetalle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class PmGrupoDetalleFacade extends AbstractFacade<PmGrupoDetalle> implements PmGrupoDetalleFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmGrupoDetalleFacade() {
        super(PmGrupoDetalle.class);
    }

    @Override
    public PmGrupoDetalle findByIdEmpleadoAndIdGrupoPm(int idGrupo, int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    pm_grupo_detalle p\n"
                    + "WHERE\n"
                    + "    p.id_grupo <> ?1 AND p.id_empleado = ?2;", PmGrupoDetalle.class);
            q.setParameter(1, idGrupo);
            q.setParameter(2, idEmpleado);
            return (PmGrupoDetalle) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PmGrupoDetalle> findByIdPmGrupo(int idPmGrupo) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    pgd.*\n"
                + "FROM\n"
                + "    pm_grupo_detalle pgd\n"
                + "WHERE\n"
                + "    pgd.id_grupo = ?1 AND pgd.estado_reg = 0;", PmGrupoDetalle.class);
        q.setParameter(1, idPmGrupo);
        return q.getResultList();
    }

    @Override
    public List<PmGrupoDetalle> findByActivosGrupo() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    pgd.*\n"
                + "FROM\n"
                + "    pm_grupo_detalle pgd\n"
                + "WHERE\n"
                + "pgd.estado_reg = 0;", PmGrupoDetalle.class);
        return q.getResultList();
    }

}
