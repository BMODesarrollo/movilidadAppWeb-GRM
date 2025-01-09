/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadAutorizacionAusentismos;
import com.movilidad.utils.Util;
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
public class NovedadAutorizacionAusentismosFacade extends AbstractFacade<NovedadAutorizacionAusentismos> implements NovedadAutorizacionAusentismosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadAutorizacionAusentismosFacade() {
        super(NovedadAutorizacionAusentismos.class);
    }

    @Override
    public List<NovedadAutorizacionAusentismos> findAllByRangoFechasAndUF(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND na.id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT \n"
                    + "    na.*\n"
                    + "FROM\n"
                    + "    novedad_autorizacion_ausentismos na\n"
                    + "        INNER JOIN\n"
                    + "    novedad n ON na.id_novedad = n.id_novedad\n"
                    + "WHERE\n"
                    + "    n.fecha between ?1 and ?2\n"
                    + sql_unida_func
                    + "                             AND n.estado_reg = 0\n"
                    + "                             AND n.id_novedad_tipo = 1\n"
                    + "                             AND na.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, NovedadAutorizacionAusentismos.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
