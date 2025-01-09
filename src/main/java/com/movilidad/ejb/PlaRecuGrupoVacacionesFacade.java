/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.movilidad.ejb;

import com.movilidad.model.planificacion_recursos.PlaRecuGrupoVacaciones;
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
 * @author Omar.beltran
 */
@Stateless
public class PlaRecuGrupoVacacionesFacade extends AbstractFacade<PlaRecuGrupoVacaciones> implements PlaRecuGrupoVacacionesFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    public PlaRecuGrupoVacacionesFacade() {
        super(PlaRecuGrupoVacaciones.class);
    }
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @Override
    public List<PlaRecuGrupoVacaciones> findAllByFechaRangeAndEstadoReg(Date desde, Date hasta, int estadoReg) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_grupo_vacaciones\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2)) AND\n"
                    + "        estado_reg = ?3;";
            Query query = em.createNativeQuery(sql, PlaRecuGrupoVacaciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            query.setParameter(3, estadoReg);
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<PlaRecuGrupoVacaciones> findAllByFechaRange(Date desde, Date hasta) {
        try {
            String sql = "SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    pla_recu_grupo_vacaciones\n"
                    + "WHERE\n"
                    + "    (( ?1 <= DATE(fecha_inicio) AND DATE(fecha_inicio) <=  ?2) OR\n"
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) <=  ?2) OR \n" 
                    + "     ( ?1 <= DATE(fecha_fin) AND DATE(fecha_fin) >  ?2)) AND\n"
                    + "    estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuGrupoVacaciones.class);
            query.setParameter(1, Util.dateFormat(desde));
            query.setParameter(2, Util.dateFormat(hasta));
            return query.getResultList();    
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Permite hacer busqueda de un registro dado el nombre del grupo y el 
     * identificador de la unidad funcional
     * @param grupo nombre del grupo que se desea buscar 
     * @param idUF identificador de la unidad funcional
     * @return null si no hay coincidencia, 
     *         el objeto de coincidencia en cualquier otro caso
     */
    @Override
    public PlaRecuGrupoVacaciones findByName(String grupo, Integer idUF) {
        try {
        String sql = "SELECT \n"
                    + "    gv.*\n"
                    + "FROM\n"
                    + "    pla_recu_grupo_vacaciones gv\n"
                    + "WHERE\n"
                    + "     gv.grupo = ?1 \n"
                    + "     AND gv.id_gop_unidad_funcional = ?2 \n"
                    + "     AND gv.estado_reg = 0;";
            Query query = em.createNativeQuery(sql, PlaRecuGrupoVacaciones.class);
            query.setParameter(1, grupo);
            query.setParameter(2, idUF);
            return query.getResultList().isEmpty() ? null : (PlaRecuGrupoVacaciones) query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public List<PlaRecuGrupoVacaciones> findByIdGopUnidadFuncional(int idGopUnidadFuncional) {
        try {

            String sql_unida_func = idGopUnidadFuncional == 0 ? "" : "        AND e.id_gop_unidad_funcional = ?1\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    e.*\n"
                    + "FROM\n"
                    + "    pla_recu_grupo_vacaciones e\n"
                    + "WHERE\n"
                    + "    e.estado_reg = 0\n"
                    + sql_unida_func, PlaRecuGrupoVacaciones.class);
            q.setParameter(1, idGopUnidadFuncional);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
    
}
