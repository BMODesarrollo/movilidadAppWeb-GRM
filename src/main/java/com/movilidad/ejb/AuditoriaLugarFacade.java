/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaLugar;
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
public class AuditoriaLugarFacade extends AbstractFacade<AuditoriaLugar> implements AuditoriaLugarFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaLugarFacade() {
        super(AuditoriaLugar.class);
    }

    @Override
    public List<AuditoriaLugar> findByArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT al.* "
                    + "FROM auditoria_lugar al "
                    + "WHERE al.id_param_area=?1 "
                    + "AND al.estado_reg=0;", AuditoriaLugar.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaLugar findByIdAuditoriaLugar(int idAuditoriaLugar) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    al.*\n"
                    + "FROM\n"
                    + "    auditoria_lugar al\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_encabezado ae ON ae.id_auditoria_lugar = al.id_auditoria_lugar\n"
                    + "        INNER JOIN\n"
                    + "    auditoria a ON a.id_auditoria_encabezado = ae.id_auditoria_encabezado\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_realizado_por arp ON arp.id_auditoria = a.id_auditoria\n"
                    + "WHERE\n"
                    + "    al.id_auditoria_lugar = ?1\n"
                    + "        AND arp.estado_reg = 0\n"
                    + "LIMIT 1", AuditoriaLugar.class);
            q.setParameter(1, idAuditoriaLugar);
            return (AuditoriaLugar) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
