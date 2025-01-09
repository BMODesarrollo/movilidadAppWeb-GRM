/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispEstadoPendActual;
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
public class DispEstadoPendActualFacade extends AbstractFacade<DispEstadoPendActual> implements DispEstadoPendActualFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DispEstadoPendActualFacade() {
        super(DispEstadoPendActual.class);
    }

    @Override
    public DispEstadoPendActual findByNombreByIdVehiculoTipoEstadoDet(String nombre,
            int idVehiculoTipoEstadoDet) {
        try {
            Query q = em.createNamedQuery("DispEstadoPendActual.findByNombreByIdVehiculoTipoEstadoDet",
                    DispEstadoPendActual.class);
            q.setParameter("nombre", nombre);
            q.setParameter("idVehiculoTipoEstadoDet", idVehiculoTipoEstadoDet);
            q.setParameter("estadoReg", 0);
            return (DispEstadoPendActual) q.setMaxResults(1).getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<DispEstadoPendActual> findAllByEstadoReg() {
        Query q = em.createNativeQuery("select * from disp_estado_pend_actual d where "
                + "d.estado_reg=0;", DispEstadoPendActual.class);
        return q.getResultList();
    }

    @Override
    public List<DispEstadoPendActual> findFirtStatusByidVehiculoTipoEstadoDetOrAll(int id_vehiculo_tipo_estado_det, boolean all) {
        String sql_primer_Estado = all ? "    depa.primer_estado = 1 AND\n" : "";
        Query q = em.createNativeQuery("SELECT \n"
                + "    depa.*\n"
                + "FROM\n"
                + "    disp_epa_vted d\n"
                + "        INNER JOIN\n"
                + "    disp_estado_pend_actual depa ON depa.id_disp_estado_pend_actual = d.id_disp_estado_pend_actual\n"
                + "WHERE\n"
                + sql_primer_Estado
                + "         d.id_vehiculo_tipo_estado_det = ?1;", DispEstadoPendActual.class);
        q.setParameter(1, id_vehiculo_tipo_estado_det);
        return q.getResultList();
    }

    @Override
    public DispEstadoPendActual findEstadoDiferir(int idDispEstadoPendActual) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    disp_estado_pend_actual\n"
                    + "WHERE\n"
                    + "    id_disp_estado_pend_actual <> ?1\n"
                    + "        AND por_defecto_diferir = 1\n"
                    + "        AND estado_reg = 0 Limit 1;", DispEstadoPendActual.class);
            q.setParameter(1, idDispEstadoPendActual);
            return (DispEstadoPendActual) q.getSingleResult();

        } catch (Exception e) {
            return null;
        }
    }
}
