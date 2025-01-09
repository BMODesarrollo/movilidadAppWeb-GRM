/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GestorNovedad;
import com.movilidad.model.Novedad;
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
public class GestorNovedadFacade extends AbstractFacade<GestorNovedad> implements GestorNovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GestorNovedadFacade() {
        super(GestorNovedad.class);
    }

    @Override
    public GestorNovedad findAllByRangoFechasAndUnidadFuncional(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT * FROM gestor_novedad where desde = ?1 and hasta = ?2 "
                    + sql_unida_func
                    + "and estado_reg = 0 LIMIT 1;";
            Query q = em.createNativeQuery(sql, GestorNovedad.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            return (GestorNovedad) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Novedad> obtenerNovedadesFreeway(Date fechaLiquidado, Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?4\n";
            String sql = "SELECT \n"
                    + "    n.*\n"
                    + "FROM\n"
                    + "    novedad n\n"
                    + "        INNER JOIN\n"
                    + "    novedad_tipo_detalles nd ON n.id_novedad_tipo_detalle = nd.id_novedad_tipo_detalle\n"
                    + "WHERE\n"
                    + "    n.creado < ?1\n"
                    + "        AND nd.suma_gestor = 1\n"
                    + "        AND nd.incluir_reporte = 1\n"
                    + "        AND ((?2 BETWEEN desde AND hasta\n"
                    + "        OR ?3 BETWEEN desde AND hasta)\n"
                    + "        OR n.fecha BETWEEN ?2 AND ?3\n"
                    + "        OR (desde BETWEEN ?2 AND ?3\n"
                    + "        OR hasta BETWEEN ?2 AND ?3))\n"
                    + sql_unida_func
                    + "        AND n.estado_reg = 0;";
            Query q = em.createNativeQuery(sql, Novedad.class);
            q.setParameter(1, Util.dateTimeFormat(fechaLiquidado));
            q.setParameter(2, Util.dateFormat(desde));
            q.setParameter(3, Util.dateFormat(hasta));
            q.setParameter(4, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
