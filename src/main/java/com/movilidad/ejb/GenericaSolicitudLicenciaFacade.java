/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaSolicitudLicencia;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class GenericaSolicitudLicenciaFacade extends AbstractFacade<GenericaSolicitudLicencia> implements GenericaSolicitudLicenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaSolicitudLicenciaFacade() {
        super(GenericaSolicitudLicencia.class);
    }

    @Override
    public List<GenericaSolicitudLicencia> findEstadoReg(Integer idArea) {
        try {
            String sql = "SELECT * FROM generica_solicitud_licencia WHERE id_param_area = ?1 AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaSolicitudLicencia.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<GenericaSolicitudLicencia> getTodoPorFecha(Date desde, Date hasta, Integer idArea) {
        try {
            String cDesde = Util.dateFormat(desde);
            String cHasta = Util.dateFormat(hasta);
            String sql = "SELECT gs.* \n"
                    + "FROM generica_solicitud_licencia gs \n"
                    + "INNER JOIN generica_token gt ON gs.id_generica_token = gt.id_generica_token \n"
                    + "WHERE gt.solicitado BETWEEN ?1 AND ?2 \n"
                    + "AND gs.id_param_area = ?3 \n"
                    + "AND gt.estado_reg = 0 \n"
                    + "AND gs.estado_reg = 0";
            Query q = em.createNativeQuery(sql, GenericaSolicitudLicencia.class);
            q.setParameter(1, cDesde);
            q.setParameter(2, cHasta);
            q.setParameter(3, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
