/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MyAppConfirmDepotEntry;
import com.movilidad.model.MyAppConfirmDepotExit;
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
public class MyAppConfirmDepotExitFacade extends AbstractFacade<MyAppConfirmDepotExit> implements MyAppConfirmDepotExitFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MyAppConfirmDepotExitFacade() {
        super(MyAppConfirmDepotExit.class);
    }

    @Override
    public List<MyAppConfirmDepotExit> findByDateRange(Date fechaDesde, Date fechaHasta, int idGopUf) {
        try {
            String uf = idGopUf == 0 ? " " : " AND tc.id_gop_unidad_funcional = " + idGopUf + " ";
            String sql = "SELECT \n"
                    + "    macde.*\n"
                    + "FROM\n"
                    + "    my_app_confirm_depot_exit macde\n"
                    + "        INNER JOIN\n"
                    + "    prg_tc tc ON macde.id_prg_tc = tc.id_prg_tc\n"
                    + "WHERE\n"
                    + "    DATE(macde.fecha_hora) BETWEEN ?1 AND ?2\n"
                    + uf
                    + "        AND macde.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, MyAppConfirmDepotExit.class);
            query.setParameter(1, Util.dateFormat(fechaDesde));
            query.setParameter(2, Util.dateFormat(fechaHasta));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MyAppConfirmDepotExit findByIdPrgTc(Integer idPrgTc) {
        try {
            String sql = "SELECT * "
                    + "FROM my_app_confirm_depot_exit "
                    + "WHERE id_prg_tc = ?1 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, MyAppConfirmDepotExit.class);
            q.setParameter(1, idPrgTc);
            return (MyAppConfirmDepotExit) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MyAppConfirmDepotExit findServiceConfirmate(Integer idPrgTc) {

        try {
            String sql = "SELECT  \n"
                    + "* \n"
                    + "FROM \n"
                    + "my_app_confirm_depot_exit \n"
                    + "WHERE \n"
                    + "id_prg_tc = ?1 \n"
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, MyAppConfirmDepotEntry.class);
            q.setParameter(1, idPrgTc);
            return (MyAppConfirmDepotExit) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public List<MyAppConfirmDepotExit> findTypeServiceConfirmateByVehiculoAndFecha(String codigoVehiculo, Integer idGopUf, Date fecha) {
        try {
            String sql = "select * from my_app_confirm_depot_exit where id_prg_tc in (SELECT \n"
                    + "p.id_prg_tc  \n"
                    + "FROM \n"
                    + "prg_tc p \n"
                    + "INNER JOIN \n"
                    + "(SELECT  \n"
                    + "psp.* \n"
                    + "FROM \n"
                    + "prg_stop_point psp \n"
                    + "WHERE \n"
                    + "psp.is_depot IS NULL OR psp.is_depot = 1) psppx ON psppx.id_prg_stoppoint = p.from_stop \n"
                    + "LEFT JOIN vehiculo veh on veh.id_vehiculo=p.id_vehiculo \n"
                    + "WHERE \n"
                    + "p.fecha = ?1 \n"
                    + "AND p.servbus IS NOT NULL \n"
                    + "AND p.estado_operacion NOT IN (3 , 4, 6, 7) \n"
                    + "AND p.id_gop_unidad_funcional = ?2\n"
                    + "AND veh.codigo= ?3 \n"
                    + ") and DATE(fecha_hora) = ?1";
            Query query = em.createNativeQuery(sql, MyAppConfirmDepotEntry.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idGopUf);
            query.setParameter(3, codigoVehiculo);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
