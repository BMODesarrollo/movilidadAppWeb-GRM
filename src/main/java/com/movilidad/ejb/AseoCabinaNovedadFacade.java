/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AseoCabinaNovedad;
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
public class AseoCabinaNovedadFacade extends AbstractFacade<AseoCabinaNovedad> implements AseoCabinaNovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AseoCabinaNovedadFacade() {
        super(AseoCabinaNovedad.class);
    }

    @Override
    public List<AseoCabinaNovedad> findByFechaAndEstadoReg(Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    acn.*\n"
                    + "FROM\n"
                    + "    aseo_cabina_novedad acn\n"
                    + "        INNER JOIN\n"
                    + "    generica g ON g.id_generica = acn.id_generica\n"
                    + "    where g.fecha=?1 and g.estado_reg=0 and acn.estado_reg=0;", AseoCabinaNovedad.class);
            q.setParameter(1, Util.dateFormat(fecha));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
