/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccTipoUsuario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class CableAccTipoUsuarioFacade extends AbstractFacade<CableAccTipoUsuario> implements CableAccTipoUsuarioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccTipoUsuarioFacade() {
        super(CableAccTipoUsuario.class);
    }

    @Override
    public List<CableAccTipoUsuario> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM cable_acc_tipo_usuario "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccTipoUsuario.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
