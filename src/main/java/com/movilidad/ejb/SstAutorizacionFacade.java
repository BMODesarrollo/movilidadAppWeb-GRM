/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstAutorizacion;
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
public class SstAutorizacionFacade extends AbstractFacade<SstAutorizacion> implements SstAutorizacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstAutorizacionFacade() {
        super(SstAutorizacion.class);
    }

    @Override
    public List<SstAutorizacion> findAllEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstAutorizacion.findByEstadoReg", SstAutorizacion.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Retorna la lista de objetos de SstAutorizacion donde conicida con el
     * parametro idSstEmpresa
     *
     * @param idSstEmpresa Identificador unico para el objeto SstEmpresa
     * @return objeto List SstAutorizacion, null en caso de error
     */
    @Override
    public List<SstAutorizacion> findBySstEmpresa(Integer idSstEmpresa) {
        try {
            String sql = "SELECT * "
                    + "FROM sst_autorizacion "
                    + "WHERE id_sst_empresa = ?1 "
                    + "AND estado_reg = 0";
            Query query = em.createNativeQuery(sql, SstAutorizacion.class);
            query.setParameter(1, idSstEmpresa);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstAutorizacion obtenerAutorizacionEntradaSalida(Date fecha, String cedula) {
        try {
            String sql = "select\n"
                    + "	sa.*\n"
                    + "from\n"
                    + "	sst_autorizacion sa\n"
                    + "inner join sst_empresa_visitante sev on\n"
                    + "	sa.id_sst_empresa_visitante = sev.id_sst_empresa_visitante\n"
                    + "where\n"
                    + "	date(sa.fecha_hora_llegada) >= ?1 and\n"
                    + "	date(sa.fecha_hora_salida) <= ?1\n"
                    + "	and sa.visita_aprobada = 1\n"
                    + "	and sev.numero_documento = ?2;";
            Query query = em.createNativeQuery(sql, SstAutorizacion.class);
            query.setParameter(1, Util.dateFormat(fecha));
            query.setParameter(2, cedula);
            return (SstAutorizacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SstAutorizacion verificarRegistroAutorizacion(Date desde, Date hasta, String cedula) {
        try {
            String sql = "select\n"
                    + "	sa.*\n"
                    + "from\n"
                    + "	sst_autorizacion sa\n"
                    + "inner join sst_empresa_visitante sev on\n"
                    + "	sa.id_sst_empresa_visitante = sev.id_sst_empresa_visitante\n"
                    + "where\n"
                    + "	date(sa.fecha_hora_llegada) >= ?1\n"
                    + "	and date(sa.fecha_hora_salida) <= ?2\n"
                    + "	and sev.numero_documento = ?3 limit 1;";
            Query query = em.createNativeQuery(sql, SstAutorizacion.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, cedula);
            return (SstAutorizacion) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
