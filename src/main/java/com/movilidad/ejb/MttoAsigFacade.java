/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoAsig;
import com.movilidad.util.beans.ResumenAsignadosPorPatio;
import com.movilidad.utils.Util;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class MttoAsigFacade extends AbstractFacade<MttoAsig> implements MttoAsigFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoAsigFacade() {
        super(MttoAsig.class);
    }

    @Override
    public MttoAsig findByServbus(Date fecha, String servbus) {
        String fecha_format = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    m.*\n"
                    + "FROM\n"
                    + "    mtto_asig m\n"
                    + "WHERE\n"
                    + "    m.fecha = ?1\n"
                    + "        AND m.servbus = ?2\n"
                    + "LIMIT 1;", MttoAsig.class);
            q.setParameter(1, fecha_format);
            q.setParameter(2, servbus);
            return (MttoAsig) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public MttoAsig findByIdVehiculo(int idVehiculo, Date fecha, int idMttoAsig) {
        String fecha_format = new SimpleDateFormat("yyyy-MM-dd").format(fecha);
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    m.*\n"
                    + "FROM\n"
                    + "    mtto_asig m\n"
                    + "WHERE\n"
                    + "    m.fecha = ?1 AND m.id_vehiculo = ?2 AND m.id_mtto_asig<> ?3", MttoAsig.class);
            q.setParameter(1, fecha_format);
            q.setParameter(2, idVehiculo);
            q.setParameter(3, idMttoAsig);
            return (MttoAsig) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<MttoAsig> findAsignacionSinServbus(Date fechaPost) {
        String fecha_format = new SimpleDateFormat("yyyy-MM-dd").format(fechaPost);
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    m.*\n"
                    + "FROM\n"
                    + "    mtto_asig m\n"
                    + "WHERE\n"
                    + "    m.servbus IS NULL AND m.fecha=?1;", MttoAsig.class);
            q.setParameter(1, fecha_format);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ResumenAsignadosPorPatio> getResumenAsignados(Date fecha) {
        try {
            String sql = "SELECT \n"
                    + "    psp.name AS name, COUNT(ma.id_from_depot) AS asignados\n"
                    + "FROM\n"
                    + "    mtto_asig ma\n"
                    + "        INNER JOIN\n"
                    + "    prg_stop_point psp ON psp.id_prg_stoppoint = ma.id_from_depot\n"
                    + "WHERE\n"
                    + "    ma.fecha = ?1\n"
                    + "        AND ma.id_vehiculo IS NOT NULL\n"
                    + "        AND ma.servbus IS NOT NULL\n"
                    + "GROUP BY 1";
            Query query = em.createNativeQuery(sql, "ResumenAsignadosPorPatioMapping");
            query.setParameter(1, Util.toDate(Util.dateFormat(fecha)));
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
