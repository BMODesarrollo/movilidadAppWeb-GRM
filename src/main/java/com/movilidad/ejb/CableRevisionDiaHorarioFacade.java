/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableRevisionDiaHorario;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Carlos Ballestas
 */
@Stateless
public class CableRevisionDiaHorarioFacade extends AbstractFacade<CableRevisionDiaHorario> implements CableRevisionDiaHorarioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableRevisionDiaHorarioFacade() {
        super(CableRevisionDiaHorario.class);
    }

    @Override
    public CableRevisionDiaHorario findByHora(String hora, Integer idRegistro) {
        try {
            String sql = "SELECT * FROM cable_revision_dia_horario where "
                    + "hora = ?1 and id_cable_revision_dia_horario <> ?2 and "
                    + "estado_reg = 0;";

            Query query = em.createNativeQuery(sql, CableRevisionDiaHorario.class);
            query.setParameter(1, hora);
            query.setParameter(2, idRegistro);

            return (CableRevisionDiaHorario) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<CableRevisionDiaHorario> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("CableRevisionDiaHorario.findByEstadoReg", CableRevisionDiaHorario.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
