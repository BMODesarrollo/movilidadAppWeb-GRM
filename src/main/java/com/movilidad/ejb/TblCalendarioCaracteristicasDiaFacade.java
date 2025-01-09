/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblCalendarioCaracteristicasDia;
import java.util.ArrayList;
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
public class TblCalendarioCaracteristicasDiaFacade extends AbstractFacade<TblCalendarioCaracteristicasDia> implements TblCalendarioCaracteristicasDiaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblCalendarioCaracteristicasDiaFacade() {
        super(TblCalendarioCaracteristicasDia.class);
    }

    @Override
    public TblCalendarioCaracteristicasDia findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM tbl_calendario_caracteristicas_dia where nombre = ?1 and id_tbl_calendario_caracteristica_dia <> ?2 and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, TblCalendarioCaracteristicasDia.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (TblCalendarioCaracteristicasDia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TblCalendarioCaracteristicasDia> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM tbl_calendario_caracteristicas_dia where estado_reg = 0;";

            Query query = em.createNativeQuery(sql, TblCalendarioCaracteristicasDia.class);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<TblCalendarioCaracteristicasDia> findAllByCampo(String campo) {
        try {
            String sql_campo = campo.equals("afecta_tec") ? " afecta_tec = 1 AND\n" : " afecta_prg = 1 AND\n";
            String sql = "SELECT * FROM tbl_calendario_caracteristicas_dia where "
                    + sql_campo
                    + "estado_reg = 0;";

            Query query = em.createNativeQuery(sql, TblCalendarioCaracteristicasDia.class);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
