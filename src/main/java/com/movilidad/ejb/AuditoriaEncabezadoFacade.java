/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaEncabezado;
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
public class AuditoriaEncabezadoFacade extends AbstractFacade<AuditoriaEncabezado> implements AuditoriaEncabezadoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaEncabezadoFacade() {
        super(AuditoriaEncabezado.class);
    }

    @Override
    public List<AuditoriaEncabezado> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT ac.* "
                    + "FROM auditoria_encabezado ac "
                    + "WHERE ac.id_param_area=?1 "
                    + "AND ac.estado_reg=0;", AuditoriaEncabezado.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaEncabezado findIdAuditoriaEncabezado(int idAuditoriaEncabezado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ae.*\n"
                    + "FROM\n"
                    + "    auditoria_encabezado ae\n"
                    + "        INNER JOIN\n"
                    + "    auditoria a ON a.id_auditoria_encabezado = ae.id_auditoria_encabezado\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_realizado_por arp ON arp.id_auditoria = a.id_auditoria\n"
                    + "WHERE\n"
                    + "    ae.id_auditoria_encabezado = ?1\n"
                    + "        AND arp.estado_reg = 0\n"
                    + "LIMIT 1", AuditoriaEncabezado.class);
            q.setParameter(1, idAuditoriaEncabezado);
            return (AuditoriaEncabezado) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
