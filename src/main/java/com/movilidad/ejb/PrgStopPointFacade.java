/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgStopPoint;
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
public class PrgStopPointFacade extends AbstractFacade<PrgStopPoint> implements PrgStopPointFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgStopPointFacade() {
        super(PrgStopPoint.class);
    }

    @Override
    public PrgStopPoint find(String field, String value) {
        try {
            Query q = getEntityManager().createNativeQuery("select * from prg_stop_point e"
                    + " where " + field + "=?1 order by e.id_prg_stoppoint desc limit 1", PrgStopPoint.class);
            q.setParameter(1, value);
            return (PrgStopPoint) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgStopPoint> getPatios() {
        try {
            Query q = em.createNativeQuery("SELECT p.* "
                    + "FROM prg_stop_point p WHERE p.is_depot=1 AND p.is_active=1;", PrgStopPoint.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgStopPoint> getparadasByNombre(String nombre, int idgopUnidadFuncional) {
        String sql_unida_func = idgopUnidadFuncional == 0 ? ""
                : "      AND psp.id_gop_unidad_funcional=?1";
        Query q = em.createNativeQuery("SELECT \n"
                + "    psp.*\n"
                + "FROM\n"
                + "    prg_stop_point psp\n"
                + "WHERE\n"
                + "    psp.propia = 1 AND name LIKE '%" + nombre + "%'\n"
                + sql_unida_func, PrgStopPoint.class);
        q.setParameter(1, idgopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgStopPoint> getParadasPropios(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "         psp.id_gop_unidad_funcional = ?1 AND\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    psp.*\n"
                + "FROM\n"
                + "    prg_stop_point psp\n"
                + "WHERE\n"
                + sql_unida_func
                + "    psp.propia = 1;", PrgStopPoint.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public PrgStopPoint validarparadaByNombre(String nombre) {

        try {
            Query q = em.createNativeQuery("SELECT * FROM prg_stop_point "
                    + "WHERE propia=1 and name=?1;", PrgStopPoint.class);
            q.setParameter(1, nombre);
            return (PrgStopPoint) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int desactivarStopPoints(String codigo) {
        try {
            Query q = em.createNativeQuery("update prg_stop_point set is_active = 0 where id_stop_point ='" + codigo + "';");
//            Query q = em.createNativeQuery("update prg_stop_point set is_active = 0 where code ='"+codigo+"';");
            int result = q.executeUpdate();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public List<PrgStopPoint> findAllByUnidadFuncional(Integer idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND psp.id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    psp.*\n"
                + "FROM\n"
                + "    prg_stop_point psp\n"
                + "WHERE\n"
                + "    psp.estado_reg = 0\n"
                + sql_unida_func, PrgStopPoint.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public Long count(int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
        Query q = em.createNativeQuery("SELECT \n"
                + "    COUNT(*) total\n"
                + "FROM\n"
                + "    prg_stop_point\n"
                + "WHERE\n"
                + "    estado_reg = 0\n"
                + sql_unida_func);
        q.setParameter(1, idGopUnidadFuncional);
        return (Long) q.getSingleResult();

    }

    /**
     * Método responsable de devolver los puntos que coincidan con el nombre que
     * se indique por parametro, estos puntos seran tambien los que tengan la
     * propiedad is_active igual a 1.
     *
     * @param name
     * @param idGopUnidadFuncional
     * @return
     */
    @Override
    public List<PrgStopPoint> findStopPointByName(String name, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND sp.id_gop_unidad_funcional = ?1\n";

        Query q = em.createNativeQuery("SELECT \n"
                + "    sp.*\n"
                + "FROM\n"
                + "    prg_stop_point sp\n"
                + "WHERE\n"
                + "    sp.name LIKE '%" + name + "%'\n"
                + sql_unida_func
                + "        AND sp.is_active = 1;", PrgStopPoint.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

}
