package com.movilidad.ejb;

import com.movilidad.model.NovedadSeguimiento;
import com.movilidad.utils.Util;
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
public class NovedadSeguimientoFacade extends AbstractFacade<NovedadSeguimiento> implements NovedadSeguimientoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadSeguimientoFacade() {
        super(NovedadSeguimiento.class);
    }

    @Override
    public List<NovedadSeguimiento> findByNovedad(int id) {
        try {
            Query query = em.createQuery("SELECT ns FROM NovedadSeguimiento ns WHERE "
                    + "ns.idNovedad.idNovedad = :novedad")
                    .setParameter("novedad", id);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }
}
