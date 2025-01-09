/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTarea;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
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
public class PrgTareaFacade extends AbstractFacade<PrgTarea> implements PrgTareaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgTareaFacade() {
        super(PrgTarea.class);
    }

    @Override
    public List<PrgTarea> findallEst() {
        try {
            Query q = em.createQuery("SELECT m FROM PrgTarea m WHERE m.estadoReg = :estadoReg", PrgTarea.class)
                    .setParameter("estadoReg", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTarea> obtenerServicios() {
        Query query = em.createQuery("SELECT t from PrgTarea t WHERE "
                + "t.comercial = 1 AND t.sumDistancia = 1");
        return query.getResultList();
    }

    @Override
    public List<PrgTarea> findAllByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        String sql_unidad_func = idGopUnidadFuncional == 0 ? "" : "        t.id_gop_unidad_funcional=?1 AND";
        Query q = em.createNativeQuery("SELECT \n"
                + "    t.*\n"
                + "FROM\n"
                + "    prg_tarea t\n"
                + "WHERE\n"
                + sql_unidad_func
                + "    t.estado_reg = 0", PrgTarea.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public List<PrgTarea> findFromAddServices(int idGopUnidadFuncional) {
        String sql_unidad_func = idGopUnidadFuncional == 0 ? "" : "        p.id_gop_unidad_funcional=?1 AND";

        Query q = em.createNativeQuery("SELECT \n"
                + "    p.*\n"
                + "FROM\n"
                + "    prg_tarea p\n"
                + "WHERE\n"
                + sql_unidad_func
                + "    p.sum_distancia = 1\n"
                + "        AND p.id_prg_tarea NOT IN ("
                + SingletonConfigEmpresa.getMapConfiMapEmpresa()
                        .get(ConstantsUtil.ID_VAC_PRG) + ")\n"
                + "        AND p.estado_reg = 0 ORDER BY p.tarea ASC;", PrgTarea.class);
        q.setParameter(1, idGopUnidadFuncional);
        return q.getResultList();
    }

    @Override
    public PrgTarea findByNombreTareaAndIdGopUnidadFuncional(String nombreTarea, int idGopUnidadFuncional, int idPrgTarea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    pt.*\n"
                    + "FROM\n"
                    + "    prg_tarea pt\n"
                    + "WHERE\n"
                    + "    pt.tarea = ?1\n"
                    + "        AND pt.id_gop_unidad_funcional = ?2\n"
                    + "        AND pt.id_prg_tarea <> ?3\n"
                    + "LIMIT 1", PrgTarea.class);
            q.setParameter(1, nombreTarea);
            q.setParameter(2, idGopUnidadFuncional);
            q.setParameter(3, idPrgTarea);
            return (PrgTarea) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgTarea> findAllTareasSumDistancia(int idGopUnidadFunc) {
        String sql_unida_func = idGopUnidadFunc == 0 ? " "
                : "      AND p.id_gop_unidad_funcional = ?1";

        Query q = em.createNativeQuery("SELECT \n"
                + "    p.*\n"
                + "FROM\n"
                + "    prg_tarea p\n"
                + "WHERE\n"
                + "    p.sum_distancia = 1 AND p.estado_reg= 1 \n" + sql_unida_func, PrgTarea.class);
        q.setParameter(1, idGopUnidadFunc);
        return q.getResultList();
    }

}
