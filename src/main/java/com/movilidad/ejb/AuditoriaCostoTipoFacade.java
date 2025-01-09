/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaCostoTipo;
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
public class AuditoriaCostoTipoFacade extends AbstractFacade<AuditoriaCostoTipo> implements AuditoriaCostoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaCostoTipoFacade() {
        super(AuditoriaCostoTipo.class);
    }

    @Override
    public AuditoriaCostoTipo findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM auditoria_costo_tipo where ";
            sql += "id_auditoria_costo_tipo <> ?1 and nombre = ?2 ";
            sql += "and estado_reg = 0 LIMIT 1;";
            
            Query query = em.createNativeQuery(sql, AuditoriaCostoTipo.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (AuditoriaCostoTipo) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AuditoriaCostoTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AuditoriaCostoTipo.findByEstadoReg", AuditoriaCostoTipo.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
