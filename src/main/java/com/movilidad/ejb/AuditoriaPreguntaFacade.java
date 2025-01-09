/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.movilidad.ejb;

import com.movilidad.model.AuditoriaPregunta;
import java.util.ArrayList;
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
public class AuditoriaPreguntaFacade extends AbstractFacade<AuditoriaPregunta> implements AuditoriaPreguntaFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AuditoriaPreguntaFacade() {
        super(AuditoriaPregunta.class);
    }

    @Override
    public List<AuditoriaPregunta> findByIdArea(int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT "
                    + "ap.* "
                    + "FROM "
                    + "auditoria_pregunta ap "
                    + "WHERE ap.id_param_area = ?1 "
                    + "AND "
                    + "ap.estado_reg = 0;", AuditoriaPregunta.class);
            q.setParameter(1, idArea);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public AuditoriaPregunta findByAreaIdAuditoriaPreguntaAndCodigo(String codigo, int idAudiPregunta, int idArea) {
        try {
            Query q = em.createNativeQuery("SELECT at.* "
                    + "FROM auditoria_pregunta at "
                    + "WHERE at.id_param_area=?3 "
                    + "AND at.estado_reg=0 "
                    + "AND at.codigo=?1 "
                    + "AND at.id_auditoria_pregunta<>?2;", AuditoriaPregunta.class);
            q.setParameter(1, codigo);
            q.setParameter(2, idAudiPregunta);
            q.setParameter(3, idArea);
            return (AuditoriaPregunta) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<AuditoriaPregunta> findByIdAuditoria(int idAuditoriaPregunta) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ap.*\n"
                    + "FROM\n"
                    + "    auditoria_pregunta ap\n"
                    + "        INNER JOIN\n"
                    + "    auditoria_pregunta_relacion apr ON apr.id_auditoria_pregunta = ap.id_auditoria_pregunta\n"
                    + "WHERE\n"
                    + "    apr.id_auditoria = ?1\n"
                    + "        AND ap.estado_reg = 0\n"
                    + "        AND apr.estado_reg = 0\n"
                    + "ORDER BY ap.numero ASC;", AuditoriaPregunta.class);
            q.setParameter(1, idAuditoriaPregunta);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Método responsable de retornar preguntas que se han utilizado en
     * auditorías o no
     *
     * @param opc
     *
     * Sí opc es igual a 0, el método devolverá las preguntas que han sido
     * utilizadas en auditorías.
     *
     * Sí opc es igual a 1, el método devolverá las preguntas que NO han sido
     * utilizadas en auditorías.
     *
     * Sí opc no coincide con los anteriores, el método devolverá todas las
     * preguntas registradas en el sistema.
     * @return Lista de pregunta.
     */
    @Override
    public List<AuditoriaPregunta> findPreguntasByOpcion(int opc) {
        String sqlPart = "";
        if (opc == 0) {
            sqlPart = "        AND ap.id_auditoria_pregunta IN (SELECT \n"
                    + "            apr.id_auditoria_pregunta\n"
                    + "        FROM\n"
                    + "            auditoria_pregunta_relacion apr\n"
                    + "        WHERE\n"
                    + "            apr.estado_reg = 0);";
        }
        if (opc == 1) {
            sqlPart = "        AND ap.id_auditoria_pregunta NOT IN (SELECT \n"
                    + "            apr.id_auditoria_pregunta\n"
                    + "        FROM\n"
                    + "            auditoria_pregunta_relacion apr\n"
                    + "        WHERE\n"
                    + "            apr.estado_reg = 0);";
        }
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ap.*\n"
                    + "FROM\n"
                    + "    auditoria_pregunta ap\n"
                    + "WHERE\n"
                    + "    ap.estado_reg = 0\n" + sqlPart, AuditoriaPregunta.class);
            return q.getResultList();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

}
