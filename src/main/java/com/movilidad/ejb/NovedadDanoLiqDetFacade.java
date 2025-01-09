/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NovedadDanoLiqDet;
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
public class NovedadDanoLiqDetFacade extends AbstractFacade<NovedadDanoLiqDet> implements NovedadDanoLiqDetFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadDanoLiqDetFacade() {
        super(NovedadDanoLiqDet.class);
    }

    @Override
    public List<NovedadDanoLiqDet> findByRangoFechasAndUf(Date desde, Date hasta, int idGopUnidadFuncional) {
        String sql_uf = idGopUnidadFuncional == 0 ? ";" : "            AND nd.id_gop_unidad_funcional = ?3;\n";
        try {
            String sql = "SELECT \n"
                    + "    det.*\n"
                    + "FROM\n"
                    + "    novedad_dano_liq_det det\n"
                    + "        INNER JOIN\n"
                    + "    novedad_dano nd ON det.id_novedad_dano = nd.id_novedad_dano\n"
                    + "WHERE\n"
                    + "    nd.fecha BETWEEN ?1 AND ?2\n"
                    + sql_uf;
            Query q = em.createNativeQuery(sql, NovedadDanoLiqDet.class);
            q.setParameter(1, Util.dateFormat(desde));
            q.setParameter(2, Util.dateFormat(hasta));
            q.setParameter(3, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
