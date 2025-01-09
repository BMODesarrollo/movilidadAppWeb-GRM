/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgClasificacionMotivo;
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
public class PrgClasificacionMotivoFacade extends AbstractFacade<PrgClasificacionMotivo> implements PrgClasificacionMotivoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgClasificacionMotivoFacade() {
        super(PrgClasificacionMotivo.class);
    }

    @Override
    public List<PrgClasificacionMotivo> findByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_clasificacion_motivo\n"
                + "WHERE\n"
                + "    estado_reg = 0;", PrgClasificacionMotivo.class);
        return q.getResultList();
    }

    @Override
    public PrgClasificacionMotivo findBynombreAndIdPrgResponsable(String nombre, int idPrgResponsable, int idPrgClasificacionMotivo) {
        try {

            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    prg_clasificacion_motivo\n"
                    + "WHERE\n"
                    + "    nombre = ?1\n"
                    + "        AND id_prg_clasificacion_motivo <> ?3\n"
                    + "        AND id_prg_tc_responsable = ?2\n"
                    + "        AND estado_reg = 0;", PrgClasificacionMotivo.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idPrgResponsable);
            q.setParameter(3, idPrgClasificacionMotivo);
            return (PrgClasificacionMotivo) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PrgClasificacionMotivo> findByIdPrgResponsableEstadoReg(Integer idPrgResponsable) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_clasificacion_motivo\n"
                + "WHERE\n"
                + "    id_prg_tc_responsable = ?1\n"
                + "        AND estado_reg = 0";
        Query q = em.createNativeQuery(sql, PrgClasificacionMotivo.class);
        q.setParameter(1, idPrgResponsable);
        return q.getResultList();
    }

}
