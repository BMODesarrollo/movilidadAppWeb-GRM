/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopCierreTurno;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class GopCierreTurnoFacade extends AbstractFacade<GopCierreTurno> implements GopCierreTurnoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GopCierreTurnoFacade() {
        super(GopCierreTurno.class);
    }

    @Override
    public GopCierreTurno findLastGopCierreTurno(Date fecha, int idGopUnidadFuncional) {
        String sql_uf = idGopUnidadFuncional == 0 ? "" : "        AND gct.id_gop_unidad_funcional = ?2\n";
        String sql = "SELECT \n"
                + "    gct.*\n"
                + "FROM\n"
                + "    gop_cierre_turno gct\n"
                + "WHERE\n"
                + "    gct.estado_reg = 0\n"
                + "    AND DATE(gct.fecha_hora) = ?1\n"
                + sql_uf
                + "ORDER BY gct.fecha_hora DESC\n"
                + "LIMIT 1";
        try {
            Query q = em.createNativeQuery(sql, GopCierreTurno.class);
            q.setParameter(2, idGopUnidadFuncional);
            q.setParameter(1, Util.dateFormat(fecha));
            return (GopCierreTurno) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GopCierreTurno> findByFechaAndIdGopUnidadFunc(Date fecha, int idGopUnidaFunc) {
        String sql_uf = idGopUnidaFunc == 0 ? "" : "        AND gct.id_gop_unidad_funcional = ?2\n";
        String sql = "SELECT \n"
                + "    gct.*\n"
                + "FROM\n"
                + "    gop_cierre_turno gct\n"
                + "WHERE\n"
                + "    gct.estado_reg = 0\n"
                + "    AND DATE(gct.fecha_hora) = ?1\n"
                + sql_uf
                + "ORDER BY gct.fecha_hora DESC\n";
        try {
            Query q = em.createNativeQuery(sql, GopCierreTurno.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idGopUnidaFunc);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
