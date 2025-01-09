/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ConsumoEnergia;
import com.movilidad.utils.Util;
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
public class ConsumoEnergiaFacade extends AbstractFacade<ConsumoEnergia> implements ConsumoEnergiaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConsumoEnergiaFacade() {
        super(ConsumoEnergia.class);
    }

    @Override
    public ConsumoEnergia findByFechaAndEstado(Integer idRegistro, Date fecha, Integer idEstado) {
        try {
            String sql = "SELECT * FROM consumo_energia where id_consumo_energia <> ?1 and date(fecha_hora) = ?2 and id_consumo_energia_estado = ?3 and estado_reg = 0 limit 1;";
            Query query = em.createNativeQuery(sql, ConsumoEnergia.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, idEstado);

            return (ConsumoEnergia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ConsumoEnergia> findAllByFecha(Date fechaInicio, Date fechaFin) {
        try {
            String sql = "SELECT * FROM consumo_energia where date(fecha_hora) BETWEEN ?1 AND ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ConsumoEnergia.class);
            query.setParameter(1, Util.dateFormat(fechaInicio));
            query.setParameter(2, Util.dateFormat(fechaFin));

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConsumoEnergia findByFechaAnterior(Date fecha) {
        try {
            String sql = "SELECT * FROM consumo_energia where date(fecha_hora) = ?1 and estado_reg = 0 order by id_consumo_energia desc limit 1;";
            Query query = em.createNativeQuery(sql, ConsumoEnergia.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return (ConsumoEnergia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConsumoEnergia findByFecha(Date fecha) {
        try {
            String sql = "SELECT * FROM consumo_energia where date(fecha_hora) = ?1 and estado_reg = 0 limit 1;";
            Query query = em.createNativeQuery(sql, ConsumoEnergia.class);
            query.setParameter(1, Util.dateFormat(fecha));

            return (ConsumoEnergia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ConsumoEnergia findByFechaSiguiente(Date fecha) {
        try {
            String sql = "SELECT * FROM consumo_energia where timestamp(fecha_hora) > ?1 and estado_reg = 0 order by fecha_hora limit 1;";
            Query query = em.createNativeQuery(sql, ConsumoEnergia.class);
            query.setParameter(1, Util.dateTimeFormat(fecha));

            return (ConsumoEnergia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
