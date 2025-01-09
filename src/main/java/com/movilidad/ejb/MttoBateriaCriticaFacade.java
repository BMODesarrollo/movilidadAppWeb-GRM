/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoBateriaCritica;
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
public class MttoBateriaCriticaFacade extends AbstractFacade<MttoBateriaCritica> implements MttoBateriaCriticaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoBateriaCriticaFacade() {
        super(MttoBateriaCritica.class);
    }

    /**
     * Obtener objeto List de MttoBateriaCritica por estadoReg = 0
     *
     * @return List MttoBateriaCritica
     */
    @Override
    public List<MttoBateriaCritica> findAllEstadoReg() {
        String sql = "SELECT * FROM mtto_bateria_critica WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, MttoBateriaCritica.class);
        return q.getResultList();
    }

    @Override
    public MttoBateriaCritica findByCargaAndIdTipoVehiculo(Integer carga, Integer idVehiculoTipo, Integer idMttoBateriaCritica) {
        try {
            String sql = "SELECT * "
                    + "FROM mtto_bateria_critica "
                    + "WHERE carga = ?1 "
                    + "AND id_vehiculo_tipo = ?2 "
                    + "AND id_mtto_bateria_critica <> ?3 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, MttoBateriaCritica.class);
            q.setParameter(1, carga);
            q.setParameter(2, idVehiculoTipo);
            q.setParameter(3, idMttoBateriaCritica);
            return (MttoBateriaCritica) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
