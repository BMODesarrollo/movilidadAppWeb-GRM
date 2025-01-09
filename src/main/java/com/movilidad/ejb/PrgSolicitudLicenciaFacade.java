/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudLicencia;
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
public class PrgSolicitudLicenciaFacade extends AbstractFacade<PrgSolicitudLicencia> implements PrgSolicitudLicenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudLicenciaFacade() {
        super(PrgSolicitudLicencia.class);
    }

    @Override
    public List<PrgSolicitudLicencia> findAllEstadoReg() {
        try {
            String sql = "SELECT * FROM prg_solicitud_licencia WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgSolicitudLicencia.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgSolicitudLicencia> getTodoPorFecha(Date desde, Date hasta) {
        try {
            String cDesde = Util.dateFormat(desde);
            String cHasta = Util.dateFormat(hasta);
            String sql = "SELECT ps.* \n"
                    + "FROM prg_solicitud_licencia ps \n"
                    + "INNER JOIN prg_token p ON ps.id_prg_token = p.id_prg_token \n"
                    + "WHERE p.solicitado BETWEEN ?1 AND ?2 \n"
                    + "AND p.estado_reg = 0 \n"
                    + "AND ps.estado_reg = 0";
            Query q = em.createNativeQuery(sql, PrgSolicitudLicencia.class);
            q.setParameter(1, cDesde);
            q.setParameter(2, cHasta);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
