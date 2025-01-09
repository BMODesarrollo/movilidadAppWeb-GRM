/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.DispClasificacionNovedad;
import com.movilidad.utils.MovilidadUtil;
import com.movilidad.utils.Util;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author solucionesit
 */
@Stateless
public class DispClasificacionNovedadFacade extends AbstractFacade<DispClasificacionNovedad> implements DispClasificacionNovedadFacadeLocal {
    
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public DispClasificacionNovedadFacade() {
        super(DispClasificacionNovedad.class);
    }
    
    @Override
    public int updateSetestadoPendActual(Integer idDispClasificacionNovedad,
            Integer IdDispEstadoPendActual, String username) {
        Query q = em.createNativeQuery("UPDATE disp_clasificacion_novedad d \n"
                + "SET \n"
                + "    d.id_disp_estado_pend_actual = ?2,\n"
                + "    d.username = ?3,\n"
                + "    d.modificado = ?4\n"
                + "WHERE\n"
                + "    d.id_disp_clasificacion_novedad = ?1");
        q.setParameter(1, idDispClasificacionNovedad);
        q.setParameter(2, IdDispEstadoPendActual);
        q.setParameter(3, username);
        q.setParameter(4, Util.dateTimeFormat(MovilidadUtil.fechaCompletaHoy()));
        
        return q.executeUpdate();
    }
    
}
