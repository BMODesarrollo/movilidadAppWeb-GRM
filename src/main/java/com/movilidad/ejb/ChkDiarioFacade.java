/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.ChkDiario;
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
 * @author Carlos Ballestas
 */
@Stateless
public class ChkDiarioFacade extends AbstractFacade<ChkDiario> implements ChkDiarioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ChkDiarioFacade() {
        super(ChkDiario.class);
    }

    @Override
    public List<ChkDiario> findAllByFechas(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_uf = idGopUnidadFuncional == 0 ? "" : "            AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT * FROM chk_diario where date(fecha) between ?1 and ?2 "
                    + sql_uf
                    + "and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, ChkDiario.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

}
