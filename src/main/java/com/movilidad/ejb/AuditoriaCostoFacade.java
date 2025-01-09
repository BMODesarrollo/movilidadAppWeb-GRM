/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.dto.AuditoriaCostoDTO;
import com.movilidad.model.AuditoriaCosto;
import com.movilidad.utils.Util;
import java.util.Date;
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
public class AuditoriaCostoFacade extends AbstractFacade<AuditoriaCosto> implements AuditoriaCostoFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaCostoFacade() {
        super(AuditoriaCosto.class);
    }

    @Override
    public List<AuditoriaCosto> findByEstadoReg() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    auditoria_costo\n"
                + "WHERE\n"
                + "    estado_reg = 0;", AuditoriaCosto.class);
        return q.getResultList();
    }

    @Override
    public AuditoriaCosto validar(int idAuditoria, int idVehiculoTipo, int idAuditoriaCostoTipo, Date desde, Date hasta, int idAuditoriaCosto) {
        try {
            String sql = idVehiculoTipo == 0 ? "" : "        AND id_vehiculo_tipo = ?2\n";
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    auditoria_costo\n"
                    + "WHERE\n"
                    + "        ((?4 BETWEEN desde AND hasta\n"
                    + "        OR ?5 BETWEEN desde AND hasta)\n"
                    + "        OR (desde BETWEEN ?4 AND ?5\n"
                    + "        OR hasta BETWEEN ?4 AND ?5))"
                    + sql
                    + "        AND id_auditoria_costo_tipo = 0\n"
                    + "        AND id_auditoria = ?1\n"
                    + "        AND id_auditoria_costo <> ?6\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;", AuditoriaCosto.class);
            q.setParameter(1, idAuditoria);
            q.setParameter(2, idVehiculoTipo);
            q.setParameter(3, idAuditoriaCostoTipo);
            q.setParameter(4, Util.dateFormat(desde));
            q.setParameter(5, Util.dateFormat(hasta));
            q.setParameter(6, idAuditoriaCosto);
            return (AuditoriaCosto) q.getSingleResult();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<AuditoriaCosto> findByIdAuditoria(Integer idAuditoria) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    auditoria_costo\n"
                + "WHERE\n"
                + "    estado_reg = 0 and id_auditoria = ?1;", AuditoriaCosto.class);
        q.setParameter(1, idAuditoria);
        return q.getResultList();
    }

    @Override
    public List<AuditoriaCostoDTO> findListDtoByIdAuditoria(Integer idAuditoria) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    act.nombre AS tipo_costo,\n"
                + "    vt.nombre_tipo_vehiculo AS tipo_vehiculo,\n"
                + "    ac.valor,\n"
                + "    ac.desde,\n"
                + "    ac.hasta\n"
                + "FROM\n"
                + "    auditoria_costo ac\n"
                + "        INNER JOIN\n"
                + "    vehiculo_tipo vt ON ac.id_vehiculo_tipo = vt.id_vehiculo_tipo\n"
                + "        INNER JOIN\n"
                + "    auditoria_costo_tipo act ON act.id_auditoria_costo_tipo = ac.id_auditoria_costo_tipo\n"
                + "WHERE\n"
                + "    ac.estado_reg = 0\n"
                + "        AND ac.id_auditoria = ?1\n"
                + "ORDER BY tipo_vehiculo ASC;", "AuditoriaCostoMapping");
        q.setParameter(1, idAuditoria);
        return q.getResultList();
    }

}
