/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaComponente;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Julián Arévalo
 */
@Stateless
public class DanoFlotaComponenteFacade extends AbstractFacade<DanoFlotaComponente> implements DanoFlotaComponenteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DanoFlotaComponenteFacade() {
        super(DanoFlotaComponente.class);
    }

    @Override
    public List<DanoFlotaComponente> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    dano_flota_componente p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", DanoFlotaComponente.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
    @Override
    public List<DanoFlotaComponente> findPieces(String piezas, Integer vehiculoTipoId) {
        try {
            Query q = em.createNativeQuery("SELECT \n" +
            "p.*\n" +
            "FROM\n" +
            "dano_flota_componente p\n" +
            "INNER JOIN dano_flota_componente_grupo p1 on p.id_componente_grupo = p1.id_componente_grupo\n" +
            "WHERE\n" +
            "p.estado_reg = 0 AND p.nombre in (" +piezas+")"
                    + "AND p1.id_vehiculo_tipo=?1;", DanoFlotaComponente.class);
            q.setParameter(1, vehiculoTipoId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
