package com.movilidad.jsf;

import com.movilidad.ejb.PmTamplitudHorasFacadeLocal;
import com.movilidad.ejb.PrgSerconFacadeLocal;
import com.movilidad.model.GopUnidadFuncional;
import com.movilidad.model.PmTamplitudHoras;
import com.movilidad.util.beans.InformeAmplitud;
import com.movilidad.utils.MovilidadUtil;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author solucionesit
 */
@Named(value = "amplitudeJSFMB")
@ViewScoped
public class AmplitudeJSFManagedBean implements Serializable {

    @EJB
    private PrgSerconFacadeLocal prgSerconEJB;
    @EJB
    private PmTamplitudHorasFacadeLocal pmTamplitudHorasEjb;
    @Inject
    private GopUnidadFuncionalSessionBean unidadFuncionalSessionBean;

    private Date fechaDesde;
    private Date fechaHasta;
    private Date fechaFiltro;

    private List<InformeAmplitud> listPrgSercon;
    private List<GopUnidadFuncional> lstUnidadesFuncionales;

    @PostConstruct
    public void init() {
        fechaDesde = MovilidadUtil.fechaHoy();
        fechaHasta = MovilidadUtil.fechaHoy();
    }

    public void cargarDatos() {
        PmTamplitudHoras pmTamplitudHora = pmTamplitudHorasEjb.findByFecha(fechaDesde, fechaHasta, unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());
        if (pmTamplitudHora == null) {
            listPrgSercon = null;
            PrimeFaces.current().ajax().update(":msgs");
            MovilidadUtil.addErrorMessage("No se han encontrado parámetros amplitud en las fechas seleccionadas");
            return;
        }
        listPrgSercon = prgSerconEJB.getAmplitudes(fechaDesde, fechaHasta, pmTamplitudHora.getNroHoras(), unidadFuncionalSessionBean.obtenerIdGopUnidadFuncional());

        if (listPrgSercon == null || listPrgSercon.isEmpty()) {
            listPrgSercon = null;
        }
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<InformeAmplitud> getListPrgSercon() {
        return listPrgSercon;
    }

    public void setListPrgSercon(List<InformeAmplitud> listPrgSercon) {
        this.listPrgSercon = listPrgSercon;
    }

    public List<GopUnidadFuncional> getLstUnidadesFuncionales() {
        return lstUnidadesFuncionales;
    }

    public void setLstUnidadesFuncionales(List<GopUnidadFuncional> lstUnidadesFuncionales) {
        this.lstUnidadesFuncionales = lstUnidadesFuncionales;
    }

}
