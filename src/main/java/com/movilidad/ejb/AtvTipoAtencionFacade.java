/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvTipoAtencion;
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
public class AtvTipoAtencionFacade extends AbstractFacade<AtvTipoAtencion> implements AtvTipoAtencionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvTipoAtencionFacade() {
        super(AtvTipoAtencion.class);
    }

    @Override
    public List<AtvTipoAtencion> findAllByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_tipo_atencion\n"
                + "WHERE\n"
                + "    estado_reg = 0 ORDER BY nombre ASC;", AtvTipoAtencion.class);
        return q.getResultList();
    }

    @Override
    public AtvTipoAtencion findByNombre(String nombre, int idAtvTipoAtencion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_tipo_atencion\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_atv_tipo_atencion<>?2\n"
                    + "        AND estado_reg = 0 LIMIT 1;", AtvTipoAtencion.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idAtvTipoAtencion);
            return (AtvTipoAtencion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
