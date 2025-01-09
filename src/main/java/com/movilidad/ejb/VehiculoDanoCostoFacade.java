/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoDanoCosto;
import com.movilidad.utils.Util;
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
public class VehiculoDanoCostoFacade extends AbstractFacade<VehiculoDanoCosto> implements VehiculoDanoCostoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VehiculoDanoCostoFacade() {
        super(VehiculoDanoCosto.class);
    }

    @Override
    public VehiculoDanoCosto verificarRegistro(Integer idVehiculoDanoCosto, Integer idVehiculoDanoSeveridad, Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + " *\n"
                    + " FROM\n"
                    + " vehiculo_dano_costo\n"
                    + " WHERE\n"
                    + " id_vehiculo_dano_costo <> ?1\n"
                    + " AND id_vehiculo_dano_severidad = ?2\n"
                    + " AND ((?3 BETWEEN desde AND hasta\n"
                    + " OR ?4 BETWEEN desde AND hasta)\n"
                    + " OR (desde BETWEEN ?3 AND ?4\n"
                    + " OR hasta BETWEEN ?3 AND ?4))\n"
                    + " AND estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, VehiculoDanoCosto.class);
            query.setParameter(1, idVehiculoDanoCosto);
            query.setParameter(2, idVehiculoDanoSeveridad);
            query.setParameter(3, Util.dateFormat(desde));
            query.setParameter(4, Util.dateFormat(hasta));

            return (VehiculoDanoCosto) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<VehiculoDanoCosto> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("VehiculoDanoCosto.findByEstadoReg", VehiculoDanoCosto.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public VehiculoDanoCosto findBySeveridadAndFechas(Integer idVehiculoDanoSeveridad, Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + " *\n"
                    + " FROM\n"
                    + " vehiculo_dano_costo\n"
                    + " WHERE\n"
                    + " id_vehiculo_dano_severidad = ?1\n"
                    + " AND ((?2 BETWEEN desde AND hasta\n"
                    + " OR ?4 BETWEEN desde AND hasta)\n"
                    + " OR (desde BETWEEN ?2 AND ?3\n"
                    + " OR hasta BETWEEN ?2 AND ?3))\n"
                    + " AND estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, VehiculoDanoCosto.class);
            query.setParameter(1, idVehiculoDanoSeveridad);
            query.setParameter(2, Util.dateFormat(desde));
            query.setParameter(3, Util.dateFormat(hasta));

            return (VehiculoDanoCosto) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
