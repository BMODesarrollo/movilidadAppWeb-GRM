/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispActividad;
import java.util.Date;
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
public class DispActividadFacade extends AbstractFacade<DispActividad> implements DispActividadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispActividadFacade() {
        super(DispActividad.class);
    }

    @Override
    public List<DispActividad> findAllByIdNovedad(int idNovedad) {
        Query q = em.createNativeQuery("SELECT * FROM disp_actividad d where "
                + "d.id_novedad= ?1 AND d.estado_reg=0;", DispActividad.class);
        q.setParameter(1, idNovedad);
        return q.getResultList();
    }

    @Override
    public DispActividad findDiferidaByIdNovedad(int idNovedad, int idDispActividad) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    da.*\n"
                    + "FROM\n"
                    + "    disp_actividad da\n"
                    + "WHERE\n"
                    + "    da.id_novedad = ?1\n"
                    + "        AND da.id_disp_actividad <> ?2\n"
                    + "        AND da.diferir = 1\n"
                    + "        AND da.estado_reg = 0;", DispActividad.class);
            q.setParameter(1, idNovedad);
            q.setParameter(2, idDispActividad);
            return (DispActividad) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DispActividad> findByFechaHora(Date fechaHora) {
        try {
            String sql = "";
            
            Query query = em.createNativeQuery(sql, DispActividad.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }

}
