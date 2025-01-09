/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaTipoRespuesta;
import java.util.ArrayList;
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
public class AuditoriaTipoRespuestaFacade extends AbstractFacade<AuditoriaTipoRespuesta> implements AuditoriaTipoRespuestaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaTipoRespuestaFacade() {
        super(AuditoriaTipoRespuesta.class);
    }

    @Override
    public List<AuditoriaTipoRespuesta> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT al.* "
                    + "FROM auditoria_tipo_respuesta al "
                    + "WHERE al.id_param_area=?1 "
                    + "AND al.estado_reg=0;", AuditoriaTipoRespuesta.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaTipoRespuesta findByAreaIdAuditoriaTipoRespuestaAndCodigo(String codigo, int idTipoRespuesta, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_tipo_respuesta at "
                    + "WHERE at.id_param_area=?3 "
                    + "AND at.estado_reg=0 "
                    + "AND at.codigo=?1 "
                    + "AND at.id_auditoria_tipo_respuesta<>?2;", AuditoriaTipoRespuesta.class);
            q.setParameter(1, codigo);
            q.setParameter(2, idTipoRespuesta);
            q.setParameter(3, idArea);
            return (AuditoriaTipoRespuesta) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
