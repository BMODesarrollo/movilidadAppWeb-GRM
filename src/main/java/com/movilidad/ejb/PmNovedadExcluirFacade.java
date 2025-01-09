/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadExcluir;
import java.util.ArrayList;
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
public class PmNovedadExcluirFacade extends AbstractFacade<PmNovedadExcluir> implements PmNovedadExcluirFacadeLocal {
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmNovedadExcluirFacade() {
        super(PmNovedadExcluir.class);
    }
 
    @Override
    public List<PmNovedadExcluir> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    pm_novedad_excluir p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", PmNovedadExcluir.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public PmNovedadExcluir getByIdNovedadTipoDet(int idDet, int idPmNovedadExcluir) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    pm_novedad_excluir g\n"
                    + "WHERE\n"
                    + "    g.id_novedad_tipo_detalle = ?1 AND "
                    + "g.id_pm_novedad_excluir<>?2 AND "
                    + "estado_reg = 0 LIMIT 1;", PmNovedadExcluir.class);
            q.setParameter(1, idDet);
            q.setParameter(2, idPmNovedadExcluir);
            return (PmNovedadExcluir) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
