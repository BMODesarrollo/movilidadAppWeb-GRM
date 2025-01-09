/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccNovedadInfraestrucTipoDocumentos;
import com.movilidad.model.AccNovedadInfrastucEstado;
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
public class AccNovedadInfraestrucTipoDocumentosFacade extends AbstractFacade<AccNovedadInfraestrucTipoDocumentos> implements AccNovedadInfraestrucTipoDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccNovedadInfraestrucTipoDocumentosFacade() {
        super(AccNovedadInfraestrucTipoDocumentos.class);
    }

    @Override
    public AccNovedadInfraestrucTipoDocumentos findByNombre(String nombre, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM acc_novedad_infraestruc_tipo_documentos where nombre = ?1 and id_acc_novedad_infraestruc_tipo_documentos <> ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AccNovedadInfrastucEstado.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idRegistro);

            return (AccNovedadInfraestrucTipoDocumentos) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AccNovedadInfraestrucTipoDocumentos> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AccNovedadInfraestrucTipoDocumentos.findByEstadoReg", AccNovedadInfraestrucTipoDocumentos.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
