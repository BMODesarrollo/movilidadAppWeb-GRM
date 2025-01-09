/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkComponenteFalla;
import java.util.ArrayList;
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
public class ChkComponenteFallaFacade extends AbstractFacade<ChkComponenteFalla> implements ChkComponenteFallaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChkComponenteFallaFacade() {
        super(ChkComponenteFalla.class);
    }

    @Override
    public ChkComponenteFalla findByNombreAndComponente(Integer idChkComponenteFalla, String nombre, Integer idChkComponente) {
        try {
            String sql = "SELECT * FROM chk_componente_falla where nombre = ?1 and id_chk_componente_falla <> ?2 and id_chk_componente = ?3 and estado_reg = 0 LIMIT 1;";

            Query query = em.createNativeQuery(sql, ChkComponenteFalla.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idChkComponenteFalla);
            query.setParameter(3, idChkComponente);
            return (ChkComponenteFalla) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<ChkComponenteFalla> findAllByEstadoReg() {
        try {
            String sql = "SELECT * FROM chk_componente_falla where estado_reg = 0 order by id_chk_componente;";

            Query query = em.createNativeQuery(sql, ChkComponenteFalla.class);
            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public ChkComponenteFalla verificarAfectaDisponibilidad(Integer idChkComponenteFalla, String nombre, Integer idChkComponente, Integer idNovedadTipo, Integer idNovedadTipoDetalle, Integer idDispSistema) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    chk_componente_falla\n"
                    + "WHERE\n"
                    + "    nombre = ?1\n"
                    + "        AND id_chk_componente_falla <> ?2\n"
                    + "        AND id_chk_componente = ?3\n"
                    + "        AND id_novedad_tipo = ?4\n"
                    + "        AND id_novedad_tipo_detalle = ?5\n"
                    + "        AND id_disp_sistema = ?6\n"
                    + "        AND estado_reg = 0;";

            Query query = em.createNativeQuery(sql, ChkComponenteFalla.class);
            query.setParameter(1, nombre);
            query.setParameter(2, idChkComponenteFalla);
            query.setParameter(3, idChkComponente);
            query.setParameter(4, idNovedadTipo);
            query.setParameter(5, idNovedadTipoDetalle);
            query.setParameter(6, idDispSistema);
            return (ChkComponenteFalla) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

}
