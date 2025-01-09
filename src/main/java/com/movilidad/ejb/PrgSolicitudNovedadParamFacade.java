/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSolicitudNovedadParam;
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
public class PrgSolicitudNovedadParamFacade extends AbstractFacade<PrgSolicitudNovedadParam> implements PrgSolicitudNovedadParamFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSolicitudNovedadParamFacade() {
        super(PrgSolicitudNovedadParam.class);
    }

    @Override
    public List<PrgSolicitudNovedadParam> findByEstadoReg() {
        try {
            Query query = em.createNamedQuery("PrgSolicitudNovedadParam.findByEstadoReg", PrgSolicitudNovedadParam.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void aumentarConsecutivoLicencia(Integer idPrgSolicitudNovedadParam) {
        try {
            String sql = "update prg_solicitud_novedad_param \n"
                    + "set consec_licencia = consec_licencia + 1\n"
                    + "where id_prg_solicitud_novedad_param = ?1";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idPrgSolicitudNovedadParam);

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aumentarConsecutivoCambio(Integer idPrgSolicitudNovedadParam) {
        try {
            String sql = "update prg_solicitud_novedad_param \n"
                    + "set consec_cambio_turno = consec_cambio_turno + 1\n"
                    + "where id_prg_solicitud_novedad_param = ?1";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idPrgSolicitudNovedadParam);

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aumentarConsecutivoPermiso(Integer idPrgSolicitudNovedadParam) {
        try {
            String sql = "update prg_solicitud_novedad_param \n"
                    + "set consec_permisos = consec_permisos + 1\n"
                    + "where id_prg_solicitud_novedad_param = ?1";
            Query query = em.createNativeQuery(sql);
            query.setParameter(1, idPrgSolicitudNovedadParam);

            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
