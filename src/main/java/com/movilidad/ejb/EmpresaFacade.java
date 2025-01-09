/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.Empresa;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpresaFacade extends AbstractFacade<Empresa> implements EmpresaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpresaFacade() {
        super(Empresa.class);
    }

    
    

 @Override
    public Empresa findByNit(String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<Empresa> query = em.createQuery(
                        "SELECT e FROM Empresa e WHERE e.nit= ?1",
                        Empresa.class);
                return query.setParameter(1, value).getSingleResult();
            }
            TypedQuery<Empresa> query = em.createQuery(
                    "SELECT e FROM Empresa e WHERE e.nit= ?1 AND"
                            + " e.idEmpresa<>?2", Empresa.class);
            query.setParameter(1, value);
            query.setParameter(2, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
