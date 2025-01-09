/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Vehiculo;
import com.movilidad.model.VehiculoIdf;
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
public class VehiculoIdfFacade extends AbstractFacade<VehiculoIdf> implements VehiculoIdfFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoIdfFacade() {
        super(VehiculoIdf.class);
    }

    @Override
    public Vehiculo findVehiculo(int id) {
        Query query = em.createQuery("SELECT v FROM Vehiculo v where "
                + "v.idVehiculo = :id", Vehiculo.class)
                .setParameter("id", id);
        return (Vehiculo) query.getSingleResult();
    }

    @Override
    public List<VehiculoIdf> findByFecha(Date fecha) {
        Query query = em.createNamedQuery("VehiculoIdf.findByFecha");
        query.setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public VehiculoIdf findByFechaAndIdVehiculo(Date fecha, int idVehiculo) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    v.*\n"
                    + "FROM\n"
                    + "    vehiculo_idf v\n"
                    + "WHERE\n"
                    + "    v.id_vehiculo = ?2\n"
                    + "        AND v.fecha <= ?1\n"
                    + "        AND v.fecha_fin >= ?1\n"
                    + "LIMIT 1;", VehiculoIdf.class);
            q.setParameter(1, Util.dateFormat(fecha));
            q.setParameter(2, idVehiculo);
            return (VehiculoIdf) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public boolean verificarSubida(Date fecha, Date fechaFin) {
        Query query = em.createQuery("SELECT k from VehiculoIdf k WHERE "
                + "k.fecha = :fecha AND k.fecha_fin = :fechaFin");
        query.setParameter("fecha", Util.toDate(Util.dateFormat(fecha)));
        query.setParameter("fechaFin", Util.toDate(Util.dateFormat(fechaFin)));
        return query.getResultList().isEmpty();
    }

    @Override
    public List<VehiculoIdf> findByRangoFecha(Date fechaInicio, Date fechaFin) {
        try {
            String sql = "select * from vehiculo_idf where fecha = ?1 and fecha_fin =?2";

            Query query = em.createNativeQuery(sql, VehiculoIdf.class);
            query.setParameter(1, Util.toDate(Util.dateFormat(fechaInicio)));
            query.setParameter(2, Util.toDate(Util.dateFormat(fechaFin)));

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
