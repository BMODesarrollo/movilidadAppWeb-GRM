/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmNovedadExcluir;
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
public class GenericaPmNovedadExcluirFacade extends AbstractFacade<GenericaPmNovedadExcluir> implements GenericaPmNovedadExcluirFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmNovedadExcluirFacade() {
        super(GenericaPmNovedadExcluir.class);
    }

    @Override
    public List<GenericaPmNovedadExcluir> getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_novedad_excluir g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaPmNovedadExcluir.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaPmNovedadExcluir getByIdNovedadTipoDet(int idDet, int idGenericaPmNovedadExcluir) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_novedad_excluir g\n"
                    + "WHERE\n"
                    + "    g.id_generica_tipo_detalle = ?1 AND "
                    + "g.idgenerica_pm_novedad_excluir<>?2 AND "
                    + "estado_reg = 0 LIMIT 1;", GenericaPmNovedadExcluir.class);
            q.setParameter(1, idDet);
            q.setParameter(2, idGenericaPmNovedadExcluir);
            return (GenericaPmNovedadExcluir) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
