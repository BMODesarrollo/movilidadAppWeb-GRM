/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TecnicoPatioTipoTabla;
import com.movilidad.utils.Util;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class TecnicoPatioTipoTablaFacade extends AbstractFacade<TecnicoPatioTipoTabla> implements TecnicoPatioTipoTablaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    private Util util;

    public TecnicoPatioTipoTablaFacade() {
        super(TecnicoPatioTipoTabla.class);
    }

    @Override
    public TecnicoPatioTipoTabla findTipoTabla(int uF, String d, String codigoVehiculo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "ROW_NUMBER() OVER(ORDER BY servbus) AS id ,p.servbus AS servbus, COUNT(p.id_prg_tc) AS num_entry_depot\n"
                    + "FROM\n"
                    + "prg_tc p\n"
                    + "INNER JOIN\n"
                    + "(SELECT \n"
                    + "psp.*\n"
                    + "FROM\n"
                    + "prg_stop_point psp\n"
                    + "WHERE\n"
                    + "psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON psppx.id_prg_stoppoint = p.to_stop\n"
                    + "LEFT JOIN vehiculo veh on veh.id_vehiculo= p.id_vehiculo\n"
                    + "WHERE\n"
                    + "p.fecha = ?1\n"
                    + "AND p.servbus IS NOT NULL\n"
                    + "AND p.estado_operacion NOT IN (3 , 4, 6, 7)\n"
                    + "AND p.id_gop_unidad_funcional = ?2\n"
                    + "AND veh.codigo= ?3\n"
                    + "GROUP BY servbus LIMIT 1", TecnicoPatioTipoTabla.class);
            q.setParameter(1, d);
            q.setParameter(2, uF);
            q.setParameter(3, codigoVehiculo);
            return (TecnicoPatioTipoTabla) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
