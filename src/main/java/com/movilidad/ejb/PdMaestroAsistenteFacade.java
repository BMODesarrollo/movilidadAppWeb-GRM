/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PdMaestroAsistente;
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
public class PdMaestroAsistenteFacade extends AbstractFacade<PdMaestroAsistente> implements PdMaestroAsistenteFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PdMaestroAsistenteFacade() {
        super(PdMaestroAsistente.class);
    }

    @Override
    public List<PdMaestroAsistente> findByIdMaestro(Integer idMaestro) {
        try {
            String sql = "SELECT * FROM pd_maestro_asistente where id_pd_maestro = ?1 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PdMaestroAsistente.class);
            query.setParameter(1, idMaestro);

            return (List<PdMaestroAsistente>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
