package com.movilidad.ejb;

import com.movilidad.model.BiometricoConfig;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Omar.beltran
 */
@Stateless
public class BiometricoConfigFacade extends AbstractFacade<BiometricoConfig> implements BiometricoConfigFacadeLocal{

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    //default constructor
    public BiometricoConfigFacade() {
        super(BiometricoConfig.class);
    }

    public BiometricoConfigFacade(Class<BiometricoConfig> entityClass) {
        super(entityClass);
    }

    @Override
    public List<BiometricoConfig> findAllActivos(int idGopUnidadFuncional, int idParamArea) {
        try {
            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND id_gop_unidad_funcional = ?1\n";
            String sql = "SELECT * from biometrico_config where estado_reg = 0 AND id_param_area = ?2\n"
                    + sql_unida_func
                    + ";";
            Query query = em.createNativeQuery(sql, BiometricoConfig.class);
            query.setParameter(1, idGopUnidadFuncional);
            query.setParameter(2, idParamArea);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
