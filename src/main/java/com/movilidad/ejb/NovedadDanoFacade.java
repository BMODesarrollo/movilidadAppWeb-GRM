/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDano;
import java.util.ArrayList;
import java.util.Date;
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
public class NovedadDanoFacade extends AbstractFacade<NovedadDano> implements NovedadDanoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadDanoFacade() {
        super(NovedadDano.class);
    }

    @Override
    public List<NovedadDano> findAll(Date fecha) {
        Query query = em.createQuery("SELECT nd from NovedadDano nd "
                + "where nd.fecha = :fecha ORDER BY nd.fecha DESC")
                .setParameter("fecha", fecha);
        return query.getResultList();
    }
    
        @Override
    public List<NovedadDano> findByDateRangeV1(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {
            try {
                String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND nd.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

        Query query = em.createQuery("SELECT nd from NovedadDano nd where nd.fecha "
                + "BETWEEN :fechaIni AND :fechaFin and nd.version is null "
                + sql_unida_func
                + "ORDER BY nd.fecha DESC")
                .setParameter("fechaIni", fechaIni)
                .setParameter("fechaFin", fechaFin);
        return query.getResultList();
            } catch (Exception e) {
                return new ArrayList<>();
            }     
    }

    @Override
    public List<NovedadDano> findByDateRange(Date fechaIni, Date fechaFin, int idGopUnidadFuncional) {

        String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "  AND nd.idGopUnidadFuncional.idGopUnidadFuncional = " + idGopUnidadFuncional + "\n";

        Query query = em.createQuery("SELECT nd from NovedadDano nd where nd.fecha "
                + "BETWEEN :fechaIni AND :fechaFin AND nd.version=2 "
                + sql_unida_func
                + "ORDER BY nd.fecha DESC")
                .setParameter("fechaIni", fechaIni)
                .setParameter("fechaFin", fechaFin);
        return query.getResultList();
    }
    
        @Override
    public List<NovedadDano> findAllByIdEmpleadoAndDates(Integer idNov, Integer idEmpleado,
            String desde, String hasta,
            int idGopUF
    ) {
        String sql = "select * "
                + "from novedad_dano "
                + "where id_empleado = ?1 "
                + "and id_novedad_dano <> ?2 and id_vehiculo_dano_severidad <> 1 "
                + "and fecha BETWEEN ?3 and ?4 ";
        if (idGopUF != 0) {
            sql = sql + "and id_gop_unidad_funcional = " + idGopUF + " ";
        }
        sql = sql + "and estado_reg = 0 and version=2";
        Query q = em.createNativeQuery(sql, NovedadDano.class);
        q.setParameter(1, idEmpleado);
        q.setParameter(2, idNov);
        q.setParameter(3, desde);
        q.setParameter(4, hasta);
        return q.getResultList();
    }

}
