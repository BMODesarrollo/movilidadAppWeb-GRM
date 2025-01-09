/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DanoFlotaSolucionValor;
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
public class DanoFlotaSolucionValorFacade extends AbstractFacade<DanoFlotaSolucionValor> implements DanoFlotaSolucionValorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DanoFlotaSolucionValorFacade() {
        super(DanoFlotaSolucionValor.class);
    }

    @Override
    public List<DanoFlotaSolucionValor> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    dano_flota_solucion_valor p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0 and p.vigencia_desde < now() and p.vigencia_hasta > now();", DanoFlotaSolucionValor.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DanoFlotaSolucionValor> findPieces(String piezas, Integer vehiculoTipoId) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "p.*\n"
                    + "FROM\n"
                    + "dano_flota_solucion_valor p\n"
                    + "INNER JOIN dano_flota_componente p2 on p.id_dano_componente=p2.id_dano_componente\n"
                    + "INNER JOIN dano_flota_componente_grupo p3 on p2.id_componente_grupo = p3.id_componente_grupo\n"
                    + "WHERE\n"
                    + "p.estado_reg = 0 and p.vigencia_desde < now() \n"
                    + "and p.vigencia_hasta > now() and p2.nombre in (" + piezas + ") \n"
                    + "and p3.id_vehiculo_tipo=?1;", DanoFlotaSolucionValor.class);
            q.setParameter(1, vehiculoTipoId);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<DanoFlotaSolucionValor> findSolucionesByComponente(Long idComponente) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "p.*\n"
                    + "FROM\n"
                    + "dano_flota_solucion_valor p\n"
                    + "WHERE\n"
                    + "p.estado_reg = 0 and p.vigencia_desde < now() \n"
                    + "and p.vigencia_hasta > now() and p.id_dano_componente=?1;", DanoFlotaSolucionValor.class);
            q.setParameter(1, idComponente);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
