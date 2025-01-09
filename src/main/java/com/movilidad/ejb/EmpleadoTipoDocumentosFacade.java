/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.EmpleadoTipoDocumentos;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Soluciones IT
 */
@Stateless
public class EmpleadoTipoDocumentosFacade extends AbstractFacade<EmpleadoTipoDocumentos> implements EmpleadoTipoDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmpleadoTipoDocumentosFacade() {
        super(EmpleadoTipoDocumentos.class);
    }

    @Override
    public EmpleadoTipoDocumentos findByNombreTipoDoc(String value, int id) {
        try {
            if (id == 0) {
                TypedQuery<EmpleadoTipoDocumentos> query = em.createQuery(
                        "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.nombreTipoDocumento= ?1",
                        EmpleadoTipoDocumentos.class);
                return query.setParameter(1, value).getSingleResult();
            }
            TypedQuery<EmpleadoTipoDocumentos> query = em.createQuery(
                    "SELECT e FROM EmpleadoTipoDocumentos e WHERE e.nombreTipoDocumento= ?1 AND"
                    + " e.idEmpleadoTipoDocumento<>?2", EmpleadoTipoDocumentos.class);
            query.setParameter(1, value);
            query.setParameter(2, id);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<EmpleadoTipoDocumentos> findAllActivos() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    etd.*\n"
                + "FROM\n"
                + "    empleado_tipo_documentos etd\n"
                + "WHERE\n"
                + "    etd.estado_reg = 0;", EmpleadoTipoDocumentos.class);
        return q.getResultList();
    }

    @Override
    public List<EmpleadoTipoDocumentos> findByIdCargo(int idTipoCargo) {
        Query q = em.createNativeQuery("SELECT \n"
                + "    etd.*\n"
                + "FROM\n"
                + "    empleado_tipo_documentos etd\n"
                + "        INNER JOIN\n"
                + "    documento_por_cargo dpc ON dpc.id_empleado_tipo_documento = etd.id_empleado_tipo_documento\n"
                + "WHERE\n"
                + "    dpc.id_empleado_tipo_cargo = ?1\n"
                + "        AND dpc.estado_reg = 0;", EmpleadoTipoDocumentos.class);
        q.setParameter(1, idTipoCargo);
        return q.getResultList();
		 }
    public List<EmpleadoTipoDocumentos> findAllEstadoReg() {
        try {
            String sql = "SELECT * "
                    + "FROM empleado_tipo_documentos "
                    + "WHERE estado_reg = 0";
            Query q = em.createNativeQuery(sql, EmpleadoTipoDocumentos.class);
            return q.getResultList();
        } catch (Exception e) {
            return null;
        }
		
    }

}
