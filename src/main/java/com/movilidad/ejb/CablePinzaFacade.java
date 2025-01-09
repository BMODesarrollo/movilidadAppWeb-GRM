/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableCabina;
import com.movilidad.model.CablePinza;
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
public class CablePinzaFacade extends AbstractFacade<CablePinza> implements CablePinzaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CablePinzaFacade() {
        super(CablePinza.class);
    }

    @Override
    public List<CablePinza> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CablePinza.findByEstadoReg", CablePinza.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public CablePinza findByCodigo(String codigo, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_pinza where id_cable_pinza <> ?1 and codigo = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CablePinza.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, codigo);

            return (CablePinza) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CablePinza findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_pinza where id_cable_pinza <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, CablePinza.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (CablePinza) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public CablePinza verificarRangoFechas(Date fecha, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_pinza \n"
                    + "where ?1 BETWEEN fecha_ini_op and fecha_fin_op \n"
                    + "and id_cable_pinza <> ?2\n"
                    + "and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, CablePinza.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, idRegistro);

            return (CablePinza) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
