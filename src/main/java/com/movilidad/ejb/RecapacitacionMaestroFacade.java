/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgTc;
import com.movilidad.model.RecapacitacionMaestro;
import com.movilidad.utils.ConstantsUtil;
import com.movilidad.utils.SingletonConfigEmpresa;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class RecapacitacionMaestroFacade extends AbstractFacade<RecapacitacionMaestro> implements RecapacitacionMaestroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RecapacitacionMaestroFacade() {
        super(RecapacitacionMaestro.class);
    }

    @Override
    public List<RecapacitacionMaestro> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("RecapacitacionMaestro.findByEstadoReg", RecapacitacionMaestro.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<RecapacitacionMaestro> findRangeRecapacitacion(Date desde, Date hasta, int idGopUF) {
        String uf = idGopUF == 0 ? " " : " AND r.id_gop_unidad_funcional = " + idGopUF + " ";
        String sql = "SELECT r.* FROM recapacitacion_maestro r \n"
                + "INNER JOIN novedad n ON r.id_novedad = n.id_novedad \n"
                + "WHERE n.fecha BETWEEN ?1 AND ?2 \n"
                + uf
                + "AND r.estado_reg = 0;";
        Query q = em.createNativeQuery(sql, RecapacitacionMaestro.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    @Override
    public List<RecapacitacionMaestro> findRangeRecapacitacionCita(Date desde, int idGopUF) {
        String uf = idGopUF == 0 ? " " : " AND r.id_gop_unidad_funcional = " + idGopUF + " ";
        String sql = "SELECT r.* \n"
                + "FROM recapacitacion_maestro r \n"
                + "INNER JOIN novedad n ON r.id_novedad = n.id_novedad\n"
                + "WHERE DATE(r.fecha_citacion) = ?1 \n"
                + "AND r.estado_reg = 0 \n"
                + uf
                + "AND r.fecha_citacion IS NOT NULL;";
        Query q = em.createNativeQuery(sql, RecapacitacionMaestro.class);
        q.setParameter(1, Util.dateFormat(desde));
        return q.getResultList();
    }

    @Override
    public RecapacitacionMaestro findNovedad(int idNovedad) {
        String sql = "SELECT r.* FROM recapacitacion_maestro r "
                + "WHERE r.id_novedad = ?1 AND r.estado_reg = 0;";
        Query q = em.createNativeQuery(sql, RecapacitacionMaestro.class);
        q.setParameter(1, idNovedad);

        try {
            return (RecapacitacionMaestro) q.getSingleResult();
        } catch (NoResultException e) {
            return null;  // O maneja la excepción como desees
        }
    }

    @Override
    public List<RecapacitacionMaestro> findRangeRecapacitacionNoProgramadas(int idGopUF) {
        String uf = idGopUF == 0 ? " " : " AND r.id_gop_unidad_funcional = " + idGopUF + " ";
        String sql = "SELECT r.* FROM recapacitacion_maestro r\n"
                + "INNER JOIN novedad n ON r.id_novedad = n.id_novedad\n"
                + "WHERE r.estado_reg = 0\n"
                + uf
                + "AND r.programado = 0;";
        Query q = em.createNativeQuery(sql, RecapacitacionMaestro.class);
        return q.getResultList();
    }

    @Override
    public List<PrgTc> findRangeTareasRecapacitacionProgramadas(Date desde, Date hasta, int idGopUF) {
        String uf = (idGopUF == 1) ? SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_RECAPACITACION_ZMOIII)
                : (idGopUF == 2) ? SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_RECAPACITACION_ZMOV)
                        : SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_RECAPACITACION_ZMOIII) + "," + SingletonConfigEmpresa.getMapConfiMapEmpresa().get(ConstantsUtil.ID_TAREA_RECAPACITACION_ZMOV);
        String sql = "SELECT p.* FROM prg_tc  p\n"
                + "WHERE p.fecha BETWEEN ?1 AND ?2\n"
                + "AND p.estado_reg = 0\n"
                + "AND p.id_task_type IN (" + uf + ") ;";
        Query q = em.createNativeQuery(sql, PrgTc.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

}
