/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaJornada;
import com.movilidad.model.GenericaSolicitud;
import com.movilidad.utils.Util;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class GenericaSolicitudFacade extends AbstractFacade<GenericaSolicitud> implements GenericaSolicitudFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaSolicitudFacade() {
        super(GenericaSolicitud.class);
    }

    @Override
    public List<GenericaSolicitud> findByDateRange(Date fechaIni, Date fechaFin, Integer area, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?4\n";
            return this.em.createNativeQuery("SELECT\n"
                    + "	gs.*\n"
                    + "FROM\n"
                    + "	generica_solicitud gs \n"
                    + "WHERE\n"
                    + "	gs.fecha_solicitud BETWEEN ?1 AND ?2\n"
                    + sql_unida_func
                    + "	AND gs.estado_reg = 0\n"
                    + "	AND gs.id_param_area = ?3\n"
                    + "ORDER BY\n"
                    + "	gs.fecha_solicitud ASC;", GenericaSolicitud.class)
                    .setParameter(1, Util.dateFormat(fechaIni))
                    .setParameter(3, area)
                    .setParameter(4, idGopUnidadFuncional)
                    .setParameter(2, Util.dateFormat(fechaFin)).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
