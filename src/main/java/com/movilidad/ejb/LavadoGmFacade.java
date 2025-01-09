/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.LavadoGm;
import com.movilidad.util.beans.CostoLavadoDTO;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class LavadoGmFacade extends AbstractFacade<LavadoGm> implements LavadoGmFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LavadoGmFacade() {
        super(LavadoGm.class);
    }

    @Override
    public List<LavadoGm> findLavadoGMByFechaAndGopUF(int idGopUF, Date desde, Date hasta) {
        String uf = idGopUF != 0 ? "AND lav.id_gop_unidad_funcional = " + idGopUF + " " : " ";
        String sql = "SELECT \n"
                + "    IFNULL(lav.costo, lc.costo) AS costo,\n"
                + "    lav.id_lavado_gm,\n"
                + "    lav.id_gop_unidad_funcional,\n"
                + "    lav.id_vehiculo,\n"
                + "    lav.id_lavado_tipo_servicio,\n"
                + "    lav.id_lavado_motivo,\n"
                + "    lav.id_lavado_calificacion,\n"
                + "    lav.estado,\n"
                + "    lav.fecha_hora,\n"
                + "    lav.usr_califica,\n"
                + "    lav.obs_motivo,\n"
                + "    lav.fecha_califica,\n"
                + "    lav.fecha_cierre,\n"
                + "    lav.usr_cierra,\n"
                + "    lav.aprobado,\n"
                + "    lav.usr_aprueba,\n"
                + "    lav.fecha_aprueba,\n"
                + "    lav.username,\n"
                + "    lav.creado,\n"
                + "    lav.modificado,\n"
                + "    lav.estado_reg\n"
                + "FROM\n"
                + "    lavado_gm lav\n"
                + "        LEFT JOIN\n"
                + "    (SELECT \n"
                + "        *\n"
                + "    FROM\n"
                + "        lavado_costo lc\n"
                + "    WHERE\n"
                + "        (DATE(?1) BETWEEN lc.desde AND lc.hasta)\n"
                + "            AND (DATE(?2) BETWEEN lc.desde AND lc.hasta)\n"
                + "            AND lc.estado_reg = 0) lc ON lav.id_lavado_tipo_servicio = lc.id_lavado_tipo_servicio\n"
                + "WHERE\n"
                + "    DATE(lav.fecha_hora) BETWEEN DATE(?1) AND DATE(?2)\n"
                + "        AND lav.estado_reg = 0\n"
                + uf
                + "ORDER BY lav.estado ASC";
        Query q = em.createNativeQuery(sql, LavadoGm.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

    @Override
    public List<LavadoGm> findLavadoByVehiculo(int idVehiculo, Date desde, Date hasta) {
        String sql = "SELECT * FROM  lavado_gm \n"
                + "WHERE fecha_hora  BETWEEN ?1 AND ?2\n"
                + "AND id_vehiculo = ?3;";
        Query q = em.createNativeQuery(sql, LavadoGm.class);
        q.setParameter(1, desde);
        q.setParameter(2, hasta);
        q.setParameter(3, idVehiculo);
        return q.getResultList();
    }

    @Override
    public List<LavadoGm> findLavadoByRange(Date desde, Date hasta, int idGopUF) {
         String uf = idGopUF != 0 ? "AND id_gop_unidad_funcional = " + idGopUF + " " : " ";
        String sql = "SELECT * FROM  lavado_gm \n"
                + "WHERE fecha_hora  BETWEEN ?1 AND ?2\n"
                + uf 
                + "AND estado_reg = 0";
        Query q = em.createNativeQuery(sql, LavadoGm.class);
        q.setParameter(1, desde);
        q.setParameter(2, hasta);
        return q.getResultList();
    }

    @Override
    public List<CostoLavadoDTO> findCostoLavadoCerrado(int idGopUF, Date desde, Date hasta) {
        try {
            String idUF = idGopUF != 0 ? " AND lav.id_gop_unidad_funcional = " + idGopUF + " " : " ";
            String sql = "SELECT \n"
                    + "    DATE_FORMAT(DATE(lav.fecha_hora), '%Y-%m-%d') AS fecha,\n"
                    + "    COUNT(DATE(lav.fecha_hora)) AS lavado_cerrado,\n"
                    + "    IFNULL(SUM(lc.costo), 0) AS total_costo\n"
                    + "FROM\n"
                    + "    lavado_gm lav\n"
                    + "        INNER JOIN\n"
                    + "    (SELECT \n"
                    + "        *\n"
                    + "    FROM\n"
                    + "        lavado_costo lc\n"
                    + "    WHERE\n"
                    + "        (DATE(?1) BETWEEN lc.desde AND lc.hasta)\n"
                    + "            AND (DATE(?2) BETWEEN lc.desde AND lc.hasta)\n"
                    + "            AND lc.estado_reg = 0) lc ON lav.id_lavado_tipo_servicio = lc.id_lavado_tipo_servicio\n"
                    + "WHERE\n"
                    + "    DATE(lav.fecha_hora) BETWEEN DATE(?1) AND DATE(?2)\n"
                    + "        AND lav.estado = 1\n"
                    + "        AND (lav.aprobado = 0 OR lav.aprobado IS NULL)\n"
                    + idUF
                    + "        AND lav.estado_reg = 0\n"
                    + "GROUP BY 1";
            Query q = em.createNativeQuery(sql, "CostoLavadoCerradoMapping");
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
