package com.movilidad.ejb;

import com.movilidad.model.OperacionGrua;
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
public class OperacionGruaFacade extends AbstractFacade<OperacionGrua> implements OperacionGruaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperacionGruaFacade() {
        super(OperacionGrua.class);
    }

    @Override
    public List<OperacionGrua> findByRangeDates(Date desde, Date hasta) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    og.*\n"
                + "FROM\n"
                + "    operacion_grua og\n"
                + "WHERE\n"
                + "    og.fecha BETWEEN ?1 AND ?2\n"
                + "        AND og.estado_reg = 0;", OperacionGrua.class);
        q.setParameter(1, Util.dateFormat(desde));
        q.setParameter(2, Util.dateFormat(hasta));

        return q.getResultList();
    }

}
