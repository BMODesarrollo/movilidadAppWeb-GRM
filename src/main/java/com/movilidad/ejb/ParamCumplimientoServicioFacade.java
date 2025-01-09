/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ParamCumplimientoServicio;
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
public class ParamCumplimientoServicioFacade extends AbstractFacade<ParamCumplimientoServicio> implements ParamCumplimientoServicioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParamCumplimientoServicioFacade() {
        super(ParamCumplimientoServicio.class);
    }

    @Override
    public List<ParamCumplimientoServicio> findAll() {
        try {
            String sql = "select * from param_cumplimiento_servicio where estado_reg = 0;";
            Query query = em.createNativeQuery(sql, ParamCumplimientoServicio.class);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public ParamCumplimientoServicio verificarRepetidos(int periodo, String tipoDia, String nombre) {
        try {
            String sql = "select * from param_cumplimiento_servicio where periodo = ?1 \n"
                    + "and tipo_dia = ?2 and nombre = ?3;";
            Query query = em.createNativeQuery(sql, ParamCumplimientoServicio.class);
            query.setParameter(1, periodo);
            query.setParameter(2, tipoDia);
            query.setParameter(3, nombre);

            return (ParamCumplimientoServicio) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
