/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GenericaPmNovedadIncluir;
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
public class GenericaPmNovedadIncluirFacade extends AbstractFacade<GenericaPmNovedadIncluir> implements GenericaPmNovedadIncluirFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GenericaPmNovedadIncluirFacade() {
        super(GenericaPmNovedadIncluir.class);
    }

    @Override
    public List<GenericaPmNovedadIncluir> getByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_novedad_incluir g\n"
                    + "WHERE\n"
                    + "    g.id_param_area = ?1 AND estado_reg = 0;", GenericaPmNovedadIncluir.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public GenericaPmNovedadIncluir getByIdNovedadTipoDet(int idDet, int idGenericaPmNovedadIncluir) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    g.*\n"
                    + "FROM\n"
                    + "    generica_pm_novedad_incluir g\n"
                    + "WHERE\n"
                    + "    g.id_generica_tipo_detalle = ?1 AND "
                    + "g.idgenerica_pm_novedad_incluir<>?2 AND "
                    + "estado_reg = 0 LIMIT 1;", GenericaPmNovedadIncluir.class);
            q.setParameter(1, idDet);
            q.setParameter(2, idGenericaPmNovedadIncluir);
            return (GenericaPmNovedadIncluir) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
