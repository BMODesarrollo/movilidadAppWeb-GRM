package com.movilidad.ejb;

import com.movilidad.model.NovedadDocumentos;
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
public class NovedadDocumentosFacade extends AbstractFacade<NovedadDocumentos> implements NovedadDocumentosFacadeLocal {

    @PersistenceContext(unitName = "rigel")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NovedadDocumentosFacade() {
        super(NovedadDocumentos.class);
    }

    @Override
    public List<NovedadDocumentos> findByIdNovedad(int id) {
        try {
            Query query = em.createQuery("SELECT nd FROM NovedadDocumentos nd where nd.idNovedad.idNovedad = :id")
                    .setParameter("id", id);
            return query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public NovedadDocumentos findByCreadoAndIdNovedad(Date creado, int idNovedad) {
        try {
            Query query = em.createNativeQuery("select * from novedad_documentos where creado = ?1 and id_novedad = ?2;", NovedadDocumentos.class);
            query.setParameter(1, creado);
            query.setParameter(2, idNovedad);
            return (NovedadDocumentos) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }
    }

}
