/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuNovedad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar.beltran
 */
@Stateless
public class PlaRecuNovedadFacade extends AbstractFacade<PlaRecuNovedad> implements PlaRecuNovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuNovedadFacade() {
        super(PlaRecuNovedad.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PlaRecuNovedad> findAll() {
        try {
            String sql = "SELECT * FROM pla_recu_novedad WHERE estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuNovedad.class);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public PlaRecuNovedad findNovedad(PlaRecuNovedad plaRecuNovedad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PlaRecuNovedad findByName(String name) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_novedad\n"
                    + "WHERE\n"
                    + "     nombre = ?1 \n"
                    + "     AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuNovedad.class);
            query.setParameter(1, name);
            return (PlaRecuNovedad)query.getSingleResult();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PlaRecuNovedad findById(String id) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_novedad\n"
                    + "WHERE\n"
                    + "     id_pla_recu_novedad = ?1 \n"
                    + "     AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuNovedad.class);
            query.setParameter(1, id);
            return (PlaRecuNovedad)query.getSingleResult();    
        } catch (Exception e) {
            return null;
        }
    }
    
}
