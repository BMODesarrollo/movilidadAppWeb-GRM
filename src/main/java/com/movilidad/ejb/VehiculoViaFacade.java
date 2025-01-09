/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.VehiculoVia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Cesar Mercado
 */
@Stateless
public class VehiculoViaFacade extends AbstractFacade<VehiculoVia> implements VehiculoViaFacadeLocal {
    
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public VehiculoViaFacade() {
        super(VehiculoVia.class);
    }
    
    @Override
    public VehiculoVia findByIdVehiculo(Integer idVehiculo) {
        try {
            String sql = "SELECT * FROM vehiculo_via WHERE id_vehiculo = ?1 LIMIT 1";
            Query q = em.createNativeQuery(sql, VehiculoVia.class);
            q.setParameter(1, idVehiculo);
            return (VehiculoVia) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<VehiculoVia> findByIdUnidadFuncional(int idGopUf) {
        String uf = idGopUf != 0 ? "WHERE id_gop_uf = " + idGopUf : " ";
        String sql = "SELECT * FROM vehiculo_via " + uf;
        Query q = em.createNativeQuery(sql, VehiculoVia.class);
        return q.getResultList();
    }
}
