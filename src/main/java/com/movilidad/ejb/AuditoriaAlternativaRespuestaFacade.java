/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaAlternativaRespuesta;
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
public class AuditoriaAlternativaRespuestaFacade extends AbstractFacade<AuditoriaAlternativaRespuesta> implements AuditoriaAlternativaRespuestaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaAlternativaRespuestaFacade() {
        super(AuditoriaAlternativaRespuesta.class);
    }

    @Override
    public List<AuditoriaAlternativaRespuesta> findByIdAuditoriaPregunta(int idTipoRespuesta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ar.*\n"
                    + "FROM\n"
                    + "    auditoria_alternativa_respuesta ar\n"
                    + "WHERE\n"
                    + "    ar.id_auditoria_tipo_respuesta = ?1 "
                    + "order by ar.numero asc; ", AuditoriaAlternativaRespuesta.class);
            q.setParameter(1, idTipoRespuesta);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
