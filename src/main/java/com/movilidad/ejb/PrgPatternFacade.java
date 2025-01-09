/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgPattern;
import java.util.ArrayList;
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
public class PrgPatternFacade extends AbstractFacade<PrgPattern> implements PrgPatternFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgPatternFacade() {
        super(PrgPattern.class);
    }

    @Override
    public List<PrgPattern> findAllOrderedByIdRoute(int idRoute) {
        try {
            Query q = em.createNativeQuery("SELECT pp.* FROM prg_pattern pp "
                    + "where pp.id_prg_route=?1 order by pp.secuence_number asc;", PrgPattern.class);
            q.setParameter(1, idRoute);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<PrgPattern> findAllByidGopUnidadFuncOrdered(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         p.id_gop_unidad_funcional = ?1 AND\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    p.*\n"
                + "FROM\n"
                + "    prg_pattern p\n"
                + "        INNER JOIN\n"
                + "    prg_route pr ON p.id_prg_route = pr.id_prg_route\n"
                + "WHERE\n"
                + sql_unida_func
                + "         p.estado_reg = 0\n"
                + "ORDER BY pr.name ASC , p.secuence_number ASC", PrgPattern.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

}
