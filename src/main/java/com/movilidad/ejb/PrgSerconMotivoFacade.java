/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgSerconMotivo;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class PrgSerconMotivoFacade extends AbstractFacade<PrgSerconMotivo> implements PrgSerconMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgSerconMotivoFacade() {
        super(PrgSerconMotivo.class);
    }

    @Override
    public List<PrgSerconMotivo> findAllEstadoRegistro() {
        try {
            Query q = em.createNativeQuery("SELECT * FROM prg_sercon_motivo WHERE estado_reg = 0", PrgSerconMotivo.class);
            return q.getResultList();
        } catch (Exception e) {
            System.out.println("Error en FacadePrgTcSerconMotivo");
            return null;
        }
    }
    
    @Override
    public PrgSerconMotivo findByName(String name){
        try {
            Query q = em.createNativeQuery("SELECT * FROM prg_sercon_motivo WHERE descripcion = ?1 AND estado_reg = 0", PrgSerconMotivo.class);
            q.setParameter(1, name);
            return (PrgSerconMotivo) q.getSingleResult();
        } catch (Exception e) {
            System.out.println("Error en FacadePrgTcSerconMotivo");
            return null;
        }
    }

}
