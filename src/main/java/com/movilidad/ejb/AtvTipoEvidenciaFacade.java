/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoTipo;
import com.movilidad.model.AtvTipoEvidencia;
import java.util.ArrayList;
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
public class AtvTipoEvidenciaFacade extends AbstractFacade<AtvTipoEvidencia> implements AtvTipoEvidenciaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvTipoEvidenciaFacade() {
        super(AtvTipoEvidencia.class);
    }

    @Override
    public List<AtvTipoEvidencia> findAllByEstadoReg() {
        try {
            Query query = em.createNamedQuery("AtvTipoEvidencia.findByEstadoReg", AtvTipoEvidencia.class);
            query.setParameter("estadoReg", 0);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public AtvTipoEvidencia findByNombre(Integer idRegistro, String nombre) {
        try {
            String sql = "SELECT * FROM atv_tipo_evidencia where id_atv_tipo_evidencia <> ?1 and nombre = ?2 and estado_reg = 0;";
            Query query = em.createNativeQuery(sql, AtvTipoEvidencia.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, nombre);

            return (AtvTipoEvidencia) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
