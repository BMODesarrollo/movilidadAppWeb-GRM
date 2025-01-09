/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.SstVehiculoTipo;
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
public class SstVehiculoTipoFacade extends AbstractFacade<SstVehiculoTipo> implements SstVehiculoTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SstVehiculoTipoFacade() {
        super(SstVehiculoTipo.class);
    }

    /**
     * Permite obtener la lista de objetos donde su atributo estado_reg sea
     * igual a 0
     *
     * @return Lista de objetos SstVehiculoTipo, null en caso de error
     */
    @Override
    public List<SstVehiculoTipo> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM sst_vehiculo_tipo "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, SstVehiculoTipo.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
