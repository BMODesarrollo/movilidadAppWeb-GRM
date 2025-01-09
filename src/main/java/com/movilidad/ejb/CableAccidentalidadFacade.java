/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.CableAccidentalidad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author cesar
 */
@Stateless
public class CableAccidentalidadFacade extends AbstractFacade<CableAccidentalidad> implements CableAccidentalidadFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CableAccidentalidadFacade() {
        super(CableAccidentalidad.class);
    }

    /**
     * Realizar la busqueda en base de datos de los registros que coinsidan con
     * la búsqueda.
     *
     * @param idCabina id de la entidad cable_cabina
     * @param idEmpleado id de la entidad empleado
     * @param cDesde objeto String con el formato fecha yyyy-MM-dd
     * @param cHasta objeto String con el formato fecha yyyy-MM-dd
     * @param idTipoAcc id de la entidad cable_acc_tipo
     * @return objeto List de CableAccidentalidad
     */
    @Override
    public List<CableAccidentalidad> findByArguments(Integer idCabina, Integer idEmpleado, String cDesde, String cHasta, Integer idTipoAcc) {
        try {
            String sql = "select * from cable_accidentalidad where date(fecha_hora) between '" + cDesde + "' and '" + cHasta + "' ";
            if (idEmpleado != null) {
                sql = sql + "and id_operador = " + idEmpleado + " ";
            }
            if (idCabina != null) {
                sql = sql + "and id_cable_cabina = " + idCabina + " ";
            }
            if (idTipoAcc != null) {
                sql = sql + "and id_cable_acc_tipo = " + idTipoAcc + " ";
            }
            sql = sql + "and estado_reg = 0";
            Query q = em.createNativeQuery(sql, CableAccidentalidad.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

}
