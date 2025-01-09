/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PqrMaestro;
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
 * @author Carlos Ballestas
 */
@Stateless
public class PqrMaestroFacade extends AbstractFacade<PqrMaestro> implements PqrMaestroFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PqrMaestroFacade() {
        super(PqrMaestro.class);
    }

    @Override
    public List<PqrMaestro> findByRangoFechas(Date desde, Date hasta, int idGopUF) {
        String uf = idGopUF != 0 ? "AND id_gop_unidad_funcional = " + idGopUF + " " : " ";
        try {
            String sql = "SELECT * FROM pqr_maestro where fecha_radicado between ?1 and ?2 and estado_reg = 0 "
                    + uf
                    + "ORDER BY fecha_radicado DESC;";
            Query query = em.createNativeQuery(sql, PqrMaestro.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<PqrMaestro> findAllOpen(boolean edit, int idPqr) {
        try {
            String unionEdit = edit ? " UNION ALL SELECT * FROM pqr_maestro where id_pqr_maestro = " + idPqr + "\n" : "";
            String sql = "SELECT * FROM pqr_maestro where estado=1 and estado_reg = 0 and id_pqr_maestro not in (select id_pqr from novedad where id_pqr >0)"
                    + unionEdit + ";";
            Query query = em.createNativeQuery(sql, PqrMaestro.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
