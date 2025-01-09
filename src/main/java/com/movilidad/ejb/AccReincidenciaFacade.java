/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AccReincidencia;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class AccReincidenciaFacade extends AbstractFacade<AccReincidencia> implements AccReincidenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AccReincidenciaFacade() {
        super(AccReincidencia.class);
    }

    /**
     * Obtener objeto List de AccReincidencia por estadoReg = 0
     *
     * @return List AccReincidencia
     */
    @Override
    public List<AccReincidencia> findAllEstadoReg() {
        String sql = "SELECT * FROM acc_reincidencia WHERE estado_reg = 0";
        Query q = em.createNativeQuery(sql, AccReincidencia.class);
        return q.getResultList();
    }

    @Override
    public AccReincidencia findByDiasAndIdNovTipoDetalles(Integer idAccReincidencia, Integer idNovTipoDetalle) {
        try {
            String sql = "SELECT * "
                    + "FROM acc_reincidencia "
                    + "WHERE id_novedad_tipo_detalle = ?1 "
                    + "AND id_acc_reincidencia <> ?2 "
                    + "AND estado_reg = 0";
            Query q = em.createNativeQuery(sql, AccReincidencia.class);
            q.setParameter(1, idNovTipoDetalle);
            q.setParameter(2, idAccReincidencia);
            return (AccReincidencia) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
