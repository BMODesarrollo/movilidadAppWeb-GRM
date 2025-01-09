/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaEstacion;
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
public class AuditoriaEstacionFacade extends AbstractFacade<AuditoriaEstacion> implements AuditoriaEstacionFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaEstacionFacade() {
        super(AuditoriaEstacion.class);
    }

    @Override
    public List<AuditoriaEstacion> findByArea(int idArea) {
        String sql_area = idArea == 0 ? "" : "  at.id_param_area = ?1 AND\n";
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    at.*\n"
                    + "FROM\n"
                    + "    auditoria_estacion at\n"
                    + "WHERE\n"
                    + sql_area
                    + "       at.estado_reg = 0;", AuditoriaEstacion.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaEstacion findByAreaIdAuditoriaEstacionAndNombre(String nombre, int idAuditoriaTipo, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_estacion "
                    + "WHERE at.id_param_area=?3 "
                    + "AND at.estado_reg=0 "
                    + "AND at.nombre=?1 "
                    + "AND at.id_auditoria_estacion=?2;", AuditoriaEstacion.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idAuditoriaTipo);
            q.setParameter(3, idArea);
            return (AuditoriaEstacion) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
