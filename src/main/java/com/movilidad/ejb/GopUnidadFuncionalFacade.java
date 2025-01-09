/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.GopUnidadFuncional;
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
public class GopUnidadFuncionalFacade extends AbstractFacade<GopUnidadFuncional> implements GopUnidadFuncionalFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GopUnidadFuncionalFacade() {
        super(GopUnidadFuncional.class);
    }

    @Override
    public List<GopUnidadFuncional> findAllByEstadoReg() {
        Query q = em.createNativeQuery("select * from gop_unidad_funcional where "
                + "estado_reg = 0;", GopUnidadFuncional.class);
        return q.getResultList();
    }

    @Override
    public GopUnidadFuncional findByNombre(String nombre, int idGopUnidadFuncional) {
        try {
            Query q = em.createNativeQuery("select * from gop_unidad_funcional where "
                    + "nombre = ?1 and id_gop_unidad_funcional<> ?2 and estado_reg = 0;", GopUnidadFuncional.class);
            q.setParameter(1, nombre);
            q.setParameter(2, idGopUnidadFuncional);
            return (GopUnidadFuncional) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public GopUnidadFuncional findByCodigo(String codigo, int idGopUnidadFuncional) {
        try {
            Query q = em.createNativeQuery("select * from gop_unidad_funcional where "
                    + "codigo = ?1 and id_gop_unidad_funcional<> ?2 and estado_reg = 0;", GopUnidadFuncional.class);
            q.setParameter(1, codigo);
            q.setParameter(2, idGopUnidadFuncional);
            return (GopUnidadFuncional) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public GopUnidadFuncional findByCodigo(String codigo) {
        try {
            Query q = em.createNativeQuery("select * from gop_unidad_funcional where "
                    + "codigo = ?1 and estado_reg = 0;", GopUnidadFuncional.class);
            q.setParameter(1, codigo);
            return q.getResultList().isEmpty() ? null : (GopUnidadFuncional) q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
