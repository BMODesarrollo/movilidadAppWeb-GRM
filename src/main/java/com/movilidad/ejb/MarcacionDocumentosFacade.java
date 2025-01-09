package com.movilidad.ejb;

import com.movilidad.model.MarcacionDocumentos;
import com.movilidad.utils.Util;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * @author Omar.beltran
 */
@Stateless
public class MarcacionDocumentosFacade extends AbstractFacade<MarcacionDocumentos> implements MarcacionDocumentosFacadeLocal{
    
    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MarcacionDocumentosFacade() {
        super(MarcacionDocumentos.class);
    }
    
    @Override
    public MarcacionDocumentos findByIdEmpleado(int idEmpleado) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    *\n"
                    + "FROM\n"
                    + "    marcacion_documentos\n"
                    + "WHERE\n"
                    + "    id_empleado = ?1\n"
                    + "        AND activo = 1\n"
                    + "LIMIT 1;", MarcacionDocumentos.class);
            q.setParameter(1, idEmpleado);
            return (MarcacionDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

   @Override
    public List<MarcacionDocumentos> findAllActivos() {
        Query q = em.createNativeQuery("SELECT \n"
                + "    *\n"
                + "FROM\n"
                + "    marcacion_documentos\n"
                + "WHERE\n"
                + "    estado_reg = 0;", MarcacionDocumentos.class);
        return q.getResultList();
    }

    @Override
    public MarcacionDocumentos findByIdEmpleadoAndFecha(int idEmpleado, Date fecha) {
        try {
            Query q = em.createNativeQuery("SELECT \n"
                    + "    ed.*\n"
                    + "FROM\n"
                    + "    marcaciones_documentos ed\n"
                    + "WHERE\n"
                    + "    ed.id_empleado = ?1\n"
                    + "        AND fecha = ?2\n"
                    + "        AND estado_reg = 0\n"
                    + "LIMIT 1;", MarcacionDocumentos.class);
            q.setParameter(1, idEmpleado);
            q.setParameter(2, Util.dateFormat(fecha));
            return (MarcacionDocumentos) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
    
}
