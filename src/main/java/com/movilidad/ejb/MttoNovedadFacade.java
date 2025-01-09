/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.MttoNovedad;
import com.movilidad.utils.MovilidadUtil;
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
public class MttoNovedadFacade extends AbstractFacade<MttoNovedad> implements MttoNovedadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MttoNovedadFacade() {
        super(MttoNovedad.class);
    }

    @Override
    public List<MttoNovedad> getMttoNovAbiertasByVehiculo(int idVehiculo) {
        try {
            Query q = em.createNativeQuery("SELECT n.* "
                    + "FROM mtto_novedad n "
                    + "WHERE n.id_vehiculo=?1 AND n.id_mtto_estado=1;", MttoNovedad.class);
            q.setParameter(1, idVehiculo);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MttoNovedad> getNovByfechas(Date desde, Date hasta) {
        try {
            Query q = em.createNativeQuery("SELECT n.* "
                    + "FROM mtto_novedad n "
                    + "WHERE n.fecha between ?1 AND ?2 ORDER BY n.fecha ASC;", MttoNovedad.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(MovilidadUtil.sumarDias(hasta, 1)));
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
