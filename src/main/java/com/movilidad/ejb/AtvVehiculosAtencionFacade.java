/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvVehiculosAtencion;
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
public class AtvVehiculosAtencionFacade extends AbstractFacade<AtvVehiculosAtencion> implements AtvVehiculosAtencionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvVehiculosAtencionFacade() {
        super(AtvVehiculosAtencion.class);
    }

    @Override
    public List<AtvVehiculosAtencion> findByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_vehiculos_atencion\n"
                + "WHERE\n"
                + "    estado_reg = 0;", AtvVehiculosAtencion.class);
        return q.getResultList();
    }

    @Override
    public AtvVehiculosAtencion findByplacaAndIdPrestador(String placa, int idAtvPrestador, int idAtvVehiculosAtencion) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_vehiculos_atencion\n"
                    + "WHERE\n"
                    + "    id_atv_prestador = ?2 AND placa = ?1\n"
                    + "        AND id_atv_vehiculos_atencion <> ?3\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;", AtvVehiculosAtencion.class);
            q.setParameter(1, placa);
            q.setParameter(2, idAtvPrestador);
            q.setParameter(3, idAtvVehiculosAtencion);
            return (AtvVehiculosAtencion) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

}
