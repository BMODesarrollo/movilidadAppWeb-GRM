/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmGrupo;
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
public class PmGrupoFacade extends AbstractFacade<PmGrupo> implements PmGrupoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmGrupoFacade() {
        super(PmGrupo.class);
    }

    @Override
    public List<PmGrupo> findAllByUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         pg.id_gop_unidad_funcional = ?1 AND\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    pg.*\n"
                + "FROM\n"
                + "    pm_grupo pg\n"
                + "WHERE\n"
                + sql_unida_func
                + "    pg.estado_reg = 0", PmGrupo.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PmGrupo> findAllActivos() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    pg.*\n"
                + "FROM\n"
                + "    pm_grupo pg\n"
                + "WHERE\n"
                + "    pg.estado_reg = 0", PmGrupo.class);
        return q.getResultList();
    }

    @Override
    public PmGrupo findByName(String nombre) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    pg.*\n"
                    + "FROM\n"
                    + "    pm_grupo pg\n"
                    + "WHERE\n"
                    + "    pg.estado_reg = 0 AND pg.nombre_grupo= ?1 LIMIT 1", PmGrupo.class);
            q.setParameter(1, nombre);
            return (PmGrupo) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
