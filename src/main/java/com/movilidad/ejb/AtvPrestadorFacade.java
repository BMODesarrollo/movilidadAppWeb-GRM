/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvPrestador;
import com.movilidad.utils.Util;
import java.util.ArrayList;
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
public class AtvPrestadorFacade extends AbstractFacade<AtvPrestador> implements AtvPrestadorFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvPrestadorFacade() {
        super(AtvPrestador.class);
    }

    @Override
    public List<AtvPrestador> findAllByEstadoReg() {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_prestador\n"
                    + "WHERE\n"
                    + "    estado_reg = 0 ORDER BY nombre asc;", AtvPrestador.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public AtvPrestador findByNombreAndRangoFecha(String nombre, Date desde, Date hasta, int idAtvPrestador) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_prestador\n"
                    + "WHERE\n"
                    + "    nombre = ?1 AND id_atv_prestador<>?2\n"
                    + "        AND ((?3 BETWEEN desde AND hasta\n"
                    + "        OR ?4 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?3 AND ?4\n"
                    + "        OR hasta BETWEEN ?3 AND ?4))\n"
                    + "        AND estado_reg = 0 LIMIT 1;", AtvPrestador.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idAtvPrestador);
            q.setParameter(3, Util.dateFormat(desde));
            q.setParameter(4, Util.dateFormat(hasta));
            return (AtvPrestador) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public AtvPrestador findByCorreoAndActivo(String correo) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_prestador\n"
                    + "WHERE\n"
                    + "    correo = ?1\n"
                    + "        AND DATE(NOW()) BETWEEN desde AND hasta\n"
                    + "        AND estado_reg = 0\n"
                    + "        AND activo = 1\n"
                    + "LIMIT 1";
            Query q = em.createNativeQuery(sql, AtvPrestador.class);
            q.setParameter(1, correo.trim());
            return (AtvPrestador) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
