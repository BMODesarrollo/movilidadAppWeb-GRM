/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.TblCalendario;
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
public class TblCalendarioFacade extends AbstractFacade<TblCalendario> implements TblCalendarioFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TblCalendarioFacade() {
        super(TblCalendario.class);
    }

    @Override
    public TblCalendario findByFechaAndIdCaracteristica(Integer idRegistro, Integer idCaracteristica, Date fecha, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?4\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    tbl_calendario\n"
                    + "WHERE\n"
                    + "    id_tbl_calendario <> ?1 AND fecha = ?2\n"
                    + sql_unida_func
                    + "        AND id_tbl_calendario_caracteristica_dia = ?3\n"
                    + "        AND estado_reg = 0;";

            Query query = em.createNativeQuery(sql, TblCalendario.class);
            query.setParameter(1, idRegistro);
            query.setParameter(2, Util.dateFormat(fecha));
            query.setParameter(3, idCaracteristica);
            query.setParameter(4, idGopUnidadFuncional);

            return (TblCalendario) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<TblCalendario> findAllByDateRange(Date desde, Date hasta, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       AND id_gop_unidad_funcional = ?3\n";
            String sql = "SELECT * FROM tbl_calendario where fecha between ?1 and ?2 "
                    + sql_unida_func
                    + "and estado_reg = 0;";

            Query query = em.createNativeQuery(sql, TblCalendario.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
