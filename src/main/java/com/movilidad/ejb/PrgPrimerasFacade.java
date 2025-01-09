/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.PrgPrimeras;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author soluciones-it
 */
@Stateless
public class PrgPrimerasFacade extends AbstractFacade<PrgPrimeras> implements PrgPrimerasFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrgPrimerasFacade() {
        super(PrgPrimeras.class);
    }

    @Override
    public List<PrgPrimeras> findAllByGopUf(Date fecha, int idGopUf) {
        String uf = idGopUf == 0 ? " " : " AND id_gop_unidad_funcional = " + idGopUf;
        String sql = "SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    prg_primeras\n"
                + "WHERE\n"
                + "    fecha = DATE(?1) "
                + uf;
        Query q = em.createNativeQuery(sql, PrgPrimeras.class);
        q.setParameter(1, Util.dateFormat(fecha));
        return q.getResultList();
    }

    @Override
    public void ejecutarProcesoPrimeras(Date fecha, int idGopUf) {
        Query query = em.createNativeQuery("{call primerasTablas(?1,?2)}")
                .setParameter(1, Util.dateFormat(fecha))
                .setParameter(2, idGopUf);
        query.executeUpdate();
    }

}
