/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AtvCostoServicio;
import com.movilidad.utils.Util;
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
public class AtvCostoServicioFacade extends AbstractFacade<AtvCostoServicio> implements AtvCostoServicioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AtvCostoServicioFacade() {
        super(AtvCostoServicio.class);
    }

    @Override
    public List<AtvCostoServicio> findByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_costo_servicio\n"
                + "WHERE\n"
                + "    estado_reg = 0", AtvCostoServicio.class);
        return q.getResultList();
    }

    @Override
    public AtvCostoServicio findByCostoAndIdPrestadorAndVehiculoTipoAndRangoFecha(int idAtvPrestador, int idVehiculoTipo, Date desde, Date hasta, int idAtvCostoServicio) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    atv_costo_servicio\n"
                    + "WHERE\n"
                    + "    id_atv_prestador = ?1\n"
                    + "        AND id_vehiculo_tipo = ?2\n"
                    + "        AND (?3 BETWEEN desde AND hasta\n"
                    + "        OR ?4 BETWEEN desde AND hasta)\n"
                    + "        AND id_atv_costo_servicio <> ?5\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1", AtvCostoServicio.class);
            q.setParameter(1, idAtvPrestador);
            q.setParameter(2, idVehiculoTipo);
            q.setParameter(3, desde);
            q.setParameter(4, hasta);
            q.setParameter(5, idAtvCostoServicio);
            return (AtvCostoServicio) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AtvCostoServicio> findAllByFechas(Date desde, Date hasta) {
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    atv_costo_servicio\n"
                + "WHERE\n"
                + "    ((?1 BETWEEN desde AND hasta\n"
                + "        OR ?2 BETWEEN desde AND hasta)\n"
                + "        OR (desde BETWEEN ?1 AND ?2\n"
                + "        OR hasta BETWEEN ?1 AND ?2))\n"
                + "        AND estado_reg = 0";
        Query q = em.createNativeQuery(sql, AtvCostoServicio.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));
        return q.getResultList();
    }

}
