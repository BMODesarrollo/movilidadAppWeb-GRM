/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadTipo;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USUARIO
 */
@Stateless
public class NovedadTipoFacade extends AbstractFacade<NovedadTipo> implements NovedadTipoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadTipoFacade() {
        super(NovedadTipo.class);
    }

    @Override
    public List<NovedadTipo> obtenerTipos() {
        Query query = em.createNativeQuery("SELECT * FROM novedad_tipo where id_novedad_tipo not in(3)", NovedadTipo.class);
        return query.getResultList();
    }

    @Override
    public List<NovedadTipo> findAllAfectaPm(int opc) {
        String byFecha = "";
        if (opc == 1) {
            byFecha = "AND gtd.fechas=1 ";
        }
        try {
            String sql = "SELECT DISTINCT\n"
                    + "    (gt.id_novedad_tipo),\n"
                    + "    gt.nombre_tipo_novedad,\n"
                    + "    gt.descripcion_tipo_novedad,\n"
                    + "    gt.username,\n"
                    + "    gt.estado_reg\n"
                    + "FROM\n"
                    + "    novedad_tipo gt\n"
                    + "        INNER JOIN\n"
                    + "    novedad_tipo_detalles gtd ON gt.id_novedad_tipo = gtd.id_novedad_tipo\n"
                    + "WHERE\n"
                    + "    gtd.afecta_pm = 1 " + byFecha + ";";

            Query query = em.createNativeQuery(sql, NovedadTipo.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public List<NovedadTipo> findAllAfectaDisp() {
        String sql = "SELECT DISTINCT\n"
                + "    (nt.id_novedad_tipo),\n"
                + "    nt.nombre_tipo_novedad,\n"
                + "    nt.descripcion_tipo_novedad,\n"
                + "    nt.username,\n"
                + "    nt.estado_reg\n"
                + "FROM\n"
                + "    novedad_tipo nt\n"
                + "        INNER JOIN\n"
                + "    novedad_tipo_detalles ntd ON nt.id_novedad_tipo = ntd.id_novedad_tipo\n"
                + "WHERE\n"
                + "    ntd.afecta_disponibilidad = 1\n"
                + "        AND nt.estado_reg = 0\n"
                + "        AND ntd.estado_reg = 0";

        Query query = em.createNativeQuery(sql, NovedadTipo.class);
	return query.getResultList();
    }
    @Override
    public List<NovedadTipo> findAllByNovTipDetMtto() {
        String sql = "SELECT DISTINCT\n"
                + "    (nt.id_novedad_tipo),\n"
                + "    nt.nombre_tipo_novedad,\n"
                + "    nt.descripcion_tipo_novedad,\n"
                + "    nt.username,\n"
                + "    nt.estado_reg\n"
                + "FROM\n"
                + "    novedad_tipo nt\n"
                + "        INNER JOIN\n"
                + "    novedad_tipo_detalles ntd ON nt.id_novedad_tipo = ntd.id_novedad_tipo\n"
                + "WHERE\n"
                + "    ntd.mtto = 1\n"
                + "        AND nt.estado_reg = 0\n"
                + "        AND ntd.estado_reg = 0";

        Query query = em.createNativeQuery(sql, NovedadTipo.class);
	return query.getResultList();
    }
    
    @Override
    public List<NovedadTipo> findAllByNovTipTC() {
        String sql = "SELECT "
                + "    (nt.id_novedad_tipo),\n"
                + "    nt.nombre_tipo_novedad,\n"
                + "    nt.descripcion_tipo_novedad,\n"
                + "    nt.username,\n"
                + "    nt.estado_reg\n"
                + "FROM\n"
                + "    novedad_tipo nt\n"
                + "        INNER JOIN\n"
                + "    novedad_tipo_detalles ntd ON nt.id_novedad_tipo = ntd.id_novedad_tipo\n"
                + "WHERE\n"
                + "    ntd.mtto = 1\n"
                + "        AND nt.estado_reg = 0\n"
                + "        AND ntd.estado_reg = 0";

        Query query = em.createNativeQuery(sql, NovedadTipo.class);
	return query.getResultList();
    }

    @Override
    public List<NovedadTipo> findAllEstadoReg() {
        Query query = em.createNativeQuery("SELECT * FROM novedad_tipo where estado_reg = 0", NovedadTipo.class);
        return query.getResultList();
    }
    
    @Override
    public NovedadTipo findByIdNovedadTipo(int idNovedadTipo) {
        try {
            Query query = em.createNamedQuery("NovedadTipo.findByIdNovedadTipo", NovedadTipo.class);
            query.setParameter("idNovedadTipo", idNovedadTipo);
            return (NovedadTipo)query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
