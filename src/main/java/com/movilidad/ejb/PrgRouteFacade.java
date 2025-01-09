/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgRoute;
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
public class PrgRouteFacade extends AbstractFacade<PrgRoute> implements PrgRouteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgRouteFacade() {
        super(PrgRoute.class);
    }

    @Override
    public PrgRoute find(String field, String value, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND e.id_gop_unidad_funcional = ?2\n";

        String sql = "SELECT \n"
                + "    e.*\n"
                + "FROM\n"
                + "    prg_route e\n"
                + "WHERE\n"
                + "    e." + field + " = ?1\n"
                + sql_unida_func
                + "ORDER BY e.id_prg_route DESC\n"
                + "LIMIT 1";
        try {
            Query q = getEntityManager().createNativeQuery(sql, PrgRoute.class);
            q.setParameter(1, value);
            q.setParameter(2, idGopUnidadFuncional);
            return (PrgRoute) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgRoute> getRutas() {
        Query query = em.createQuery("SELECT r from PrgRoute r WHERE "
                + "r.isActive = 1 ORDER BY r.name", PrgRoute.class);
        return query.getResultList();
    }

    @Override
    public List<PrgRoute> findActivas() {
        try {
            Query q = getEntityManager().createNativeQuery("SELECT pr.* FROM "
                    + "prg_route pr where pr.is_active=1;", PrgRoute.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int desactivarRoute(String codigo) {
        try {
            Query q = em.createNativeQuery("update prg_route set is_active = 0 where code ='" + codigo + "';");
            int result = q.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<PrgRoute> findActivasByUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND pr.id_gop_unidad_funcional = ?1\n";
        String sql = "SELECT \n"
                + "    pr.*\n"
                + "FROM\n"
                + "    prg_route pr\n"
                + "WHERE\n"
                + "    pr.is_active = 1 AND pr.estado_reg = 0\n"
                + sql_unida_func;
        Query q = getEntityManager().createNativeQuery(sql, PrgRoute.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgRoute> findByUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND pr.id_gop_unidad_funcional = ?1\n";
        String sql = "SELECT \n"
                + "    pr.*\n"
                + "FROM\n"
                + "    prg_route pr\n"
                + "WHERE\n"
                + "     pr.estado_reg = 0\n"
                + sql_unida_func;
        Query q = getEntityManager().createNativeQuery(sql, PrgRoute.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public Long count(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    COUNT(*) total\n"
                + "FROM\n"
                + "    prg_route\n"
                + "WHERE\n"
                + "    estado_reg = 0\n"
                + sql_unida_func);
        q.setParameter(1, idGopUnidadFuncional);
        return (Long) q.getSingleResult();
    }
}
