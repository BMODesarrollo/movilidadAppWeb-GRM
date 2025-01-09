/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.NominaServerParamEmpresa;
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
public class NominaServerParamEmpresaFacade extends AbstractFacade<NominaServerParamEmpresa> implements NominaServerParamEmpresaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NominaServerParamEmpresaFacade() {
        super(NominaServerParamEmpresa.class);
    }

    @Override
    public List<NominaServerParamEmpresa> findAllByEstadoRegAndUnidadFuncional(int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       id_gop_unidad_funcional = ?1 AND\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    nomina_server_param_empresa\n"
                    + "WHERE\n"
                    + sql_unida_func
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NominaServerParamEmpresa.class);
            query.setParameter(1, idGopUnidadFuncional);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public NominaServerParamEmpresa findByUfAndCodigoSw(Integer idNominaServerParamEmpresa, int idGopUnidadFuncional, String codigo) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "    AND id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    nomina_server_param_empresa\n"
                    + "WHERE\n"
                    + "	    id_nomina_server_param_empresa <> ?2\n"
                    + "        AND cod_sw_nomina = ?3\n"
                    + sql_unida_func
                    + "        AND estado_reg = 0;";
            Query query = em.createNativeQuery(sql, NominaServerParamEmpresa.class);
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, idNominaServerParamEmpresa);
            query.setParameter(3, codigo);

            return (NominaServerParamEmpresa) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NominaServerParamEmpresa findByIdNominaServerParamAndUf(Integer idNominaServerParam, int idGopUnidadFuncional) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "       id_gop_unidad_funcional = ?2 AND\n";
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    nomina_server_param_empresa\n"
                    + "WHERE\n"
                    + "    id_nomina_server_param=?1 AND\n"
                    + sql_unida_func
                    + "    estado_reg = 0 LIMIT 1;";
            Query query = em.createNativeQuery(sql, NominaServerParamEmpresa.class);
            query.setParameter(1, idNominaServerParam);
            query.setParameter(2, idGopUnidadFuncional);

            return (NominaServerParamEmpresa) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
