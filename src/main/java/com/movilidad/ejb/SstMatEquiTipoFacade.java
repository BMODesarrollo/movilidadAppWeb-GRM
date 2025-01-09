/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstMatEquiTipo;
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
public class SstMatEquiTipoFacade extends AbstractFacade<SstMatEquiTipo> implements SstMatEquiTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstMatEquiTipoFacade() {
        super(SstMatEquiTipo.class);
    }

    /**
     * Permite obtener la lista de objetos donde su atributo estado_reg sea
     * igual a 0
     *
     * @return Lista de objetos SstMatEquiTipo, null en caso de error
     */

    @Override
    public List<SstMatEquiTipo> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("SstMatEquiTipo.findByEstadoReg", SstMatEquiTipo.class);
            query.setParameter("estadoReg", 0);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
