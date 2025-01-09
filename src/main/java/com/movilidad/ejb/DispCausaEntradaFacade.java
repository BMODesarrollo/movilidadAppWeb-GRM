/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispCausaEntrada;
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
public class DispCausaEntradaFacade extends AbstractFacade<DispCausaEntrada> implements DispCausaEntradaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispCausaEntradaFacade() {
        super(DispCausaEntrada.class);
    }

    @Override
    public List<DispCausaEntrada> findAll() {
        Query q = em.createNamedQuery("DispCausaEntrada.findAll", DispCausaEntrada.class);
        return q.getResultList();
    }

    @Override
    public DispCausaEntrada findByNombreByIdDispSistema(String nombre, int id, int idDispSistema) {
        try {
            Query q = em.createNamedQuery("DispCausaEntrada.findByNombreByIdDispSistema", DispCausaEntrada.class);
            q.setParameter("nombre", nombre);
            q.setParameter("id", id);
            q.setParameter("idDispSistema", idDispSistema);
            q.setParameter("estadoReg", 0);
            return (DispCausaEntrada) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DispCausaEntrada> findByEstadoReg() {
        Query q = em.createNamedQuery("DispCausaEntrada.findByEstadoReg", DispCausaEntrada.class);
        q.setParameter("estadoReg", 0);
        return q.getResultList();
    }

    @Override
    public List<DispCausaEntrada> findAllByIdDispSistema(int idDispSistema) {
        Query q = em.createNativeQuery("SELECT d.* FROM disp_causa_entrada d where "
                + "d.id_disp_sistema= ?1 AND d.estado_reg=0;", DispCausaEntrada.class);
        q.setParameter(1, idDispSistema);
        return q.getResultList();
    }

}
