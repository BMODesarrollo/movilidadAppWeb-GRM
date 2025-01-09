/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Multa;
import com.movilidad.util.beans.MultasCtrl;
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
public class MultaFacade extends AbstractFacade<Multa> implements MultaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MultaFacade() {
        super(Multa.class);
    }

    @Override
    public List<Multa> findEstRegis(Date fecha) {
        try {
            Query q = em.createQuery("SELECT m FROM Multa m WHERE m.estadoReg = :estadoReg AND m.fechaHora = :fecha", Multa.class)
                    .setParameter("estadoReg", 0)
                    .setParameter("fecha", 0);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Multa> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND m.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";
        Query query = em.createQuery("SELECT m from Multa m where m.fechaHora "
                + "BETWEEN :fechaIni AND :fechaFin "
                + sql_unida_func
                + "AND m.estadoReg = 0 ORDER BY m.fechaHora DESC")
                .setParameter("fechaIni", fechaIni)
                .setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }

    @Override
    public List<Multa> findAll(Date fecha) {
        Query query = em.createQuery("SELECT m from Multa m "
                + "where m.fechaHora = :fecha ORDER BY m.fechaHora DESC")
                .setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public List<MultasCtrl> findMultasCtrl(Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND m.id_gop_unidad_funcional = ?2\n";
            Query query = em.createNativeQuery("select\n"
                    + "	e.codigo_tm as codigo_operador,\n"
                    + "	v.codigo as codigo_vehiculo,\n"
                    + "	CONCAT(e.nombres, ' ', e.apellidos) as nombre_operador,\n"
                    + "	COUNT(if(m.id_multa_tipo = 1, 1, null)) as operaciones,\n"
                    + "	COUNT(if(m.id_multa_tipo = 2, 1, null)) as mantenimiento,\n"
                    + "	COUNT(if(m.id_multa_tipo = 3, 1, null)) as lavado,\n"
                    + "	COUNT(if(m.id_multa_tipo = 4, 1, null)) as seguridad_vial,\n"
                    + "	SUM(if(m.id_multa_tipo = 1, mc.metros/1000, 0)) as kms_operaciones,\n"
                    + "	SUM(if(m.id_multa_tipo = 2, mc.metros/1000, 0)) as kms_mantenimiento,\n"
                    + "	SUM(if(m.id_multa_tipo = 3, mc.metros/1000, 0)) as kms_lavado,\n"
                    + "	SUM(if(m.id_multa_tipo = 4, mc.metros/1000, 0)) as kms_seguridad_vial\n"
                    + "from\n"
                    + "	multa m\n"
                    + "left join vehiculo v on\n"
                    + "	m.id_vehiculo = v.id_vehiculo\n"
                    + "inner join empleado e on\n"
                    + "	m.id_empleado = e.id_empleado\n"
                    + "inner join multa_clasificacion mc on\n"
                    + "	m.id_multa_clasificacion = mc.id_multa_clasificacion	\n"
                    + "where\n"
                    + "	date(m.fecha_hora) = ?1\n"
                    + sql_unida_func
                    + "and m.procede = 1 \n"
                    + "group by\n"
                    + "	m.id_empleado,m.id_vehiculo\n"
                    + "order by nombre_operador;", "MultasCtrlMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(2, idGopUnidadFuncional);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
