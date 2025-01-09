/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaAreaComun;
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
public class AuditoriaAreaComunFacade extends AbstractFacade<AuditoriaAreaComun> implements AuditoriaAreaComunFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaAreaComunFacade() {
        super(AuditoriaAreaComun.class);
    }

    @Override
    public List<AuditoriaAreaComun> findByArea(int idArea) {
        String sql_area = idArea == 0 ? "" : "  aac.id_param_area = ?1 AND\n";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    aac.*\n"
                    + "FROM\n"
                    + "    auditoria_area_comun aac\n"
                    + "WHERE\n"
                    + sql_area
                    + "        AND aac.estado_reg = 0;", AuditoriaAreaComun.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaAreaComun findByAreaIdAuditoriaAreaComunAndNombre(String nombre, int idAuditoriaTipo, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_area_comun "
                    + "WHERE at.id_param_area=?3 "
                    + "AND at.estado_reg=0 "
                    + "AND at.nombre=?1 "
                    + "AND at.id_auditoria_area_comun=?2;", AuditoriaAreaComun.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idAuditoriaTipo);
            q.setParameter(3, idArea);
            return (AuditoriaAreaComun) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
