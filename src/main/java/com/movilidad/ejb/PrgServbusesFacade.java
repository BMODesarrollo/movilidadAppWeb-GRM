/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgServbuses;
import com.movilidad.model.PrgTc;
import com.movilidad.util.beans.ReporteServbuses;
import com.movilidad.util.beans.ReporteServbusesPatioFin;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author luis
 */
@Stateless
public class PrgServbusesFacade extends AbstractFacade<PrgServbuses> implements PrgServbusesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgServbusesFacade() {
        super(PrgServbuses.class);
    }

    @Override
    public List<PrgServbuses> findByDate(Date fecha) {
        try {
            Query q = em.createQuery("SELECT p FROM PrgServbuses p WHERE "
                    + "p.fecha = :fecha  "
                    + "ORDER BY p.servbus");
            q.setParameter("fecha", fecha);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ReporteServbuses> getReporteServbuses(Date fecha) {
        try {
            String sql = "SELECT\n"
                    + "	serv.fecha,\n"
                    + "	v.codigo,\n"
                    + "	serv.servbus,\n"
                    + "	(select sp.name from prg_stop_point sp where sp.id_prg_stoppoint= serv.from_depot) as fromdepot,\n"
                    + "	(select sp.name from prg_stop_point sp where sp.id_prg_stoppoint= serv.to_depot) as todepot,\n"
                    + "	serv.time_origin,\n"
                    + "	serv.time_destiny\n"
                    + "from\n"
                    + "	prg_servbuses serv,\n"
                    + "	prg_asignacion asig\n"
                    + "inner join vehiculo v on asig.id_vehiculo = v.id_vehiculo\n"
                    + "where\n"
                    + "	serv.fecha = ?1\n"
                    + "	AND asig.fecha = ?1\n"
                    + "	AND serv.servbus = asig.servbus;";
            Query query = em.createNativeQuery(sql, "ReporteServbusesMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ReporteServbusesPatioFin> getReporteServbusesPatioFin(Date fecha) {
        try {
            String sql = "SELECT\n"
                    + "	serv.fecha,\n"
                    + "	v.codigo,\n"
                    + "	serv.servbus,\n"
                    + "	(select sp.name from prg_stop_point sp where\n"
                    + "		sp.id_prg_stoppoint = serv.to_depot) as todepot\n"
                    + "from\n"
                    + "	prg_servbuses AS serv \n"
                    + "inner join prg_stop_point st on\n"
                    + "	serv.to_depot = st.id_prg_stoppoint	,\n"
                    + "	prg_asignacion AS asig\n"
                    + "inner join vehiculo v on\n"
                    + "	asig.id_vehiculo = v.id_vehiculo\n"
                    + "where\n"
                    + "	serv.fecha = ?1 AND\n"
                    + "	is_depot = 1 AND is_active = 1\n"
                    + "	AND asig.fecha = ?1\n"
                    + "	AND serv.servbus = asig.servbus;";
            Query query = em.createNativeQuery(sql, "ReporteServbusesPatioFinMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public PrgTc getPrgtcSinAsignar(int codVehiculo) {
        try {
            String sql = "SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_stop_point psp ON tc.to_stop = psp.id_prg_stoppoint\n"
                    + "WHERE\n"
                    + "    tc.servbus IS NOT NULL\n"
                    + "        AND tc.id_vehiculo = ?1\n"
                    + "        AND psp.is_depot = 1\n"
                    + "        AND psp.is_active = 1\n"
                    + "        and tc.estado_operacion not in(5,8,99)\n"
                    + "ORDER BY tc.fecha DESC , TIME_TO_SEC(tc.time_destiny) DESC\n"
                    + "LIMIT 1;";
            Query query = em.createNativeQuery(sql, PrgTc.class);
            query.setParameter(1, codVehiculo);
//            query.setParameter(2, Util.toDate(Util.dateFormat(fecha)));
            return (PrgTc) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PrgTc getPrgtcSinAsignarByFecha(int codVehiculo, Date fecha, int idPatio) {
        try {
            String sql = "SELECT \n"
                    + "    tc.*\n"
                    + "FROM\n"
                    + "    prg_tc tc\n"
                    + "        INNER JOIN\n"
                    + "    prg_stop_point ON tc.to_stop = prg_stop_point.id_prg_stoppoint\n"
                    + "WHERE\n"
                    + "    tc.servbus IS NOT NULL\n"
                    + "        AND tc.id_vehiculo = ?1\n"
                    + "        AND is_depot = 1\n"
                    + "        AND is_active = 1\n"
                    + "        AND tc.fecha <=?2\n"
                    + "        AND tc.estado_operacion not in (5,8,99)\n"
                    + "ORDER BY tc.fecha DESC , TIME_TO_SEC(tc.time_destiny) DESC\n"
                    + "LIMIT 1;";
            Query query = em.createNativeQuery(sql, PrgTc.class);
            query.setParameter(1, codVehiculo);
            query.setParameter(2, Util.toDate(Util.dateFormat(fecha)));
            query.setParameter(3, idPatio);
            PrgTc tc = (PrgTc) query.getSingleResult();
            if (tc.getToStop().getIdPrgStoppoint().equals(idPatio) || idPatio == 0) {
                return tc;
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int removeByDate(Date d) {
        try {
            String sql = "delete from prg_servbuses where fecha= ?1";
            Query q = em.createNativeQuery(sql);
            q.setParameter(1, Util.dateFormat(d));
            return q.executeUpdate();
        } catch (Exception e) {
            return 0;
        }
    }
}
