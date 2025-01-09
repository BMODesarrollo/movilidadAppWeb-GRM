/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PmNovedadIncluir;
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
public class PmNovedadIncluirFacade extends AbstractFacade<PmNovedadIncluir> implements PmNovedadIncluirFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmNovedadIncluirFacade() {
        super(PmNovedadIncluir.class);
    }

    @Override
    public List<PmNovedadIncluir> getAllActivo() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    p.*\n"
                    + "FROM\n"
                    + "    pm_novedad_incluir p\n"
                    + "WHERE\n"
                    + "    p.estado_reg = 0;", PmNovedadIncluir.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public PmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idPmNovedadIncluir) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    pm_novedad_incluir g\n"
                    + "WHERE\n"
                    + "    g.id_novedad_tipo_detalle = ?1 AND "
                    + "g.id_pm_novedad_incluir<>?2 AND "
                    + "estado_reg = 0 LIMIT 1;", PmNovedadIncluir.class);
            q.setParameter(1, idDet);
            q.setParameter(2, idPmNovedadIncluir);
            return (PmNovedadIncluir) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
