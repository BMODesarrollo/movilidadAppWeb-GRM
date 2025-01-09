/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgAsignacion;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class PrgAsignacionFacade extends AbstractFacade<PrgAsignacion> implements PrgAsignacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgAsignacionFacade() {
        super(PrgAsignacion.class);
    }

    @Override
    public List<PrgAsignacion> findAsignacionDiaByFechaAndIdGopUF(Date fecha, int idGopUF) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_asignacion\n"
                    + "WHERE\n"
                    + "    fecha = DATE(?1)\n"
                    + "        AND id_gop_unidad_funcional = ?2\n"
                    + "ORDER BY id_vehiculo ASC";
            Query q = em.createNativeQuery(sql, PrgAsignacion.class)
                    .setParameter(1, Util.dateFormat(fecha))
                    .setParameter(2, idGopUF);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int removeByDate(Date d, int idGopUF) {
        try {
            String sql = "DELETE FROM prg_asignacion \n"
                    + "WHERE\n"
                    + "    fecha = DATE(?1)\n"
                    + "    AND id_gop_unidad_funcional = ?2";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            q.setParameter(2, idGopUF);
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public List<PrgAsignacion> getTareasProgramadasMtto(Date desde, Date hasta, int idGopUF) {
        try {
            String sql = "SELECT * "
                    + "FROM prg_asignacion "
                    + "WHERE fecha BETWEEN ?1 AND ?2 "
                    + "AND id_mtto_tarea is not null "
                    + "AND estado_reg = 0;";
            Query q = em.createNativeQuery(sql, PrgAsignacion.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Integer countAsignacionDiaByFechaAndIdGopUF(Date fecha, int idGopUF) {
        try {
            String sql = "SELECT \n"
                    + "    COUNT(*)\n"
                    + "FROM\n"
                    + "    prg_asignacion\n"
                    + "WHERE\n"
                    + "    fecha = DATE(?1)\n"
                    + "        AND id_gop_unidad_funcional = ?2";
            Query q = em.createNativeQuery(sql, PrgAsignacion.class)
                    .setParameter(1, Util.dateFormat(fecha))
                    .setParameter(2, idGopUF);
            Object count = q.getSingleResult();
            return count != null ? (Integer) count : 0;
        } catch (Exception e) {
            return 0;
        }
    }

}
